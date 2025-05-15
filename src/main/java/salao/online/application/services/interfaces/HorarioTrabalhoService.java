package salao.online.application.services.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface HorarioTrabalhoService {

    List<LocalTime> buscarHorariosDisponiveis(UUID idProfissional, LocalDate data);

    void deletarHorarioDeTrabalho(UUID idHorario, UUID idProfissional);
}
