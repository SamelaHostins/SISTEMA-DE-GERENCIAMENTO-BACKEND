CREATE SCHEMA IF NOT EXISTS salao;

-- Extensão necessária para gerar UUID automaticamente
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tabela endereco
CREATE TABLE IF NOT EXISTS salao.endereco (
    id_endereco UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    rua VARCHAR(50) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    numero INT NOT NULL CHECK (numero >= 0),
    cep VARCHAR(10) NOT NULL CHECK (cep ~ '^\d{5}-\d{3}$')
);

-- Tabela profissional
CREATE TABLE IF NOT EXISTS salao.profissional (
    id_profissional UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    instagram VARCHAR(30),
    profissao VARCHAR(30) NOT NULL,
    nome VARCHAR(25) NOT NULL,
    sobrenome VARCHAR(25) NOT NULL,
    data_nascimento DATE NOT NULL CHECK (data_nascimento <= CURRENT_DATE),
    email VARCHAR(30) NOT NULL UNIQUE,
    telefone VARCHAR(12) NOT NULL UNIQUE CHECK (telefone ~ '^[0-9]+$'),
    usuario VARCHAR(25) NOT NULL UNIQUE,
    senha VARCHAR(8) NOT NULL CHECK (senha ~ '^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8}$'),
    documento VARCHAR(18) NOT NULL UNIQUE CHECK (
        documento ~ '^\d{3}\.\d{3}\.\d{3}-\d{2}$' OR -- Validação CPF (000.000.000-00)
        documento ~ '^\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}$' -- Validação CNPJ (00.000.000/0000-00)
    ),
    id_endereco UUID UNIQUE, -- Relacionamento OneToOne
    FOREIGN KEY (id_endereco) REFERENCES salao.endereco(id_endereco) ON DELETE SET NULL
);

-- Tabela cliente (agora com CPF/CNPJ)
CREATE TABLE IF NOT EXISTS salao.cliente (
    id_cliente UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(25) NOT NULL,
    sobrenome VARCHAR(25) NOT NULL,
    data_nascimento DATE NOT NULL CHECK (data_nascimento <= CURRENT_DATE),
    email VARCHAR(30) NOT NULL UNIQUE,
    telefone VARCHAR(12) NOT NULL UNIQUE CHECK (telefone ~ '^[0-9]+$'),
    usuario VARCHAR(25) NOT NULL UNIQUE,
    senha VARCHAR(8) NOT NULL CHECK (senha ~ '^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8}$'),
    documento VARCHAR(18) NOT NULL UNIQUE CHECK (
        documento ~ '^\d{3}\.\d{3}\.\d{3}-\d{2}$' OR -- Validação CPF (000.000.000-00)
        documento ~ '^\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}$' -- Validação CNPJ (00.000.000/0000-00)
    ),
    id_endereco UUID UNIQUE, -- Relacionamento OneToOne com endereço
    FOREIGN KEY (id_endereco) REFERENCES salao.endereco(id_endereco) ON DELETE SET NULL
);

-- Tabela metodoPagamento
CREATE TABLE IF NOT EXISTS salao.metodo_pagamento (
    id_metodo_pagamento UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(20) NOT NULL UNIQUE CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 20)
);

-- Tabela servico
CREATE TABLE IF NOT EXISTS salao.servico (
    id_servico UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tipo_servico INT4,
    nome VARCHAR(55) NOT NULL CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55),
    especificacao VARCHAR(500),
    termos_e_condicoes VARCHAR(1000),
    tempo VARCHAR(10),
    valor DECIMAL(5, 2),
    id_profissional UUID NOT NULL,
    FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional)
);

-- Tabela agendamento
CREATE TABLE IF NOT EXISTS salao.agendamento (
    id_agendamento UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dt_agendamento DATE NOT NULL CHECK (dt_agendamento >= CURRENT_DATE),
    hora_agendamento TIME NOT NULL,
    status_agendamento INT4,
    id_cliente UUID NOT NULL,
    id_servico UUID NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
    FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico)
);

-- Tabela profissionalMetodoPagamento (para relacionamento muitos-para-muitos)
CREATE TABLE IF NOT EXISTS salao.profissional_metodo_pagamento (
    id_profissional UUID NOT NULL,
    id_metodo_pagamento UUID NOT NULL,
    PRIMARY KEY (id_profissional, id_metodo_pagamento),
    FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
    FOREIGN KEY (id_metodo_pagamento) REFERENCES salao.metodo_pagamento(id_metodo_pagamento)
);

-- Tabela estoque
CREATE TABLE IF NOT EXISTS salao.estoque (
    id_estoque UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(55) NOT NULL CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55),
    qtd_de_produtos INT4,
    id_profissional UUID NOT NULL,
    FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional)
);

-- Tabela produto
CREATE TABLE IF NOT EXISTS salao.produto (
    id_produto UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(55) CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55),
    dt_entrada DATE,
    dt_validade DATE,
    valor DECIMAL(5, 2),
    id_estoque UUID NOT NULL,
    FOREIGN KEY (id_estoque) REFERENCES salao.estoque(id_estoque)
);

-- Tabela avaliacao
CREATE TABLE IF NOT EXISTS salao.avaliacao (
    id_avaliacao UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nota INT4 NOT NULL CHECK (nota >= 0 AND nota <= 5),
    dt_avaliacao DATE CHECK (dt_avaliacao >= CURRENT_DATE),
    id_cliente UUID NOT NULL,
    id_servico UUID NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
    FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico)
);

-- Tabela imagem
CREATE TABLE IF NOT EXISTS salao.imagem (
    id_imagem UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    url_imagem TEXT NOT NULL,
    tipo_imagem SMALLINT NOT NULL CHECK (tipo_imagem BETWEEN 0 AND 1),
    id_profissional UUID,
    id_cliente UUID,
    FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
    FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente)
);
