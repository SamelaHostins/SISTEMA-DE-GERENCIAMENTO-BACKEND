package salao.online.application.dtos.dtosDoAgendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarAgendamentoPeloClienteDTO {

    @NotNull
    private UUID idServico;

    @NotNull
    private UUID idCliente;

    @NotNull
    @FutureOrPresent
    private LocalDate dataAgendamento;

    @NotNull
    private LocalTime horaAgendamento;

    @NotNull
    private Integer formaPagamento;

}
