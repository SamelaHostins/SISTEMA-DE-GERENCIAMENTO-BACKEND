package salao.online.application.dtos.dtosHorario;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioTrabalhoDTO {
    private int diaSemana; // 0 = DOMINGO, 1 = SEGUNDA...
    private LocalTime horaInicio;
    private LocalTime horaFim;
}
