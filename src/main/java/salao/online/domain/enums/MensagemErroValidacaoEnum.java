package salao.online.domain.enums;

import lombok.Getter;

public enum MensagemErroValidacaoEnum {

    //Mensagens de erro de Cliente
    CLIENTE_NAO_ENCONTRADO("O cliente não foi encontrado."),

    //Mensagens de erro de Profissional
    PROFISSIONAL_NAO_ENCONTRADO("O profissional não foi encontrado."),
    
    //Mensagem de erro de Serviço
    SERVICO_NAO_ENCONTRADO("O serviço não foi encontrado."),

    //Mensagem de erro de Produto
    PRODUTO_NAO_ENCONTRADO("O produto não foi encontrado."),

    //Mensagem de erro de Relatório
    RELATORIO_NAO_CRIADO("O relatório não pôde ser criado."),

    //Mensagem de erro de Avaliação
    AVALIACAO_INSUFICIENTE("É necessário no mínimo 3 avaliações para retornar uma média");

    public @Getter String mensagemErro;

    private MensagemErroValidacaoEnum(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
