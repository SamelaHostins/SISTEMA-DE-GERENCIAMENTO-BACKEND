package salao.online.application.dtos.dtosDoAgendamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum StatusAgendamentoEnumDTO {
    AGUARDANDO_ENTRADA(0),
    AGENDADO(1),
    CANCELADO(2);

    private final int statusAgendamento;

    StatusAgendamentoEnumDTO(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    @JsonValue
    public int getStatusAgendamento() {
        return statusAgendamento;
    }

    public static StatusAgendamentoEnumDTO fromStatusAgendamento(int valor) {
        for (StatusAgendamentoEnumDTO tipo : values()) {
            if (tipo.getStatusAgendamento() == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + valor);
    }
}
