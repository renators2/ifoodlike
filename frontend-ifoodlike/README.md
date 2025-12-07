# 🍔 Burger House - Frontend

Frontend em **React + Next.js 14** com **TypeScript** e **Tailwind CSS** para o sistema de delivery de comida.

## Tecnologias

- **Next.js 14** (App Router)
- **React 18**
- **TypeScript**
- **Tailwind CSS**
- **Axios** (comunicação com API REST)
- **Lucide React** (ícones)

## Funcionalidades

### Cliente
- ✅ Login e Cadastro
- ✅ Visualização de produtos por categoria
- ✅ Busca de produtos
- ✅ Carrinho de compras
- ✅ Finalização de pedido
- ✅ Histórico de pedidos
- ✅ Acompanhamento de status

### Design
- 🎨 Tema McDonald's (vermelho #DA291C e amarelo #FFC72C)
- 🍔 Ícones temáticos (hambúrguer, batata, refrigerante, sorvete, etc.)
- 📱 Responsivo (desktop, tablet, mobile)
- ✨ Animações e transições suaves

## Instalação

```bash
# Instalar dependências
npm install

# Configurar variável de ambiente
# Crie o arquivo .env.local com:
NEXT_PUBLIC_API_URL=http://localhost:8080/api

# Executar em desenvolvimento
npm run dev

# Acessar aplicação
# http://localhost:3000
```

## Estrutura

```
src/
├── app/                    # Páginas Next.js (App Router)
│   ├── page.tsx           # Home (cardápio)
│   ├── login/             # Login
│   ├── register/          # Cadastro
│   ├── checkout/          # Finalizar pedido
│   └── pedidos/           # Histórico de pedidos
├── components/            # Componentes reutilizáveis
├── services/              # API services (axios)
│   └── api.ts            # Endpoints REST
├── types/                 # TypeScript interfaces
│   └── index.ts          # Tipos do domínio
└── utils/                 # Utilitários
```

## Contas de Teste

### Cliente
- **Email:** cliente@email.com
- **Senha:** cliente123

### Loja
- **Email:** burguer@loja.com
- **Senha:** loja123

## Páginas

### 1. Login (`/login`)
- Autenticação JWT
- Validação de credenciais
- Redirecionamento após login

### 2. Cadastro (`/register`)
- Criação de conta cliente
- Campos: nome, email, senha, CPF, telefone, endereço

### 3. Home (`/`)
- Listagem de produtos
- Filtro por categoria
- Busca de produtos
- Carrinho de compras (sidebar)
- Adicionar/remover itens

### 4. Checkout (`/checkout`)
- Resumo do pedido
- Endereço de entrega
- Observações
- Confirmação e envio

### 5. Pedidos (`/pedidos`)
- Histórico de pedidos
- Status em tempo real
- Detalhes de cada pedido

## API Endpoints Consumidos

```typescript
// Auth
POST   /auth/login       - Login
POST   /auth/register    - Cadastro

// Produtos
GET    /cliente/produtos - Listar produtos

// Categorias
GET    /cliente/categorias - Listar categorias

// Pedidos
POST   /cliente/pedidos  - Criar pedido
GET    /cliente/pedidos  - Listar pedidos
GET    /cliente/pedidos/:id - Detalhe do pedido
```

## Cores do Tema

```css
--mcdonalds-red: #DA291C
--mcdonalds-yellow: #FFC72C
--mcdonalds-dark: #27251F
```

## Ícones por Categoria

| Categoria | Ícone |
|-----------|-------|
| Lanches | 🍔 |
| Bebidas | 🥤 |
| Sobremesas | 🍦 |
| Acompanhamentos | 🍟 |
| Combos | 🎁 |

## Build para Produção

```bash
npm run build
npm start
```

## Integração com Backend

O frontend consome a API REST do backend Spring Boot rodando em `http://localhost:8080/api`.

**Certifique-se de:**
1. Backend está rodando (docker-compose up)
2. Variável `NEXT_PUBLIC_API_URL` está configurada
3. CORS está habilitado no backend para `http://localhost:3000`
