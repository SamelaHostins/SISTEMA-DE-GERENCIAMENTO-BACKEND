package salao.online.application.dtos.dtosDoAgendamento;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

// DTO de filtro que o frontend pode enviar como JSON
@Getter
@Setter
public class FiltroAgendamentoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer status;
    private Integer formaPagamento;
}
