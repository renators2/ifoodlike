export interface Usuario {
  id: number;
  email: string;
  nome: string;
  perfil: 'CLIENTE' | 'LOJA' | 'ADMIN';
}

export interface LoginResponse {
  token: string;
  tipo: string;
  usuarioId: number;
  email: string;
  nome: string;
  perfil: string;
}

export interface Produto {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  imagemUrl?: string;
  categoriaNome: string;
  lojaId: number;
  lojaNome: string;
}

export interface Categoria {
  id: number;
  nome: string;
  descricao: string;
}

export interface ItemPedido {
  produtoId: number;
  quantidade: number;
  observacao?: string;
}

export interface PedidoCriacao {
  lojaId: number;
  itens: ItemPedido[];
  observacao?: string;
  enderecoEntrega?: string;
}

export interface PedidoResumo {
  id: number;
  lojaNome: string;
  status: StatusPedido;
  total: number;
  quantidadeItens: number;
  createdAt: string;
}

export interface DetalhePedido {
  id: number;
  lojaId: number;
  lojaNome: string;
  status: StatusPedido;
  total: number;
  observacao?: string;
  enderecoEntrega?: string;
  itens: ItemPedidoDetalhes[];
  createdAt: string;
  updatedAt: string;
}

export interface ItemPedidoDetalhes {
  id: number;
  produtoNome: string;
  quantidade: number;
  precoUnitario: number;
  subtotal: number;
  observacao?: string;
}

export type StatusPedido = 'CRIADO' | 'EM_PREPARO' | 'PRONTO' | 'ENTREGUE' | 'CANCELADO';

export interface CartItem extends Produto {
  quantity: number;
  observacao?: string;
}
