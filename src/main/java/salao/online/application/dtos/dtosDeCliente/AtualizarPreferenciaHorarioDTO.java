package salao.online.application.dtos.dtosDeCliente;

import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtualizarPreferenciaHorarioDTO {

    private UUID idCliente;
    private LocalTime horaInicioPreferida;
    private LocalTime horaFimPreferida;
}