package salao.online.application.services.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import salao.online.application.dtos.dtosHorario.HorarioTrabalhoDTO;
import salao.online.application.mappers.HorarioTrabalhoMapper;
import salao.online.application.services.interfaces.HorarioTrabalhoService;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.entities.HorarioTrabalho;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.DiaSemanaEnum;
import salao.online.domain.enums.MensagemErroValidacaoEnum;
import salao.online.domain.exceptions.ValidacaoException;
import salao.online.infra.repositories.AgendamentoRepository;
import salao.online.infra.repositories.HorarioTrabalhoRepository;
import salao.online.infra.repositories.ProfissionalRepository;

@ApplicationScoped
public class HorarioTrabalhoServiceImpl implements HorarioTrabalhoService {

    @Inject
    AgendamentoRepository agendamentoRepository;

    @Inject
    ProfissionalRepository profissionalRepository;

    @Inject
    HorarioTrabalhoRepository horarioTrabalhoRepository;

    @Inject
    HorarioTrabalhoMapper mapper;

    @Override
    public List<HorarioTrabalhoDTO> listarHorariosDoProfissional(UUID idProfissional) {
        List<HorarioTrabalho> horarios = horarioTrabalhoRepository.list("profissional.id", idProfissional);
        return mapper.fromEntityListToDtoList(horarios);
    }

    @Override
    @Transactional
    public void atualizarHorariosTrabalho(UUID idProfissional, List<HorarioTrabalhoDTO> novosHorariosDto)
            throws ValidacaoException {

        Profissional profissional = profissionalRepository.findByIdOptional(idProfissional)
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));

        List<HorarioTrabalho> atuais = profissional.getHorariosTrabalho();

        List<HorarioTrabalho> novos = mapper.fromDtoListToEntityList(novosHorariosDto);
        novos.forEach(n -> n.setProfissional(profissional));

        // Descobre todos os dias que vieram na atualização
        Set<DiaSemanaEnum> diasAtualizados = novos.stream()
                .map(HorarioTrabalho::getDiaSemana)
                .collect(Collectors.toSet());

        // Filtra os horários antigos que NÃO são desses dias (serão preservados)
        List<HorarioTrabalho> remanescentes = atuais.stream()
                .filter(h -> !diasAtualizados.contains(h.getDiaSemana()))
                .collect(Collectors.toList());

        profissional.getHorariosTrabalho().clear();
        profissional.getHorariosTrabalho().addAll(remanescentes);
        profissional.getHorariosTrabalho().addAll(novos);

        profissionalRepository.persistAndFlush(profissional);

    }

    @Override
    public List<LocalTime> buscarHorariosDisponiveis(UUID idProfissional, LocalDate data) throws ValidacaoException {
        Profissional profissional = Optional.ofNullable(profissionalRepository.findById(idProfissional))
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));

        DiaSemanaEnum dia = DiaSemanaEnum.values()[data.getDayOfWeek().getValue() - 1];

        List<HorarioTrabalho> faixas = montarFaixasValidas(profissional.getHorariosTrabalho(), dia);
        if (faixas.isEmpty())
            return List.of();

        List<Agendamento> agendamentos = agendamentoRepository.buscarPorProfissionalEData(idProfissional, data);
        for (Agendamento ag : agendamentos) {
            if (ag.getServico() == null) {
                throw new ValidacaoException(MensagemErroValidacaoEnum.SERVICO_NULO.getMensagemErro());
            }
        }

        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        for (HorarioTrabalho faixa : faixas) {
            LocalTime inicio = faixa.getHoraInicio();
            LocalTime fim = faixa.getHoraFim();

            if (inicio == null || fim == null || !inicio.isBefore(fim))
                continue;

            long duracaoMinutos = Duration.between(inicio, fim).toMinutes();
            int blocos = (int) (duracaoMinutos / 30);

            for (int i = 0; i <= blocos; i++) {
                LocalTime inicioSlot = inicio.plusMinutes(i * 30);
                LocalTime fimSlot = inicioSlot.plusMinutes(30);
                if (fimSlot.isAfter(fim))
                    break;

                boolean sobrepoe = agendamentos.stream().anyMatch(ag -> {
                    LocalTime inicioAg = ag.getHoraAgendamento();
                    LocalTime fimAg = inicioAg.plus(ag.getServico().getTempo());

                    return !(fimSlot.isBefore(inicioAg) || fimSlot.equals(inicioAg)
                            || inicioSlot.isAfter(fimAg) || inicioSlot.equals(fimAg));
                });

                if (!sobrepoe) {
                    horariosDisponiveis.add(inicioSlot);
                }
            }
        }

        return horariosDisponiveis;
    }

    private List<HorarioTrabalho> montarFaixasValidas(List<HorarioTrabalho> todos, DiaSemanaEnum dia) {
        List<HorarioTrabalho> doDia = todos.stream()
                .filter(h -> h.getDiaSemana() == dia)
                .toList();

        List<HorarioTrabalho> completas = doDia.stream()
                .filter(f -> f.getHoraInicio() != null && f.getHoraFim() != null
                        && !f.getHoraInicio().isAfter(f.getHoraFim()))
                .collect(Collectors.toList());

        List<HorarioTrabalho> inicios = doDia.stream()
                .filter(f -> f.getHoraInicio() != null && f.getHoraFim() == null)
                .collect(Collectors.toList());

        List<HorarioTrabalho> fins = new ArrayList<>(doDia.stream()
                .filter(f -> f.getHoraInicio() == null && f.getHoraFim() != null)
                .toList());

        for (HorarioTrabalho inicio : inicios) {
            HorarioTrabalho fim = fins.stream()
                    .filter(f -> f.getDiaSemana() == dia)
                    .findFirst()
                    .orElse(null);

            if (fim != null) {
                LocalTime hi = inicio.getHoraInicio();
                LocalTime hf = fim.getHoraFim();

                if (hi != null && hf != null && !hi.isAfter(hf)) {
                    HorarioTrabalho faixa = new HorarioTrabalho();
                    faixa.setDiaSemana(dia);
                    faixa.setHoraInicio(hi);
                    faixa.setHoraFim(hf);
                    completas.add(faixa);
                    fins.remove(fim); // evita reuso
                }
            }
        }

        return completas;
    }

    @Override
    public void deletarHorarioDeTrabalho(UUID idHorario, UUID idProfissional) {
        HorarioTrabalho horario = horarioTrabalhoRepository.findById(idHorario);

        if (horario == null || !horario.getProfissional().getIdProfissional().equals(idProfissional)) {
            throw new WebApplicationException("Horário não encontrado ou não pertence ao profissional", 403);
        }

        horarioTrabalhoRepository.delete(horario);
    }
}
