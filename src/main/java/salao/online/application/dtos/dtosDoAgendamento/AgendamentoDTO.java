package salao.online.application.dtos.dtosDoAgendamento;

import java.time.LocalDate;
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
public class AgendamentoDTO {

    private UUID idAgendamento;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
    private StatusAgendamentoEnumDTO status;
    private FormaPagamentoEnumDTO formaPagamento;
    private String nomeCliente;
    private String nomeProfissional;
    private String nomeServico;
    private double valorServico;
    private String tempoServico;
}
