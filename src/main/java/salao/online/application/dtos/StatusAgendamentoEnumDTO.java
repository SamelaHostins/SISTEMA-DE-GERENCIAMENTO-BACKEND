package salao.online.application.dtos;

import lombok.Getter;

public enum StatusAgendamentoEnumDTO {

    AGENDADO(0),
    AGUARDANDO_ENTRADA(1),
    CONFIRMADO(2),
    CANCELADO(3);
    
    private @Getter int statusAgendamento;

    private StatusAgendamentoEnumDTO(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}
