package salao.online.application.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import salao.online.application.services.interfaces.HorarioTrabalhoService;
import salao.online.domain.entities.Agendamento;
import salao.online.domain.entities.HorarioTrabalho;
import salao.online.domain.entities.Profissional;
import salao.online.domain.enums.DiaSemanaEnum;
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

    @Override
    public List<LocalTime> buscarHorariosDisponiveis(UUID idProfissional, LocalDate data) {
        Profissional profissional = profissionalRepository.findById(idProfissional);
        if (profissional == null) {
            throw new WebApplicationException("Profissional não encontrado", 404);
        }

        // Descobre o dia da semana (0 = DOMINGO, 1 = SEGUNDA...)
        DiaSemanaEnum dia = DiaSemanaEnum.values()[data.getDayOfWeek().getValue() % 7];

        // Filtra faixas cadastradas pro dia
        List<HorarioTrabalho> faixas = profissional.getHorariosTrabalho().stream()
                .filter(h -> h.getDiaSemana() == dia)
                .toList();

        if (faixas.isEmpty())
            return List.of(); // Sem expediente hoje? Então nem tenta.

        // Puxa os agendamentos já existentes do dia
        List<Agendamento> agendamentos = agendamentoRepository
                .buscarPorProfissionalEData(idProfissional, data);

        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        for (HorarioTrabalho faixa : faixas) {
            LocalTime hora = faixa.getHoraInicio();

            while (!hora.plusMinutes(30).isAfter(faixa.getHoraFim())) {
                final LocalTime finalHora = hora; // Agora sim, sem treta com a lambda!

                boolean sobrepoe = agendamentos.stream().anyMatch(ag -> {
                    LocalTime inicioAg = ag.getHoraAgendamento();
                    LocalTime fimAg = inicioAg.plus(ag.getServico().getTempo());
                    return !(finalHora.plusMinutes(30).isBefore(inicioAg)
                            || finalHora.isAfter(fimAg));
                });

                if (!sobrepoe) {
                    horariosDisponiveis.add(hora);
                }

                hora = hora.plusMinutes(30); // Bora pro próximo bloco
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
