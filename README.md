# 🍔 Burger House - Sistema Completo de Delivery

Sistema completo de delivery de comida estilo iFood/McDonald's com backend em **Spring Boot** e frontend em **Next.js**.

## 🚀 Início Rápido (1 Comando!)

```powershell
# Na pasta raiz Ifood-
docker-compose up -d --build
```

**Pronto!** Tudo estará rodando:
- 🖥️ **Frontend:** http://localhost:3000
- 🔧 **Backend API:** http://localhost:8080/api
- 📚 **Swagger:** http://localhost:8080/api/swagger-ui.html
- 🗄️ **PostgreSQL:** localhost:5432
- ⚡ **Redis:** localhost:6379

## 📦 Containers

| Container | Porta | Descrição |
|-----------|-------|-----------|
| ifoodlike-frontend | 3000 | Next.js 14 + React |
| ifoodlike-backend | 8080 | Spring Boot 3.2 |
| ifoodlike-postgres | 5432 | PostgreSQL 15 |
| ifoodlike-redis | 6379 | Redis 7 |

## 🔑 Contas de Teste

### Cliente
- **Email:** cliente@email.com
- **Senha:** cliente123

### Loja
- **Email:** burguer@loja.com
- **Senha:** loja123

### Admin
- **Email:** admin@ifoodlike.com
- **Senha:** admin123

## 🛠️ Tecnologias

### Backend
- Java 17
- Spring Boot 3.2
  - Spring Web
  - Spring Data JPA
  - Spring Security + JWT
  - Spring Data Redis
- PostgreSQL 15
- Redis 7
- Flyway (migrations)
- OpenAPI/Swagger
- Docker

### Frontend
- Next.js 14 (App Router)
- React 18
- TypeScript
- Tailwind CSS
- Axios

## 📁 Estrutura do Projeto

```
Ifood-/
├── docker-compose.yml          # Docker Compose principal
├── backend-ifoodlike/          # Backend Spring Boot
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── docker-compose.yml      # Pode ser usado separadamente
├── frontend-ifoodlike/         # Frontend Next.js
│   ├── src/
│   ├── package.json
│   └── Dockerfile
└── README.md                   # Este arquivo
```

## 🎯 Funcionalidades

### Cliente
- ✅ Cadastro e Login (JWT)
- ✅ Visualizar cardápio
- ✅ Filtrar por categoria
- ✅ Buscar produtos
- ✅ Adicionar ao carrinho
- ✅ Finalizar pedido
- ✅ Acompanhar pedidos
- ✅ Histórico de pedidos

### Loja (via API/Swagger)
- ✅ Gerenciar produtos
- ✅ Visualizar pedidos da cozinha
- ✅ Atualizar status dos pedidos
- ✅ Filtrar pedidos por status

## 📋 Comandos Úteis

### Iniciar tudo
```bash
docker-compose up -d --build
```

### Ver logs
```bash
# Todos os serviços
docker-compose logs -f

# Apenas um serviço
docker-compose logs -f frontend-app
docker-compose logs -f backend-api
```

### Status dos containers
```bash
docker-compose ps
```

### Parar tudo
```bash
docker-compose down
```

### Resetar banco de dados (apagar dados)
```bash
docker-compose down -v
docker-compose up -d --build
```

### Reiniciar um serviço específico
```bash
docker-compose restart backend-api
docker-compose restart frontend-app
```

### Rebuild apenas um serviço
```bash
docker-compose up -d --build --no-deps backend-api
docker-compose up -d --build --no-deps frontend-app
```

## 🧪 Testando a Aplicação

### 1. Via Interface (Frontend)
1. Acesse: http://localhost:3000
2. Faça login com: `cliente@email.com` / `cliente123`
3. Navegue pelo cardápio
4. Adicione produtos ao carrinho
5. Finalize o pedido
6. Veja o histórico em "Meus Pedidos"

