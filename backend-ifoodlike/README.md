# iFood-Like Backend API

Sistema de pedidos estilo iFood/McDonald's desenvolvido com Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Redis (cache)
- Flyway (migrations)
- Docker & Docker Compose
- OpenAPI/Swagger

## Arquitetura

Clean Architecture + DDD com:
- **model/** - Entidades de domínio
- **repository/** - Repositórios JPA
- **service/** - Use Cases
- **controller/** - REST endpoints
- **dtos/** - Objetos de transferência
- **event/** - Eventos de domínio

## Executando com Docker

```bash
# Na raiz do projeto backend-ifoodlike
docker-compose up -d --build
```

A API estará disponível em: `http://localhost:8080/api`

### Logs dos containers
```bash
docker-compose logs -f app-api
```

### Parar os containers
```bash
docker-compose down
```

### Remover volumes (limpar banco de dados)
```bash
docker-compose down -v
```

## Executando Localmente

1. Inicie PostgreSQL e Redis
2. Configure `application.yml`
3. Execute:
```bash
mvn spring-boot:run
```

## Endpoints Principais

### Autenticação
- `POST /api/auth/register` - Cadastro
- `POST /api/auth/login` - Login

### Cliente
- `GET /api/cliente/produtos` - Listar produtos
- `GET /api/cliente/categorias` - Listar categorias
- `POST /api/cliente/pedidos` - Criar pedido
- `GET /api/cliente/pedidos` - Listar pedidos

### Loja
- `POST /api/loja/produtos` - Cadastrar produto
- `GET /api/loja/pedidos` - Listar pedidos (cozinha)
- `PATCH /api/loja/pedidos/{id}/status` - Atualizar status

## Documentação

Swagger UI: `http://localhost:8080/api/swagger-ui.html`

## Usuários de Teste

| Email | Senha | Perfil |
|-------|-------|--------|
| admin@ifoodlike.com | admin123 | ADMIN |
| burguer@loja.com | loja123 | LOJA |
| cliente@email.com | cliente123 | CLIENTE |
