package salao.online.application.dtos;

import lombok.Getter;

public enum StatusAgendamentoEnumDTO {

    AGUARDANDO_ENTRADA(0),
    AGENDADO(1),
    CANCELADO(2);
    
    private @Getter int statusAgendamento;

    private StatusAgendamentoEnumDTO(int statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}
