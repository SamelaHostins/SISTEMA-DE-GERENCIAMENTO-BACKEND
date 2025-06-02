package salao.online.application.services.impl;

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
        // Validação do profissional
        Profissional profissional = Optional.ofNullable(profissionalRepository.findById(idProfissional))
                .orElseThrow(() -> new ValidacaoException(
                        MensagemErroValidacaoEnum.PROFISSIONAL_NAO_ENCONTRADO.getMensagemErro()));

        // Descobre o dia da semana (0 = SEGUNDA, 1 = TERÇA...)
        DiaSemanaEnum dia = DiaSemanaEnum.values()[data.getDayOfWeek().getValue() - 1];

        // Filtra faixas cadastradas para o dia
        List<HorarioTrabalho> faixas = profissional.getHorariosTrabalho().stream()
                .filter(h -> h.getDiaSemana() == dia)
                .toList();

        // Retorna lista vazia se não há expediente hoje
        if (faixas.isEmpty()) {
            return List.of();
        }

        // Puxa os agendamentos já existentes para o dia
        List<Agendamento> agendamentos = agendamentoRepository
                .buscarPorProfissionalEData(idProfissional, data);

        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        for (HorarioTrabalho faixa : faixas) {
            LocalTime hora = faixa.getHoraInicio();

            while (!hora.plusMinutes(30).isAfter(faixa.getHoraFim())) {
                final LocalTime finalHora = hora;

                boolean sobrepoe = agendamentos.stream().anyMatch(ag -> {
                    LocalTime inicioAg = ag.getHoraAgendamento();
                    LocalTime fimAg = inicioAg.plus(ag.getServico().getTempo());
                    return !(finalHora.plusMinutes(30).isBefore(inicioAg) || finalHora.isAfter(fimAg));
                });
                if (!sobrepoe) {
                    horariosDisponiveis.add(hora);
                }
                hora = hora.plusMinutes(30);
            }
        }

        return horariosDisponiveis;
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
