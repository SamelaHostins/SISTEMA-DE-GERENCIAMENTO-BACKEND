package salao.online.domain.enums;

import lombok.Getter;

public enum StatusAgendamentoEnum {
    AGENDADO(0),
    AGUARDANDO_ENTRADA(1),
    CONFIRMADO(2),
    CANCELADO(3);

    public @Getter int statusAgendamento;

    private StatusAgendamentoEnum(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}
