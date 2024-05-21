-- Inserir dados na tabela Cliente
INSERT INTO salao.cliente (id_cliente, especial, nome, sobrenome, idade, email, telefone, senha) 
VALUES 
  ('234e5678-e89b-12d3-a456-426614174001', false, 'Ana', 'Silva', 28, 'ana.silva@example.com', '12345678901', 'Senha1234'),
  ('334e5678-e89b-12d3-a456-426614174002', true, 'Bruno', 'Oliveira', 34, 'bruno.oliveira@example.com', '23456789012', 'Senha1234'),
  ('434e5678-e89b-12d3-a456-426614174003', false, 'Carla', 'Souza', 25, 'carla.souza@example.com', '34567890123', 'Senha1234'),
  ('534e5678-e89b-12d3-a456-426614174004', true, 'Diego', 'Pereira', 30, 'diego.pereira@example.com', '45678901234', 'Senha1234'),
  ('634e5678-e89b-12d3-a456-426614174005', false, 'Elisa', 'Lima', 22, 'elisa.lima@example.com', '56789012345', 'Senha1234');

-- Inserir dados na tabela Profissional
INSERT INTO salao.profissional (id_profissional, metodos_de_pagamento, rua, bairro, cidade, numero, cep) 
VALUES 
  ('345e6789-e89b-12d3-a456-426614174002', 'Cartão de crédito', '123e4567-e89b-12d3-a456-426614174000', 'Rua A', 'Bairro X', 'Cidade Y', 123, '12345-678'),
  ('445e6789-e89b-12d3-a456-426614174003', 'Dinheiro', '223e4567-e89b-12d3-a456-426614174001', 'Rua B', 'Bairro Y', 'Cidade Z', 456, '23456-789'),
  ('545e6789-e89b-12d3-a456-426614174004', 'Transferência bancária', '323e4567-e89b-12d3-a456-426614174002', 'Rua C', 'Bairro Z', 'Cidade X', 789, '34567-890'),
  ('645e6789-e89b-12d3-a456-426614174005', 'PIX', '423e4567-e89b-12d3-a456-426614174003', 'Rua D', 'Bairro W', 'Cidade Y', 1011, '45678-901'),
  ('745e6789-e89b-12d3-a456-426614174006', 'Boleto', '523e4567-e89b-12d3-a456-426614174004', 'Rua E', 'Bairro V', 'Cidade Z', 1213, '56789-012');

-- Inserir dados na tabela Estoque
INSERT INTO salao.estoque (id_estoque, nome, qtd_de_produtos, id_profissional) 
VALUES 
  ('456e7890-e89b-12d3-a456-426614174003', 'Estoque 1', 1, '345e6789-e89b-12d3-a456-426614174002'),
  ('556e7890-e89b-12d3-a456-426614174004', 'Estoque 2', 1, '445e6789-e89b-12d3-a456-426614174003'),
  ('656e7890-e89b-12d3-a456-426614174005', 'Estoque 3', 1, '545e6789-e89b-12d3-a456-426614174004'),
  ('756e7890-e89b-12d3-a456-426614174006', 'Estoque 4', 1, '645e6789-e89b-12d3-a456-426614174005'),
  ('856e7890-e89b-12d3-a456-426614174007', 'Estoque 5', 1, '745e6789-e89b-12d3-a456-426614174006');

-- Inserir dados na tabela Produto
INSERT INTO salao.produto (id_produto, nome, dt_entrada, dt_validade, valor, id_estoque) 
VALUES 
  ('567e8901-e89b-12d3-a456-426614174004', 'Produto 1', '2024-05-05', '2024-12-31', 10.99, '456e7890-e89b-12d3-a456-426614174003'),
  ('667e8901-e89b-12d3-a456-426614174005', 'Produto 2', '2024-05-06', '2024-12-31', 20.99, '556e7890-e89b-12d3-a456-426614174004'),
  ('767e8901-e89b-12d3-a456-426614174006', 'Produto 3', '2024-05-07', '2024-12-31', 15.99, '656e7890-e89b-12d3-a456-426614174005'),
  ('867e8901-e89b-12d3-a456-426614174007', 'Produto 4', '2024-05-08', '2024-12-31', 25.99, '756e7890-e89b-12d3-a456-426614174006'),
  ('967e8901-e89b-12d3-a456-426614174008', 'Produto 5', '2024-05-09', '2024-12-31', 30.99, '856e7890-e89b-12d3-a456-426614174007');

