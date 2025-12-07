-- Script de inicialização do banco de dados
-- Este arquivo é executado automaticamente quando o container PostgreSQL é criado

-- Criar extensão para UUID se necessário
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criar database caso não exista (já é criado pelo POSTGRES_DB)
-- SELECT 'CREATE DATABASE ifoodlike' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'ifoodlike');

-- Configurações adicionais do banco
ALTER SYSTEM SET timezone TO 'America/Sao_Paulo';
