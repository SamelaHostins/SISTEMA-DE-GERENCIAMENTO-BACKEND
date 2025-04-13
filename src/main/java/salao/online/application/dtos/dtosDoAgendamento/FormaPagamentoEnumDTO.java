package salao.online.application.dtos.dtosDoAgendamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum FormaPagamentoEnumDTO {
    CARTAO_DEBITO(0),
    CARTAO_CREDITO(1),
    DINHEIRO(2),
    PIX(3);

    private final int formaPagamento;

    FormaPagamentoEnumDTO(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @JsonValue
    public int getFormaPagamento() {
        return formaPagamento;
    }

    public static FormaPagamentoEnumDTO fromFormaPagamento(int valor) {
        for (FormaPagamentoEnumDTO tipo : values()) {
            if (tipo.getFormaPagamento() == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Forma de pagamento inválida: " + valor);
    }
}
