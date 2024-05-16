-- Desativando as restrições de chave estrangeira para evitar erros ao excluir tabelas
ALTER TABLE IF EXISTS salao.agendamento DROP CONSTRAINT IF EXISTS agendamento_id_servico_fkey;
ALTER TABLE IF EXISTS salao.agendamento DROP CONSTRAINT IF EXISTS agendamento_id_cliente_fkey;
ALTER TABLE IF EXISTS salao.servico DROP CONSTRAINT IF EXISTS servico_id_profissional_fkey;
ALTER TABLE IF EXISTS salao.profissional_metodo_pagamento DROP CONSTRAINT IF EXISTS profissional_metodo_pagamento_id_metodo_pagamento_fkey;
ALTER TABLE IF EXISTS salao.profissional_metodo_pagamento DROP CONSTRAINT IF EXISTS profissional_metodo_pagamento_id_profissional_fkey;
ALTER TABLE IF EXISTS salao.estoque DROP CONSTRAINT IF EXISTS estoque_id_profissional_fkey;
ALTER TABLE IF EXISTS salao.produto DROP CONSTRAINT IF EXISTS produto_id_estoque_fkey;
ALTER TABLE IF EXISTS salao.avaliacao DROP CONSTRAINT IF EXISTS avaliacao_id_servico_fkey;
ALTER TABLE IF EXISTS salao.avaliacao DROP CONSTRAINT IF EXISTS avaliacao_id_cliente_fkey;

-- Deletando tabelas
DROP TABLE IF EXISTS salao.agendamento;
DROP TABLE IF EXISTS salao.avaliacao;
DROP TABLE IF EXISTS salao.cliente;
DROP TABLE IF EXISTS salao.estoque;
DROP TABLE IF EXISTS salao.metodo_pagamento;
DROP TABLE IF EXISTS salao.profissional_metodo_pagamento;
DROP TABLE IF EXISTS salao.produto;
DROP TABLE IF EXISTS salao.servico;
DROP TABLE IF EXISTS salao.profissional;

-- Deletando o esquema
DROP SCHEMA IF EXISTS salao CASCADE;
