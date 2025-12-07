-- Tabela de usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de lojas
CREATE TABLE lojas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    nome_fantasia VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    endereco VARCHAR(500),
    telefone VARCHAR(20),
    ativa BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_loja_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de clientes
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    cpf VARCHAR(20),
    telefone VARCHAR(20),
    endereco VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Tabela de categorias de produtos
CREATE TABLE categorias_produto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    ativa BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de produtos
CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    loja_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(1000),
    preco DECIMAL(10, 2) NOT NULL,
    imagem_url VARCHAR(500),
    disponivel BOOLEAN NOT NULL DEFAULT TRUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_produto_loja FOREIGN KEY (loja_id) REFERENCES lojas(id),
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias_produto(id)
);

-- Tabela de pedidos
CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    loja_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    observacao VARCHAR(1000),
    endereco_entrega VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_pedido_loja FOREIGN KEY (loja_id) REFERENCES lojas(id)
);

-- Tabela de itens do pedido
CREATE TABLE itens_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    observacao VARCHAR(500),
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Índices para melhorar performance
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_lojas_usuario ON lojas(usuario_id);
CREATE INDEX idx_clientes_usuario ON clientes(usuario_id);
CREATE INDEX idx_produtos_loja ON produtos(loja_id);
CREATE INDEX idx_produtos_categoria ON produtos(categoria_id);
CREATE INDEX idx_pedidos_cliente ON pedidos(cliente_id);
CREATE INDEX idx_pedidos_loja ON pedidos(loja_id);
CREATE INDEX idx_pedidos_status ON pedidos(status);
CREATE INDEX idx_itens_pedido ON itens_pedido(pedido_id);
