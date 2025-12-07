# Build stage
FROM node:18-alpine AS builder
WORKDIR /app

# Copiar arquivos de dependências
COPY package*.json ./
RUN npm install

# Copiar código fonte
COPY . .

# Build da aplicação
RUN npm run build

# Run stage
FROM node:18-alpine AS runner
WORKDIR /app

# Criar usuário não-root
RUN addgroup -S nodejs && adduser -S nextjs -G nodejs

# Copiar arquivos necessários
COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

# Mudar para usuário não-root
USER nextjs

# Expor porta
EXPOSE 3000

# Variáveis de ambiente
ENV NODE_ENV=production
ENV PORT=3000

# Iniciar aplicação
CMD ["node", "server.js"]
