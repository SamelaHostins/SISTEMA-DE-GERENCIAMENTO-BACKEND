CREATE SCHEMA IF NOT EXISTS salao;

-- Extensão necessária para gerar UUID automaticamente
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tabela endereco
CREATE TABLE IF NOT EXISTS salao.endereco (
    id_endereco UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR (20) NOT NULL,
    rua VARCHAR(50) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    numero INT NOT NULL CHECK (numero >= 0),
    cep VARCHAR(10) NOT NULL,
    complemento VARCHAR (30)
);

-- Tabela profissionals
CREATE TABLE IF NOT EXISTS salao.profissional (
    id_profissional UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    instagram VARCHAR(30),
    profissao INT4 NOT NULL,
    descricao_prof VARCHAR(150),
    nome VARCHAR(25) NOT NULL,
    sobrenome VARCHAR(25) NOT NULL,
    data_nascimento DATE NOT NULL CHECK (data_nascimento <= CURRENT_DATE),
    email VARCHAR(30) NOT NULL UNIQUE,
    telefone VARCHAR(12) NOT NULL,
    usuario VARCHAR(25) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    aceitou_termos BOOLEAN NOT NULL,
    documento VARCHAR(14) NOT NULL UNIQUE CHECK (
    documento ~ '^\d{11}$' OR 
    documento ~ '^\d{14}$' 
),
    id_endereco UUID UNIQUE, -- Relacionamento OneToOne
    FOREIGN KEY (id_endereco) REFERENCES salao.endereco(id_endereco) ON DELETE SET NULL
);

-- Tabela perguntas frequentes
CREATE TABLE IF NOT EXISTS salao.pergunta_frequente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pergunta VARCHAR(150) NOT NULL,
    resposta VARCHAR(150) NOT NULL,
    id_profissional UUID NOT NULL,
    CONSTRAINT fk_profissional_pergunta FOREIGN KEY (id_profissional)
        REFERENCES salao.profissional(id_profissional)
        ON DELETE CASCADE
);


-- Tabela cliente 
CREATE TABLE IF NOT EXISTS salao.cliente (
    id_cliente UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(25) NOT NULL,
    sobrenome VARCHAR(25) NOT NULL,
    data_nascimento DATE NOT NULL CHECK (data_nascimento <= CURRENT_DATE),
    email VARCHAR(30) NOT NULL UNIQUE,
    telefone VARCHAR(12) NOT NULL,
    usuario VARCHAR(25) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    aceitou_termos BOOLEAN NOT NULL,
    documento VARCHAR(14) NOT NULL UNIQUE CHECK (
    documento ~ '^\d{11}$' OR 
    documento ~ '^\d{14}$' 
    ),
    hora_inicio_preferida TIME NOT NULL DEFAULT '07:00:00',
    hora_fim_preferida TIME NOT NULL DEFAULT '19:00:00'
);

-- Tabela servico
CREATE TABLE IF NOT EXISTS salao.servico (
    id_servico UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tipo_servico INT4,
    nome VARCHAR(55) NOT NULL CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55),
    especificacao VARCHAR(200),
    termos_e_condicoes VARCHAR(1000),
    tempo INTERVAL,
    valor DECIMAL(5, 2),
    id_profissional UUID NOT NULL,
    FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional)
);

-- Tabela agendamento
CREATE TABLE IF NOT EXISTS salao.agendamento (
    id_agendamento UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dt_agendamento DATE NOT NULL CHECK (dt_agendamento >= CURRENT_DATE),
    hora_agendamento TIME NOT NULL,
    status_agendamento INT4 NOT NULL,
    forma_pagamento INT4 NOT NULL,
    id_cliente UUID,
    id_servico UUID NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
    FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico)
);

-- Tabela Horário de trabalho
CREATE TABLE IF NOT EXISTS salao.horario_trabalho (
    id_horario UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dia_semana INT NOT NULL CHECK (dia_semana BETWEEN 0 AND 6), -- 0=DOMINGO, 1=SEGUNDA, ..., 6=SÁBADO
    hora_inicio TIME,
    hora_fim TIME,
    id_profissional UUID NOT NULL,
    CONSTRAINT fk_profissional_horario FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional) ON DELETE CASCADE
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
