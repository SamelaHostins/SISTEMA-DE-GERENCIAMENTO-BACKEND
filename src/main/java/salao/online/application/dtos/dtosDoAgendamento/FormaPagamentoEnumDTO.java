package salao.online.application.dtos.dtosDoAgendamento;

import lombok.Getter;

@Getter
public class FormaPagamentoEnumDTO {

    private int codigo;
    private String descricao;

    public FormaPagamentoEnumDTO(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
