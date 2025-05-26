package salao.online.application.dtos.dtosHorario;

import java.time.LocalTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioTrabalhoDTO {
    private UUID idHorario;
    private int diaSemana; // 0 = DOMINGO, 1 = SEGUNDA...
    private LocalTime horaInicio;
    private LocalTime horaFim;
}
