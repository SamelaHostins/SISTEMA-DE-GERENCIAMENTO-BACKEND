-- 🔹 Extensão necessária para geração automática de UUIDs
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 🔹 Inserir dados na tabela Endereco
INSERT INTO salao.endereco (rua, bairro, cidade, numero, cep) 
VALUES 
  ('Rua das Acácias', 'Centro', 'Florianópolis', 200, '88000-001'), -- Endereço Ricardo
  ('Rua das Palmeiras', 'Centro', 'São Paulo', 110, '04567-890'), -- Endereço Fernanda
  ('Rua dos Pinheiros', 'Zona Sul', 'Rio de Janeiro', 50, '22222-222'), -- Endereço Ana
  ('Avenida Paulista', 'Centro', 'São Paulo', 800, '01311-000'); -- Endereço Bruno

-- 🔹 Inserir dados na tabela Cliente (com CPF/CNPJ)
INSERT INTO salao.cliente (id_cliente, nome, sobrenome, data_nascimento, email, telefone, usuario, senha, documento) 
VALUES 
  (gen_random_uuid(), 'Ana', 'Silva', '1996-04-15', 'ana.silva@example.com', '12345678901', 'ana.silva', 'Senha123', '123.456.789-01'),

  (gen_random_uuid(), 'Bruno', 'Oliveira', '1990-09-10', 'bruno.oliveira@example.com', '23456789012', 'bruno.oliveira', 'Bruno567', '12.345.678/0001-95');

-- 🔹 Inserir dados na tabela Profissional (com CPF/CNPJ e Endereço)
INSERT INTO salao.profissional (id_profissional, profissao, nome, sobrenome, data_nascimento, email, telefone, usuario, senha, documento, id_endereco) 
VALUES 
  (gen_random_uuid(), 'Cabeleireiro', 'Ricardo', 'Santos Oliveira', '1984-07-22', 'ricardo.oliveira@example.com', '47987654321', 'ricardoOliveira', 'Senha123', '987.654.321-00',
   (SELECT id_endereco FROM salao.endereco WHERE rua = 'Rua das Acácias' LIMIT 1)),

  (gen_random_uuid(), 'Maquiadora', 'Fernanda', 'Lima Souza', '1992-03-18', 'fernanda.lima@example.com', '31999987654', 'fernandaLima', 'Senha568', '98.765.432/0001-22',
   (SELECT id_endereco FROM salao.endereco WHERE rua = 'Rua das Palmeiras' LIMIT 1));

-- 🔹 Inserir dados na tabela Metodo Pagamento
INSERT INTO salao.metodo_pagamento (nome) 
VALUES 
  ('Cartão de Crédito'), 
  ('Dinheiro'), 
  ('PIX');

-- 🔹 Inserir relação entre Profissional e Método de Pagamento
INSERT INTO salao.profissional_metodo_pagamento (id_profissional, id_metodo_pagamento) 
VALUES 
  ((SELECT id_profissional FROM salao.profissional WHERE nome = 'Ricardo' LIMIT 1), 
   (SELECT id_metodo_pagamento FROM salao.metodo_pagamento WHERE nome = 'Cartão de Crédito')),

  ((SELECT id_profissional FROM salao.profissional WHERE nome = 'Fernanda' LIMIT 1), 
   (SELECT id_metodo_pagamento FROM salao.metodo_pagamento WHERE nome = 'PIX'));

-- 🔹 Inserir dados na tabela Estoque
INSERT INTO salao.estoque (nome, qtd_de_produtos, id_profissional) 
VALUES 
  ('Estoque Ricardo', 10, (SELECT id_profissional FROM salao.profissional WHERE nome = 'Ricardo' LIMIT 1)),
  ('Estoque Fernanda', 15, (SELECT id_profissional FROM salao.profissional WHERE nome = 'Fernanda' LIMIT 1));

-- 🔹 Inserir dados na tabela Produto
INSERT INTO salao.produto (nome, dt_entrada, dt_validade, valor, id_estoque) 
VALUES 
  ('Shampoo Profissional', '2024-05-05', '2025-12-31', 30.99, (SELECT id_estoque FROM salao.estoque WHERE nome = 'Estoque Ricardo' LIMIT 1)),
  ('Base para Maquiagem', '2024-05-06', '2025-12-31', 45.99, (SELECT id_estoque FROM salao.estoque WHERE nome = 'Estoque Fernanda' LIMIT 1));

-- 🔹 Inserir dados na tabela Servico
INSERT INTO salao.servico (tipo_servico, nome, especificacao, termos_e_condicoes, tempo, valor, id_profissional) 
VALUES 
  (0, 'Corte Masculino', 'Corte de cabelo profissional', 'Termos padrão', '00:30', 80.00, 
   (SELECT id_profissional FROM salao.profissional WHERE nome = 'Ricardo' LIMIT 1)),

  (0, 'Corte Feminino', 'Corte de cabelo para mulheres', 'Termos padrão', '00:45', 100.00, 
   (SELECT id_profissional FROM salao.profissional WHERE nome = 'Ricardo' LIMIT 1)),

  (1, 'Maquiagem Completa', 'Maquiagem para eventos e festas', 'Termos padrão', '01:00', 150.00, 
   (SELECT id_profissional FROM salao.profissional WHERE nome = 'Fernanda' LIMIT 1)),

  (1, 'Maquiagem Noiva', 'Maquiagem especial para noivas', 'Termos padrão', '01:30', 250.00, 
   (SELECT id_profissional FROM salao.profissional WHERE nome = 'Fernanda' LIMIT 1));

-- 🔹 Inserir dados na tabela Avaliacao
INSERT INTO salao.avaliacao (nota, dt_avaliacao, id_cliente, id_servico) 
VALUES 
  (5, CURRENT_DATE, (SELECT id_cliente FROM salao.cliente WHERE nome = 'Ana' LIMIT 1), 
   (SELECT id_servico FROM salao.servico WHERE nome = 'Corte Masculino' LIMIT 1)),

  (4, CURRENT_DATE, (SELECT id_cliente FROM salao.cliente WHERE nome = 'Bruno' LIMIT 1), 
   (SELECT id_servico FROM salao.servico WHERE nome = 'Maquiagem Completa' LIMIT 1));

-- 🔹 Inserir dados na tabela Agendamento
INSERT INTO salao.agendamento (dt_agendamento, hora_agendamento, status_agendamento, id_cliente, id_servico) 
VALUES 
  (CURRENT_DATE + INTERVAL '7 days', '14:00', 1, 
   (SELECT id_cliente FROM salao.cliente WHERE nome = 'Ana' LIMIT 1), 
   (SELECT id_servico FROM salao.servico WHERE nome = 'Corte Masculino' LIMIT 1)),

  (CURRENT_DATE + INTERVAL '7 days', '15:00', 1, 
   (SELECT id_cliente FROM salao.cliente WHERE nome = 'Bruno' LIMIT 1), 
   (SELECT id_servico FROM salao.servico WHERE nome = 'Maquiagem Completa' LIMIT 1));