### 2. Via API (Swagger)
1. Acesse: http://localhost:8080/api/swagger-ui.html
2. Teste os endpoints:
   - POST `/auth/login` - Fazer login
   - GET `/cliente/produtos` - Listar produtos
   - POST `/cliente/pedidos` - Criar pedido
   - GET `/cliente/pedidos` - Listar pedidos

### 3. Via cURL
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"cliente@email.com","senha":"cliente123"}'

# Listar produtos
curl http://localhost:8080/api/cliente/produtos

# Listar categorias
curl http://localhost:8080/api/cliente/categorias
```

## 📊 Dados Iniciais

O banco é populado automaticamente via Flyway com:

### Categorias
- Lanches 🍔
- Bebidas 🥤
- Sobremesas 🍦
- Acompanhamentos 🍟
- Combos 🎁

### Produtos
- X-Burguer (R$ 18,90)
- X-Bacon (R$ 22,90)
- X-Tudo (R$ 28,90)
- Coca-Cola 350ml (R$ 6,00)
- Suco Natural 500ml (R$ 8,00)
- Milk-Shake Chocolate (R$ 12,00)
- Batata Frita P (R$ 8,00)
- Batata Frita G (R$ 14,00)
- Combo X-Burguer (R$ 29,90)

## 🔧 Configurações

### Variáveis de Ambiente

As variáveis já estão configuradas no `docker-compose.yml`, mas podem ser ajustadas:

**Backend:**
- `DB_HOST=postgres-db`
- `DB_PORT=5432`
- `DB_NAME=ifoodlike`
- `DB_USERNAME=postgres`
- `DB_PASSWORD=postgres`
- `REDIS_HOST=redis-cache`
- `REDIS_PORT=6379`
- `JWT_SECRET=<base64-secret>`

**Frontend:**
- `NEXT_PUBLIC_API_URL=http://localhost:8080/api`

## 🐛 Troubleshooting

### Containers não sobem
```bash
# Limpar tudo e recomeçar
docker-compose down -v
docker system prune -f
docker-compose up -d --build
```

### Frontend não conecta no backend
- Verifique se o backend está saudável: `docker-compose ps`
- Veja os logs: `docker-compose logs backend-api`
- O frontend espera o backend ficar "healthy" antes de iniciar

### Erro no banco de dados
```bash
# Resetar volumes
docker-compose down -v
docker volume rm ifood-_postgres_data
docker-compose up -d --build
```

### Porta já em uso
```bash
# Verificar o que está usando a porta
netstat -ano | findstr :3000
netstat -ano | findstr :8080

# Parar o processo ou alterar a porta no docker-compose.yml
```

## 📈 Arquitetura

```
┌─────────────┐      ┌─────────────┐
│   Browser   │─────>│  Frontend   │
│             │      │  (Next.js)  │
└─────────────┘      └──────┬──────┘
                            │ HTTP/REST
                            ▼
                     ┌──────────────┐
                     │   Backend    │
                     │ (Spring Boot)│
                     └──────┬───┬───┘
                            │   │
                ┌───────────┘   └──────────┐
                ▼                           ▼
         ┌─────────────┐            ┌─────────────┐
         │ PostgreSQL  │            │    Redis    │
         │   (Dados)   │            │   (Cache)   │
         └─────────────┘            └─────────────┘
```

## 📝 Clean Architecture

O backend segue **Clean Architecture + DDD**:

```
├── controller/    # REST endpoints (adapters)
├── service/       # Use Cases (application)
├── model/         # Entidades (domain)
├── repository/    # Repositórios (infrastructure)
├── dtos/          # Data Transfer Objects
├── event/         # Eventos de domínio
├── exception/     # Tratamento de erros
└── security/      # JWT & Spring Security
```

## 🚢 Deploy (Opcional)

O projeto está pronto para deploy em qualquer ambiente que suporte Docker Compose:

- AWS (ECS, EC2)
- Google Cloud (Cloud Run, GKE)
- Azure (Container Instances, AKS)
- DigitalOcean (App Platform)
- Heroku (com Docker)

## 📄 Licença

Projeto acadêmico - livre para uso educacional.

---

**Desenvolvido com ❤️ usando Spring Boot + Next.js**
