package salao.online.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoDTO {
    
    private UUID idAgendamento;
    private LocalDate dataAgendamento;
    private Time horaAgendamento;
    private StatusAgendamentoEnumDTO statusAgendamento;
    private UUID idCliente;
    private UUID idServico;
}
