package salao.online.application.dtos.dtosDoAgendamento;

import lombok.Getter;

public enum FormaPagamentoEnumDTO {
    CARTAO_DEBITO(0),
    CARTAO_CREDITO(1),
    DINHEIRO(2),
    PIX(3);

    private @Getter int formaPagamento;

    FormaPagamentoEnumDTO(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public static FormaPagamentoEnumDTO fromFormaPagamento(int formaPagamento) {
        for (FormaPagamentoEnumDTO tipo : values()) {
            if (tipo.getFormaPagamento() == formaPagamento) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Forma de pagamento inválida: " + formaPagamento);
    }
}
