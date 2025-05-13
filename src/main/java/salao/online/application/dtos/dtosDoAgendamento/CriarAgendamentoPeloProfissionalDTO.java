package salao.online.application.dtos.dtosDoAgendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarAgendamentoPeloProfissionalDTO {

    @NotNull
    private UUID idServico;

    @NotNull
    @FutureOrPresent
    private LocalDate dataAgendamento;

    @NotNull
    private LocalTime horaAgendamento;

    private UUID idCliente;
}