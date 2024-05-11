CREATE SCHEMA IF NOT EXISTS salao;

-- Tabela informacao
CREATE TABLE IF NOT EXISTS salao.informacao (
   id_informacao UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(25) NOT NULL,
   sobrenome VARCHAR(25) NOT NULL, -- adicionar minimo de 3 caracteres
   email VARCHAR(30) NOT NULL, -- adicionar minimo de 3 caracteres
   telefone VARCHAR(12) NOT NULL,
   usuario VARCHAR (25) NOT NULL, -- adicionar minimo de 3 caracteres
   senha VARCHAR(8) NOT NULL, ---- verificar se tem algo que diz que tem que ter EXATO 8 caracteres
);

-- Tabela cliente
CREATE TABLE IF NOT EXISTS salao.cliente (
   id_cliente UUID NOT NULL PRIMARY KEY
   id_informacao UUID NOT NULL,
   FOREIGN KEY (id_informacao) REFERENCES salao.informacao(id_informacao)
);

-- Tabela endereco
CREATE TABLE IF NOT EXISTS salao.endereco (
   id_endereco UUID NOT NULL PRIMARY KEY,
   rua VARCHAR(20),
   bairro VARCHAR(20),
   cidade VARCHAR(20),
   numero INT,
   cep VARCHAR(10) NOT NULL
);

-- Tabela profissional
CREATE TABLE IF NOT EXISTS salao.profissional (
   id_profissional UUID NOT NULL PRIMARY KEY,
   id_informacao UUID NOT NULL,
   id_endereco UUID NOT NULL,
   FOREIGN KEY (id_informacao) REFERENCES salao.informacao(id_informacao)
   FOREIGN KEY (id_endereco) REFERENCES salao.endereco(id_endereco));

-- Tabela metodoPagamento
CREATE TABLE IF NOT EXISTS salao.metodo_pagamento (
   id_metodo_pagamento UUID PRIMARY KEY,
   nome VARCHAR(20) UNIQUE
);

-- Tabela profissionalMetodoPagamento (para relacionamento muitos-para-muitos)
CREATE TABLE IF NOT EXISTS salao.profissional_metodo_pagamento (
   id_profissional UUID,
   id_metodo_pagamento INT,
   PRIMARY KEY (id_profissional, id_metodo_pagamento),
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional),
   FOREIGN KEY (id_metodo_pagamento) REFERENCES salao.metodo_pagamento(id_metodo_pagamento)
);

-- Tabela estoque
CREATE TABLE IF NOT EXISTS salao.estoque (
   id_estoque UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55) NOT NULL,
   qtd_de_produtos INT,
   id_profissional UUID NOT NULL,
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional)
);

-- Tabela produto
CREATE TABLE IF NOT EXISTS salao.produto (
   id_produto UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55),
   dt_entrada DATE,
   dt_validade DATE,
   valor DOUBLE PRECISION, --
   id_estoque UUID NOT NULL,
   FOREIGN KEY (id_estoque) REFERENCES salao.estoque(id_estoque)
);

-- Tabela servico
CREATE TABLE IF NOT EXISTS salao.servico (
   id_servico UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55) NOT NULL (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   especificacao VARCHAR(500),
   termos_e_condicoes VARCHAR(1000),
   valor DECIMAL(5, 2),
   id_profissional UUID NOT NULL,
   FOREIGN KEY (id_profissional) REFERENCES salao.profissional(id_profissional)
);

-- Tabela avaliacao
CREATE TABLE IF NOT EXISTS salao.avaliacao (
   id_avaliacao UUID NOT NULL PRIMARY KEY,
   nota INT NOT NULL,
   dt_avaliacao DATE,
   id_cliente UUID NOT NULL,
   id_servico UUID NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
   FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico)
);

-- Tabela agendamento
CREATE TABLE IF NOT EXISTS salao.agendamento (
   id_agendamento UUID NOT NULL PRIMARY KEY,
   dt_agendamento DATE NOT NULL,
   hora_agendamento TIME NOT NULL,
   status_agendamento INT,
   id_cliente UUID NOT NULL,
   id_servico UUID NOT NULL,
   FOREIGN KEY (id_cliente) REFERENCES salao.cliente(id_cliente),
   FOREIGN KEY (id_servico) REFERENCES salao.servico(id_servico)
);