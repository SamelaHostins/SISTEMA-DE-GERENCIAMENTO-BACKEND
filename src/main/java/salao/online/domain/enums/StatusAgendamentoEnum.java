package salao.online.domain.enums;

import lombok.Getter;

public enum StatusAgendamentoEnum {
    AGUARDANDO_ENTRADA(0),
    AGENDADO(1),
    CANCELADO(2);

    public @Getter int statusAgendamento;

    private StatusAgendamentoEnum(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}
