'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { produtosAPI, categoriasAPI } from '@/services/api';
import type { Produto, Categoria, CartItem } from '@/types';
import { ShoppingCart, User, LogOut, Search } from 'lucide-react';

export default function Home() {
  const router = useRouter();
  const [produtos, setProdutos] = useState<Produto[]>([]);
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [categoriaAtiva, setCategoriaAtiva] = useState<number | null>(null);
  const [carrinho, setCarrinho] = useState<CartItem[]>([]);
  const [busca, setBusca] = useState('');
  const [usuario, setUsuario] = useState<string | null>(null);

  useEffect(() => {
    const nome = localStorage.getItem('nomeUsuario');
    const token = localStorage.getItem('token');
    setUsuario(nome);

    if (!token) {
      router.push('/login');
      return;
    }

    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const [produtosData, categoriasData] = await Promise.all([
        produtosAPI.listar(),
        categoriasAPI.listar(),
      ]);
      setProdutos(produtosData);
      setCategorias(categoriasData);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
    }
  };

  const filtrarProdutos = () => {
    let produtosFiltrados = produtos;

    if (categoriaAtiva) {
      produtosFiltrados = produtosFiltrados.filter(
        (p) => p.categoriaNome === categorias.find((c) => c.id === categoriaAtiva)?.nome
      );
    }

    if (busca) {
      produtosFiltrados = produtosFiltrados.filter((p) =>
        p.nome.toLowerCase().includes(busca.toLowerCase())
      );
    }

    return produtosFiltrados;
  };

  const adicionarAoCarrinho = (produto: Produto) => {
    const itemExistente = carrinho.find((item) => item.id === produto.id);

    if (itemExistente) {
      setCarrinho(
        carrinho.map((item) =>
          item.id === produto.id ? { ...item, quantity: item.quantity + 1 } : item
        )
      );
    } else {
      setCarrinho([...carrinho, { ...produto, quantity: 1 }]);
    }
  };

  const removerDoCarrinho = (produtoId: number) => {
    const item = carrinho.find((i) => i.id === produtoId);
    if (item && item.quantity > 1) {
      setCarrinho(
        carrinho.map((i) =>
          i.id === produtoId ? { ...i, quantity: i.quantity - 1 } : i
        )
      );
    } else {
      setCarrinho(carrinho.filter((i) => i.id !== produtoId));
    }
  };

  const calcularTotal = () => {
    return carrinho.reduce((total, item) => total + item.preco * item.quantity, 0);
  };

  const finalizarPedido = () => {
    if (carrinho.length === 0) {
      alert('Adicione itens ao carrinho primeiro!');
      return;
    }
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
    router.push('/checkout');
  };

  const logout = () => {
    localStorage.clear();
    router.push('/login');
  };

  const produtosFiltrados = filtrarProdutos();

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-mcdonalds-red text-white shadow-lg">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <h1 className="text-3xl font-bold">🍔 Burger House</h1>
            </div>
            <div className="flex items-center space-x-6">
              <div className="flex items-center space-x-2">
                <User size={20} />
                <span>{usuario}</span>
              </div>
              <button onClick={logout} className="flex items-center space-x-2 hover:text-mcdonalds-yellow transition-colors">
                <LogOut size={20} />
                <span>Sair</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      <div className="container mx-auto px-4 py-8">
        {/* Busca */}
        <div className="mb-8">
          <div className="relative max-w-xl mx-auto">
            <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
            <input
              type="text"
              placeholder="Buscar produtos..."
              value={busca}
              onChange={(e) => setBusca(e.target.value)}
              className="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-mcdonalds-red"
            />
          </div>
        </div>

        {/* Categorias */}
        <div className="mb-8 overflow-x-auto">
          <div className="flex space-x-4">
            <button
              onClick={() => setCategoriaAtiva(null)}
              className={`px-6 py-2 rounded-full font-semibold whitespace-nowrap ${
                categoriaAtiva === null
                  ? 'bg-mcdonalds-red text-white'
                  : 'bg-white text-gray-700 hover:bg-gray-100'
              }`}
            >
              Todos
            </button>
            {categorias.map((cat) => (
              <button
                key={cat.id}
                onClick={() => setCategoriaAtiva(cat.id)}
                className={`px-6 py-2 rounded-full font-semibold whitespace-nowrap ${
                  categoriaAtiva === cat.id
                    ? 'bg-mcdonalds-red text-white'
                    : 'bg-white text-gray-700 hover:bg-gray-100'
                }`}
              >
                {cat.nome}
              </button>
            ))}
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Produtos */}
          <div className="lg:col-span-2">
            <h2 className="text-2xl font-bold mb-6">Cardápio</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {produtosFiltrados.map((produto) => (
                <div key={produto.id} className="card">
                  <div className="h-48 bg-gradient-to-br from-orange-400 to-red-500 flex items-center justify-center">
                    <span className="text-7xl">{getIcon(produto.categoriaNome)}</span>
                  </div>
                  <div className="p-4">
                    <h3 className="text-xl font-bold mb-2">{produto.nome}</h3>
                    <p className="text-gray-600 text-sm mb-3">{produto.descricao}</p>
                    <div className="flex items-center justify-between">
                      <span className="text-2xl font-bold text-mcdonalds-red">
                        R$ {produto.preco.toFixed(2)}
                      </span>
                      <button
                        onClick={() => adicionarAoCarrinho(produto)}
                        className="btn-primary text-sm py-2 px-4"
                      >
                        Adicionar
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Carrinho */}
          <div className="lg:col-span-1">
            <div className="card sticky top-4">
              <div className="p-6">
                <div className="flex items-center justify-between mb-6">
                  <h2 className="text-2xl font-bold flex items-center">
                    <ShoppingCart className="mr-2" />
                    Carrinho
                  </h2>
                  <span className="bg-mcdonalds-red text-white rounded-full w-8 h-8 flex items-center justify-center font-bold">
                    {carrinho.reduce((acc, item) => acc + item.quantity, 0)}
                  </span>
                </div>

                {carrinho.length === 0 ? (
                  <p className="text-gray-500 text-center py-8">Carrinho vazio</p>
                ) : (
                  <>
                    <div className="space-y-4 mb-6 max-h-96 overflow-y-auto">
                      {carrinho.map((item) => (
                        <div key={item.id} className="flex items-center justify-between border-b pb-3">
                          <div className="flex-1">
                            <h4 className="font-semibold">{item.nome}</h4>
                            <p className="text-sm text-gray-600">R$ {item.preco.toFixed(2)}</p>
                          </div>
                          <div className="flex items-center space-x-2">
                            <button
                              onClick={() => removerDoCarrinho(item.id)}
                              className="w-8 h-8 bg-gray-200 rounded-full hover:bg-gray-300 font-bold"
                            >
                              -
                            </button>
                            <span className="w-8 text-center font-bold">{item.quantity}</span>
                            <button
                              onClick={() => adicionarAoCarrinho(item)}
                              className="w-8 h-8 bg-mcdonalds-red text-white rounded-full hover:bg-red-700 font-bold"
                            >
                              +
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>

                    <div className="border-t pt-4 mb-4">
                      <div className="flex justify-between text-xl font-bold">
                        <span>Total:</span>
                        <span className="text-mcdonalds-red">R$ {calcularTotal().toFixed(2)}</span>
                      </div>
                    </div>

                    <button onClick={finalizarPedido} className="btn-primary w-full">
                      Finalizar Pedido
                    </button>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

function getIcon(categoria: string): string {
  const icons: Record<string, string> = {
    'Lanches': '🍔',
    'Bebidas': '🥤',
    'Sobremesas': '🍦',
    'Acompanhamentos': '🍟',
    'Combos': '🎁',
  };
  return icons[categoria] || '🍽️';
}
