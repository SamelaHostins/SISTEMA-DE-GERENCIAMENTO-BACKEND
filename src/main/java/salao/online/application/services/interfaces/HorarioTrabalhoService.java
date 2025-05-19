package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import salao.online.application.dtos.dtosHorario.HorarioTrabalhoDTO;
import salao.online.domain.exceptions.ValidacaoException;

public interface HorarioTrabalhoService {

    List<HorarioTrabalhoDTO> listarHorariosDoProfissional(UUID idProfissional);

    void atualizarHorariosTrabalho(UUID idProfissional, List<HorarioTrabalhoDTO> horarios) throws ValidacaoException;

    List<LocalTime> buscarHorariosDisponiveis(UUID idProfissional, LocalDate data) throws ValidacaoException;

    void deletarHorarioDeTrabalho(UUID idHorario, UUID idProfissional);
}
