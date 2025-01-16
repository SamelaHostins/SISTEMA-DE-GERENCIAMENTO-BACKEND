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

    // Método personalizado para buscar o enum pelo valor inteiro
    public static StatusAgendamentoEnum fromStatusAgendamento(int statusAgendamento) {
        for (StatusAgendamentoEnum tipo : StatusAgendamentoEnum.values()) {
            if (tipo.getStatusAgendamento() == statusAgendamento) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de serviço inválido: " + statusAgendamento);
    }
}
