package salao.online.domain.enums;

import lombok.Getter;

public enum MensagemErroValidacaoEnum {

    // Mensagens de erro de Cliente
    CLIENTE_NAO_ENCONTRADO("O cliente não foi encontrado."),
    EMAIL_JA_CADASTRADO("Já existe um usuário cadastrado com este e-mail."),
    DOCUMENTO_JA_CADASTRADO("Já existe um usuário cadastrado com este CPF/CNPJ."),

    // Mensagens de erro de Profissional
    PROFISSIONAL_NAO_ENCONTRADO("O profissional não foi encontrado."),

    // Mensagens de erro de Estoque
    ESTOQUE_NAO_ENCONTRADO("O estoque não foi encontrado."),

    // Mensagem de erro de Serviço
    SERVICO_NAO_ENCONTRADO("O serviço não foi encontrado."),

    // Mensagem de erro de Produto
    PRODUTO_NAO_ENCONTRADO("O produto não foi encontrado."),

    // Mensagem de erro de Relatório
    RELATORIO_NAO_CRIADO("O relatório não pôde ser criado.");

    public @Getter String mensagemErro;

    private MensagemErroValidacaoEnum(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