-- Inserir dados na tabela Servico
INSERT INTO salao.servico (id_servico, nome, especificacao, termos_e_condicoes, valor, id_profissional) 
VALUES 
  ('678e9012-e89b-12d3-a456-426614174005', 'Serviço 1', 'Especificação do Serviço 1', 'Termos e Condições do Serviço 1', 50.00, '345e6789-e89b-12d3-a456-426614174002'),
  ('778e9012-e89b-12d3-a456-426614174006', 'Serviço 2', 'Especificação do Serviço 2', 'Termos e Condições do Serviço 2', 60.00, '445e6789-e89b-12d3-a456-426614174003'),
  ('878e9012-e89b-12d3-a456-426614174007', 'Serviço 3', 'Especificação do Serviço 3', 'Termos e Condições do Serviço 3', 70.00, '545e6789-e89b-12d3-a456-426614174004'),
  ('978e9012-e89b-12d3-a456-426614174008', 'Serviço 4', 'Especificação do Serviço 4', 'Termos e Condições do Serviço 4', 80.00, '645e6789-e89b-12d3-a456-426614174005'),
  ('a78e9012-e89b-12d3-a456-426614174009', 'Serviço 5', 'Especificação do Serviço 5', 'Termos e Condições do Serviço 5', 90.00, '745e6789-e89b-12d3-a456-426614174006');

-- Inserir dados na tabela profissional_metodo_pagamento
INSERT INTO salao.profissional_metodo_pagamento (id_profissional, id_metodo_pagamento) 
VALUES 
  ('345e6789-e89b-12d3-a456-426614174002', '123e4567-e89b-12d3-a456-426614174000'),  -- Relacionando o profissional com Cartão de crédito
  ('445e6789-e89b-12d3-a456-426614174003', '223e4567-e89b-12d3-a456-426614174001'),  -- Relacionando o profissional com Dinheiro
 
-- Inserir dados na tabela Avaliacao
INSERT INTO salao.avaliacao (id_avaliacao, nota, dt_avaliacao, id_cliente, id_servico) 
VALUES 
  ('789e0123-e89b-12d3-a456-426614174006', 5, '2024-07-20', '234e5678-e89b-12d3-a456-426614174001', '678e9012-e89b-12d3-a456-426614174005'),
  ('889e0123-e89b-12d3-a456-426614174007', 4, '2024-07-20', '334e5678-e89b-12d3-a456-426614174002', '778e9012-e89b-12d3-a456-426614174006'),
  ('989e0123-e89b-12d3-a456-426614174008', 3, '2024-07-20', '434e5678-e89b-12d3-a456-426614174003', '878e9012-e89b-12d3-a456-426614174007'),
  ('a89e0123-e89b-12d3-a456-426614174009', 2, '2024-07-20', '534e5678-e89b-12d3-a456-426614174004', '978e9012-e89b-12d3-a456-426614174008'),
  ('b89e0123-e89b-12d3-a456-426614174010', 1, '2024-07-20', '634e5678-e89b-12d3-a456-426614174005', 'a78e9012-e89b-12d3-a456-426614174009');

-- Inserir dados na tabela Agendamento
-- INSERT INTO salao.agendamento (id_agendamento, nome, hora, id_cliente, id_servico) 
-- VALUES 
--   ('890e1234-e89b-12d3-a456-426614174007', 'Agendamento 1', 14, '234e5678-e89b-12d3-a456-426614174001', '678e9012-e89b-12d3-a456-426614174005'),
--   ('990e1234-e89b-12d3-a456-426614174008', 'Agendamento 2', 15, '334e5678-e89b-12d3-a456-426614174002', '778e9012-e89b-12d3-a456-426614174006'),
--   ('a90e1234-e89b-12d3-a456-426614174009', 'Agendamento 3', 16, '434e5678-e89b-12d3-a456-426614174003', '878e9012-e89b-12d3-a456-426614174007'),
--   ('b90e1234-e89b-12d3-a456-426614174010', 'Agendamento 4', 17, '534e5678-e89b-12d3-a456-426614174004', '978e9012-e89b-12d3-a456-426614174008'),
--   ('c90e1234-e89b-12d3-a456-426614174011', 'Agendamento 5', 18, '634e5678-e89b-12d3-a456-426614174005', 'a78e9012-e89b-12d3-a456-426614174009');