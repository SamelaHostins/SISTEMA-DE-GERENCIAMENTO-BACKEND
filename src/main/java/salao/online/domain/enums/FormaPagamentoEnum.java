package salao.online.domain.enums;

import lombok.Getter;

public enum FormaPagamentoEnum {
    CARTAO_DEBITO(0),
    CARTAO_CREDITO(1),
    DINHEIRO(2),
    PIX(3);

    private @Getter int formaPagamento;

    FormaPagamentoEnum(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public static FormaPagamentoEnum fromFormaPagamento(int formaPagamento) {
        for (FormaPagamentoEnum tipo : values()) {
            if (tipo.getFormaPagamento() == formaPagamento) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Forma de pagamento inválida: " + formaPagamento);
    }
}
