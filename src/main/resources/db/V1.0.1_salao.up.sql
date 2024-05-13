CREATE SCHEMA IF NOT EXISTS salao;

-- Tabela informacao
CREATE TABLE IF NOT EXISTS salao.informacao (
   idInformacao UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   sobrenome VARCHAR(55), (LENGTH(sobrenome) >= 3 AND LENGTH(sobrenome) <= 55)
   email VARCHAR(30),
   telefone VARCHAR(12),
   usuario VARCHAR (12),
   senha VARCHAR(8),
);

-- Tabela cliente
CREATE TABLE IF NOT EXISTS salao.cliente (
   idCliente UUID NOT NULL PRIMARY KEY
   idInformacao UUID NOT NULL,
   FOREIGN KEY (idInformacao) REFERENCES salao.informacao(idInformacao)
);

-- Tabela endereco
CREATE TABLE IF NOT EXISTS salao.endereco (
   idEndereco UUID NOT NULL PRIMARY KEY,
   rua VARCHAR(120),
   bairro VARCHAR(50),
   cidade VARCHAR(20),
   numero INT,
   cep VARCHAR(10)
);

-- Tabela profissional
CREATE TABLE IF NOT EXISTS salao.profissional (
   idProfissional UUID NOT NULL PRIMARY KEY,
   metodosDePagamento VARCHAR(255),
   idInformacao UUID NOT NULL,
   idEndereco UUID NOT NULL,
   FOREIGN KEY (idInformacao) REFERENCES salao.informacao(idInformacao)
   FOREIGN KEY (idEndereco) REFERENCES salao.endereco(idEndereco));

-- Tabela metodoPagamento
CREATE TABLE IF NOT EXISTS salao.metodoPagamento (
   idMetodo UUID PRIMARY KEY,
   nome VARCHAR(50), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55) UNIQUE
);

-- Tabela profissionalMetodoPagamento (para relacionamento muitos-para-muitos)
CREATE TABLE IF NOT EXISTS salao.profissionalMetodoPagamento (
   idProfissional UUID,
   idMetodo INT,
   PRIMARY KEY (idProfissional, idMetodo),
   FOREIGN KEY (idProfissional) REFERENCES salao.profissional(idProfissional),
   FOREIGN KEY (idMetodo) REFERENCES salao.metodoPagamento(idMetodo)
);

-- Tabela estoque
CREATE TABLE IF NOT EXISTS salao.estoque (
   idEstoque UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   qtdProdutos INT,
   idProfissional UUID,
   FOREIGN KEY (idProfissional) REFERENCES salao.profissional(idProfissional)
);

-- Tabela produto
CREATE TABLE IF NOT EXISTS salao.produto (
   idProduto UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(100), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   dtEntrada DATE,
   validade DATE,
   valor DOUBLE PRECISION, --
   idEstoque UUID,
   FOREIGN KEY (idEstoque) REFERENCES salao.estoque(idEstoque)
);

-- Tabela servico
CREATE TABLE IF NOT EXISTS salao.servico (
   idServico UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   especificacao VARCHAR(500),
   termosCondicoes VARCHAR(5000),
   valor DECIMAL(5, 2),
   idProfissional UUID,
   FOREIGN KEY (idProfissional) REFERENCES salao.profissional(idProfissional)
);

-- Tabela avaliacao
CREATE TABLE IF NOT EXISTS salao.avaliacao (
   idAvaliacao UUID NOT NULL PRIMARY KEY,
   nota INT,
   idCliente UUID,
   idServico UUID,
   FOREIGN KEY (idCliente) REFERENCES salao.cliente(idCliente),
   FOREIGN KEY (idServico) REFERENCES salao.servico(idServico)
);

-- Tabela agendamento
CREATE TABLE IF NOT EXISTS salao.agendamento (
   idAgendamento UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   hora TIME,
   idCliente UUID,
   idServico UUID,
   FOREIGN KEY (idCliente) REFERENCES salao.cliente(idCliente),
   FOREIGN KEY (idServico) REFERENCES salao.servico(idServico)
);

-- Tabela calendario
CREATE TABLE IF NOT EXISTS salao.calendario (
   idCalendario UUID NOT NULL PRIMARY KEY,
   nome VARCHAR(55), (LENGTH(nome) >= 3 AND LENGTH(nome) <= 55)
   hora TIME,
   profissionalId UUID,
   FOREIGN KEY (idProfissional) REFERENCES salao.profissional(idProfissional)
);