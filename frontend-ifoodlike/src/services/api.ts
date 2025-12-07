import axios from 'axios';
import type { LoginResponse, Produto, Categoria, PedidoCriacao, DetalhePedido, PedidoResumo } from '@/types';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para adicionar token JWT
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auth
export const authAPI = {
  login: async (email: string, senha: string): Promise<LoginResponse> => {
    const response = await api.post<LoginResponse>('/auth/login', { email, senha });
    return response.data;
  },

  register: async (data: {
    nome: string;
    email: string;
    senha: string;
    perfil: string;
    cpf?: string;
    telefone?: string;
    endereco?: string;
  }): Promise<LoginResponse> => {
    const response = await api.post<LoginResponse>('/auth/register', data);
    return response.data;
  },
};

// Produtos
export const produtosAPI = {
  listar: async (lojaId?: number, categoriaId?: number): Promise<Produto[]> => {
    const params: any = {};
    if (lojaId) params.lojaId = lojaId;
    if (categoriaId) params.categoriaId = categoriaId;

    const response = await api.get<Produto[]>('/cliente/produtos', { params });
    return response.data;
  },
};

// Categorias
export const categoriasAPI = {
  listar: async (): Promise<Categoria[]> => {
    const response = await api.get<Categoria[]>('/cliente/categorias');
    return response.data;
  },
};

// Pedidos
export const pedidosAPI = {
  criar: async (pedido: PedidoCriacao): Promise<DetalhePedido> => {
    const response = await api.post<DetalhePedido>('/cliente/pedidos', pedido);
    return response.data;
  },

  listar: async (): Promise<PedidoResumo[]> => {
    const response = await api.get<PedidoResumo[]>('/cliente/pedidos');
    return response.data;
  },

  buscarPorId: async (id: number): Promise<DetalhePedido> => {
    const response = await api.get<DetalhePedido>(`/cliente/pedidos/${id}`);
    return response.data;
  },
};

export default api;
