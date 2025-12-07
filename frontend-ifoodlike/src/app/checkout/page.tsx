'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { pedidosAPI } from '@/services/api';
import type { CartItem, PedidoCriacao } from '@/types';
import { ArrowLeft, Check } from 'lucide-react';

export default function Checkout() {
  const router = useRouter();
  const [carrinho, setCarrinho] = useState<CartItem[]>([]);
  const [endereco, setEndereco] = useState('');
  const [observacao, setObservacao] = useState('');
  const [loading, setLoading] = useState(false);
  const [sucesso, setSucesso] = useState(false);

  useEffect(() => {
    const carrinhoSalvo = localStorage.getItem('carrinho');
    if (carrinhoSalvo) {
      setCarrinho(JSON.parse(carrinhoSalvo));
    } else {
      router.push('/');
    }

    const enderecoSalvo = localStorage.getItem('endereco');
    if (enderecoSalvo) {
      setEndereco(enderecoSalvo);
    }
  }, []);

  const calcularTotal = () => {
    return carrinho.reduce((total, item) => total + item.preco * item.quantity, 0);
  };

  const finalizarPedido = async () => {
    if (!endereco.trim()) {
      alert('Por favor, informe o endereço de entrega');
      return;
    }

    setLoading(true);

    try {
      const lojaId = carrinho[0]?.lojaId || 1;

      const pedido: PedidoCriacao = {
        lojaId,
        itens: carrinho.map((item) => ({
          produtoId: item.id,
          quantidade: item.quantity,
          observacao: item.observacao,
        })),
        observacao,
        enderecoEntrega: endereco,
      };

      await pedidosAPI.criar(pedido);

      setSucesso(true);
      localStorage.removeItem('carrinho');
      localStorage.setItem('endereco', endereco);

      setTimeout(() => {
        router.push('/pedidos');
      }, 2000);
    } catch (error: any) {
      alert(error.response?.data?.mensagem || 'Erro ao criar pedido');
    } finally {
      setLoading(false);
    }
  };

  if (sucesso) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
        <div className="text-center">
          <div className="w-24 h-24 bg-green-500 rounded-full flex items-center justify-center mx-auto mb-6">
            <Check size={48} className="text-white" />
          </div>
          <h1 className="text-3xl font-bold text-gray-800 mb-2">Pedido Realizado!</h1>
          <p className="text-gray-600 mb-4">Seu pedido foi enviado para a cozinha</p>
          <p className="text-sm text-gray-500">Redirecionando...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-mcdonalds-red text-white py-4">
        <div className="container mx-auto px-4">
          <button
            onClick={() => router.back()}
            className="flex items-center space-x-2 hover:text-mcdonalds-yellow transition-colors"
          >
            <ArrowLeft size={20} />
            <span>Voltar</span>
          </button>
        </div>
      </header>

      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">Finalizar Pedido</h1>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Formulário */}
          <div>
            <div className="card p-6 mb-6">
              <h2 className="text-xl font-bold mb-4">Endereço de Entrega</h2>
              <input
                type="text"
                value={endereco}
                onChange={(e) => setEndereco(e.target.value)}
                placeholder="Rua, Número - Bairro, Cidade"
                className="input-field"
                required
              />
            </div>

            <div className="card p-6">
              <h2 className="text-xl font-bold mb-4">Observações (opcional)</h2>
              <textarea
                value={observacao}
                onChange={(e) => setObservacao(e.target.value)}
                placeholder="Ex: Sem cebola, ponto da carne, etc..."
                rows={4}
                className="input-field resize-none"
              />
            </div>
          </div>

          {/* Resumo */}
          <div>
            <div className="card p-6">
              <h2 className="text-xl font-bold mb-4">Resumo do Pedido</h2>

              <div className="space-y-3 mb-6">
                {carrinho.map((item) => (
                  <div key={item.id} className="flex justify-between items-start border-b pb-3">
                    <div className="flex-1">
                      <h4 className="font-semibold">{item.nome}</h4>
                      <p className="text-sm text-gray-600">
                        {item.quantity}x R$ {item.preco.toFixed(2)}
                      </p>
                    </div>
                    <span className="font-semibold">
                      R$ {(item.preco * item.quantity).toFixed(2)}
                    </span>
                  </div>
                ))}
              </div>

              <div className="border-t pt-4 mb-6">
                <div className="flex justify-between text-sm mb-2">
                  <span className="text-gray-600">Subtotal</span>
                  <span>R$ {calcularTotal().toFixed(2)}</span>
                </div>
                <div className="flex justify-between text-sm mb-2">
                  <span className="text-gray-600">Taxa de entrega</span>
                  <span className="text-green-600">Grátis</span>
                </div>
                <div className="flex justify-between text-xl font-bold mt-4">
                  <span>Total</span>
                  <span className="text-mcdonalds-red">R$ {calcularTotal().toFixed(2)}</span>
                </div>
              </div>

              <button
                onClick={finalizarPedido}
                disabled={loading}
                className="btn-primary w-full"
              >
                {loading ? 'Finalizando...' : 'Confirmar Pedido'}
              </button>

              <p className="text-xs text-gray-500 text-center mt-4">
                Ao confirmar, você concorda com nossos termos de serviço
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
