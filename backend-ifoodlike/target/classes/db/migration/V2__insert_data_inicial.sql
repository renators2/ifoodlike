-- Inserir categorias de produtos
INSERT INTO categorias_produto (nome, descricao, ativa) VALUES
('Lanches', 'Hambúrgueres, sanduíches e wraps', true),
('Bebidas', 'Refrigerantes, sucos e água', true),
('Sobremesas', 'Doces, sorvetes e milk-shakes', true),
('Acompanhamentos', 'Batatas fritas, nuggets e onion rings', true),
('Combos', 'Combinações de lanches com bebidas e acompanhamentos', true);

-- Inserir usuário admin (senha: admin123)
INSERT INTO usuarios (email, senha, nome, perfil, ativo) VALUES
('admin@ifoodlike.com', '$2a$10$8YQw6CZaO.a4BXqzZZgJzeQZkb8TYgqFH1xqNFJCjvZC0hYoJVmfO', 'Administrador', 'ADMIN', true);

-- Inserir usuário loja de exemplo (senha: loja123)
INSERT INTO usuarios (email, senha, nome, perfil, ativo) VALUES
('burguer@loja.com', '$2a$10$8YQw6CZaO.a4BXqzZZgJzeQZkb8TYgqFH1xqNFJCjvZC0hYoJVmfO', 'Burguer House', 'LOJA', true);

-- Inserir loja de exemplo
INSERT INTO lojas (usuario_id, nome_fantasia, cnpj, endereco, telefone, ativa) VALUES
(2, 'Burguer House', '12.345.678/0001-90', 'Rua das Flores, 123 - Centro', '(11) 99999-1234', true);

-- Inserir produtos de exemplo
INSERT INTO produtos (loja_id, categoria_id, nome, descricao, preco, disponivel, ativo) VALUES
(1, 1, 'X-Burguer', 'Hambúrguer com queijo, alface, tomate e maionese', 18.90, true, true),
(1, 1, 'X-Bacon', 'Hambúrguer com queijo, bacon crocante, alface e maionese', 22.90, true, true),
(1, 1, 'X-Tudo', 'Hambúrguer completo com queijo, bacon, ovo, presunto, alface, tomate e maionese', 28.90, true, true),
(1, 2, 'Coca-Cola 350ml', 'Refrigerante Coca-Cola lata', 6.00, true, true),
(1, 2, 'Suco Natural 500ml', 'Suco de laranja natural', 8.00, true, true),
(1, 3, 'Milk-Shake Chocolate', 'Milk-shake cremoso de chocolate', 12.00, true, true),
(1, 4, 'Batata Frita P', 'Porção pequena de batata frita', 8.00, true, true),
(1, 4, 'Batata Frita G', 'Porção grande de batata frita', 14.00, true, true),
(1, 5, 'Combo X-Burguer', 'X-Burguer + Batata P + Refrigerante', 29.90, true, true);

-- Inserir usuário cliente de exemplo (senha: cliente123)
INSERT INTO usuarios (email, senha, nome, perfil, ativo) VALUES
('cliente@email.com', '$2a$10$8YQw6CZaO.a4BXqzZZgJzeQZkb8TYgqFH1xqNFJCjvZC0hYoJVmfO', 'João Silva', 'CLIENTE', true);

-- Inserir cliente de exemplo
INSERT INTO clientes (usuario_id, cpf, telefone, endereco) VALUES
(3, '123.456.789-00', '(11) 98888-5678', 'Av. Principal, 456 - Bairro');
