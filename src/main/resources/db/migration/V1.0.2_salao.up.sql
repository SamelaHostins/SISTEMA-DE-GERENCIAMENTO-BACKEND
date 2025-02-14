
CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE salao.agendamento 
ALTER COLUMN id_agendamento SET DEFAULT gen_random_uuid();

ALTER TABLE salao.avaliacao 
ALTER COLUMN id_avaliacao SET DEFAULT gen_random_uuid();

ALTER TABLE salao.cliente 
ALTER COLUMN id_cliente SET DEFAULT gen_random_uuid();

ALTER TABLE salao.endereco 
ALTER COLUMN id_endereco SET DEFAULT gen_random_uuid();

ALTER TABLE salao.estoque 
ALTER COLUMN id_estoque SET DEFAULT gen_random_uuid();

ALTER TABLE salao.imagem 
ALTER COLUMN id_imagem SET DEFAULT gen_random_uuid();

ALTER TABLE salao.metodo_pagamento 
ALTER COLUMN id_metodo_pagamento SET DEFAULT gen_random_uuid();

ALTER TABLE salao.produto 
ALTER COLUMN id_produto SET DEFAULT gen_random_uuid();

ALTER TABLE salao.profissional 
ALTER COLUMN id_profissional SET DEFAULT gen_random_uuid();

ALTER TABLE salao.servico 
ALTER COLUMN id_servico SET DEFAULT gen_random_uuid();
