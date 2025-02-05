package salao.online.application.dtos.dtosDoAgendamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    
    private UUID idAgendamento;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
    private StatusAgendamentoEnumDTO statusAgendamento;
    private UUID idCliente;
    private UUID idServico;
}
