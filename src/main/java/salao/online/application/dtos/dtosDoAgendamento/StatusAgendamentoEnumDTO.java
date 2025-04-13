package salao.online.application.dtos.dtosDoAgendamento;

import java.util.Arrays;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum StatusAgendamentoEnumDTO {

    AGUARDANDO_ENTRADA(0),
    AGENDADO(1),
    CANCELADO(2);

    private @Getter int statusAgendamento;

    private StatusAgendamentoEnumDTO(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    // Método para converter um valor inteiro para o enum
    public static StatusAgendamentoEnumDTO fromStatusAgendamento(int statusAgendamento) {
        return Arrays.stream(StatusAgendamentoEnumDTO.values())
                .filter(t -> t.getStatusAgendamento() == statusAgendamento)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de agendamento inválido: " + statusAgendamento));
    }
}
