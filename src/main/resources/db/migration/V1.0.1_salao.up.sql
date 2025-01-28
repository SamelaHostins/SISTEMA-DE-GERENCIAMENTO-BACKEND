CREATE SCHEMA IF NOT EXISTS salao;

-- Tabela cliente
CREATE TABLE IF NOT EXISTS salao.cliente (
    id_cliente UUID NOT NULL PRIMARY KEY,
    especial BOOLEAN,
    nome VARCHAR(25) NOT NULL,
    sobrenome VARCHAR(25) NOT NULL,
    nome_social VARCHAR(25),
    idade SMALLINT NOT NULL,  
    email VARCHAR(30) NOT NULL,
    telefone VARCHAR(12) NOT NULL,
    usuario VARCHAR(25) NOT NULL,
    senha VARCHAR(8) NOT NULL,
    CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 25),
    CHECK (LENGTH(sobrenome) >= 3 AND LENGTH(sobrenome) <= 25),
    CHECK (LENGTH(nome_social) >= 3 AND LENGTH(nome_social) <= 25),
    CHECK (senha ~ '^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8}$'),
    CHECK (idade > 0),
    CHECK (telefone ~ '^[0-9]+$')
);

 
-- Tabela profissional
CREATE TABLE IF NOT EXISTS salao.profissional (
   id_profissional UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(25) NOT NULL,
   sobrenome VARCHAR(25) NOT NULL,
   nome_social VARCHAR(25),
   idade SMALLINT NOT NULL,
   email VARCHAR(30) NOT NULL,
   telefone VARCHAR(12) NOT NULL,
   usuario VARCHAR(25) NOT NULL,
   senha VARCHAR(8) NOT NULL,
   rua VARCHAR(20),
   bairro VARCHAR(20),
   cidade VARCHAR(20),
   numero INT4,
   cep VARCHAR(10) NOT NULL
   CHECK (numero >= 0)
   CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 25),
   CHECK (LENGTH(sobrenome) >= 3 AND LENGTH(sobrenome) <= 25),
   CHECK (senha ~ '^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8}$'),
   CHECK (idade > 0),
   CHECK (telefone ~ '^[0-9]+$')
);

-- Tabela metodoPagamento
CREATE TABLE IF NOT EXISTS salao.metodo_pagamento (
   id_metodo_pagamento UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(20) NOT NULL UNIQUE,
   CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 20)
);

-- Tabela servico
CREATE TABLE IF NOT EXISTS salao.servico (
   id_servico UUID NOT NULL PRIMARY KEY,
   tipo_servico INT4,
   nome VARCHAR(55) NOT NULL,
   especificacao VARCHAR(500),
   termos_e_condicoes VARCHAR(1000),
   tempo VARCHAR (10),
   valor DECIMAL(5, 2),
   id_profissional UUID NOT NULL,
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
   CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
);

-- Tabela agendamento
CREATE TABLE IF NOT EXISTS salao.agendamento (
   id_agendamento UUID NOT NULL PRIMARY KEY,
   dt_agendamento DATE NOT NULL,
   hora_agendamento TIME NOT NULL,
   status_agendamento INT4,
   id_cliente UUID NOT NULL,
   id_servico UUID NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
   FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico),
   CHECK (dt_agendamento >= CURRENT_DATE)
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
   id_estoque UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55) NOT NULL,
   qtd_de_produtos INT4,
   id_profissional UUID NOT NULL,
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
   CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
);

-- Tabela produto
CREATE TABLE IF NOT EXISTS salao.produto (
   id_produto UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55),
   dt_entrada DATE,
   dt_validade DATE,
   valor DECIMAL(5, 2),
   id_estoque UUID NOT NULL,
   FOREIGN KEY (id_estoque) REFERENCES salao.estoque(id_estoque),
   CHECK (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
);

-- Tabela avaliacao
CREATE TABLE IF NOT EXISTS salao.avaliacao (
   id_avaliacao UUID NOT NULL PRIMARY KEY,
   nota INT4 NOT NULL,
   dt_avaliacao DATE,
   id_cliente UUID NOT NULL,
   id_servico UUID NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
   FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico),
   CHECK (nota >= 0 AND nota <= 5),
   CHECK (dt_avaliacao >= CURRENT_DATE)
);

-- Tabela imagem
CREATE TABLE IF NOT EXISTS salao.imagem (
   id_imagem UUID NOT NULL PRIMARY KEY,
   url_imagem TEXT NOT NULL,
   tipo_imagem SMALLINT NOT NULL CHECK (tipo_imagem BETWEEN 0 AND 1),
   id_profissional UUID,
   id_cliente UUID,
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
   FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente)
);
