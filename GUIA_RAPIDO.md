# 🚀 Guia Rápido - Burger House

## Passo 1: Subir o Backend

```powershell
# Navegue até a pasta do backend
cd C:\Users\Renato\Downloads\Ifood-\backend-ifoodlike

# Suba os containers
docker-compose up -d

# Verifique se está rodando
docker-compose logs -f app-api
```

**Backend estará em:** http://localhost:8080/api
**Swagger:** http://localhost:8080/api/swagger-ui.html

## Passo 2: Instalar e Rodar o Frontend

```powershell
# Navegue até a pasta do frontend
cd C:\Users\Renato\Downloads\Ifood-\frontend-ifoodlike

# Instalar dependências (primeira vez apenas)
npm install

# Rodar o frontend
npm run dev
```

**Frontend estará em:** http://localhost:3000

## Passo 3: Testar a Aplicação

### Opção 1: Usar Conta de Teste

1. Acesse http://localhost:3000/login
2. Use as credenciais:
   - **Email:** cliente@email.com
   - **Senha:** cliente123

### Opção 2: Criar Nova Conta

1. Acesse http://localhost:3000/register
2. Preencha o formulário
3. Após criar, você será automaticamente logado

## Fluxo Completo

1. **Login** → Entre com email e senha
2. **Cardápio** → Visualize os produtos
3. **Filtrar** → Clique nas categorias (Lanches, Bebidas, etc.)
4. **Buscar** → Use a barra de busca
5. **Adicionar ao Carrinho** → Clique em "Adicionar"
6. **Ajustar Quantidade** → Use os botões + e - no carrinho
7. **Finalizar Pedido** → Clique em "Finalizar Pedido"
8. **Checkout** → Informe endereço e observações
9. **Confirmar** → Clique em "Confirmar Pedido"
10. **Acompanhar** → Veja seus pedidos em "Meus Pedidos"

## URLs Importantes

| Serviço | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| Swagger | http://localhost:8080/api/swagger-ui.html |
| PostgreSQL | localhost:5432 |
| Redis | localhost:6379 |

## Comandos Úteis

### Backend
```bash
# Ver logs
docker-compose logs -f app-api

# Parar containers
docker-compose down

# Resetar banco de dados
docker-compose down -v
docker-compose up -d
```

### Frontend
```bash
# Desenvolvimento
npm run dev

# Build para produção
npm run build
npm start

# Limpar cache
rm -rf .next node_modules
npm install
```

## Estrutura de Dados

### Produtos Pré-cadastrados
- X-Burguer - R$ 18,90
- X-Bacon - R$ 22,90
- X-Tudo - R$ 28,90
- Coca-Cola 350ml - R$ 6,00
- Suco Natural 500ml - R$ 8,00
- Milk-Shake Chocolate - R$ 12,00
- Batata Frita P - R$ 8,00
- Batata Frita G - R$ 14,00
- Combo X-Burguer - R$ 29,90

### Categorias
- Lanches 🍔
- Bebidas 🥤
- Sobremesas 🍦
- Acompanhamentos 🍟
- Combos 🎁

## Troubleshooting

### Frontend não conecta no backend
- Verifique se o backend está rodando: `docker-compose ps`
- Verifique a variável `.env.local`: `NEXT_PUBLIC_API_URL=http://localhost:8080/api`

### Erro de CORS
- Backend já está configurado para aceitar `localhost:3000`
- Reinicie o backend: `docker-compose restart app-api`

### Produtos não aparecem
- Verifique os logs: `docker-compose logs app-api`
- O Flyway já insere dados iniciais automaticamente

### Erro ao fazer login
- Verifique se está usando as credenciais corretas
- Senha mínima: 6 caracteres
- Veja os logs da API

## Stack Completa

### Backend
- ✅ Java 17
- ✅ Spring Boot 3.2
- ✅ PostgreSQL
- ✅ Redis
- ✅ JWT
- ✅ Flyway
- ✅ Docker

### Frontend
- ✅ Next.js 14
- ✅ React 18
- ✅ TypeScript
- ✅ Tailwind CSS
- ✅ Axios

## Próximos Passos

1. ✅ Backend funcionando
2. ✅ Frontend funcionando
3. ✅ Integração completa
4. 📝 Testar todos os fluxos
5. 🎨 Personalizar cores/tema (opcional)
6. 📱 Testar responsividade
7. 🚀 Deploy (opcional)
