-- Excluir todas as tabelas se existirem
DROP TABLE IF EXISTS salao.avaliacao;
DROP TABLE IF EXISTS salao.agendamento;
DROP TABLE IF EXISTS salao.calendario;
DROP TABLE IF EXISTS salao.servico;
DROP TABLE IF EXISTS salao.produto;
DROP TABLE IF EXISTS salao.estoque;
DROP TABLE IF EXISTS salao.profissionalMetodoPagamento;
DROP TABLE IF EXISTS salao.metodoPagamento;
DROP TABLE IF EXISTS salao.profissional;
DROP TABLE IF EXISTS salao.endereco;
DROP TABLE IF EXISTS salao.cliente;
DROP TABLE IF EXISTS salao.informacao;

-- Excluir o esquema
DROP SCHEMA IF EXISTS salao CASCADE;