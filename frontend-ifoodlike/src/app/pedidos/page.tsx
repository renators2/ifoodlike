'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { pedidosAPI } from '@/services/api';
import type { PedidoResumo } from '@/types';
import { ArrowLeft, Clock, CheckCircle, Package, Truck, XCircle } from 'lucide-react';

export default function Pedidos() {
  const router = useRouter();
  const [pedidos, setPedidos] = useState<PedidoResumo[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    carregarPedidos();
  }, []);

  const carregarPedidos = async () => {
    try {
      const data = await pedidosAPI.listar();
      setPedidos(data);
    } catch (error) {
      console.error('Erro ao carregar pedidos:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'CRIADO':
        return <Clock className="text-blue-500" />;
      case 'EM_PREPARO':
        return <Package className="text-orange-500" />;
      case 'PRONTO':
        return <CheckCircle className="text-green-500" />;
      case 'ENTREGUE':
        return <Truck className="text-green-700" />;
      case 'CANCELADO':
        return <XCircle className="text-red-500" />;
      default:
        return <Clock />;
    }
  };

  const getStatusText = (status: string) => {
    const statusMap: Record<string, string> = {
      CRIADO: 'Pedido Criado',
      EM_PREPARO: 'Em Preparo',
      PRONTO: 'Pronto',
      ENTREGUE: 'Entregue',
      CANCELADO: 'Cancelado',
    };
    return statusMap[status] || status;
  };

  const getStatusColor = (status: string) => {
    const colorMap: Record<string, string> = {
      CRIADO: 'bg-blue-100 text-blue-800',
      EM_PREPARO: 'bg-orange-100 text-orange-800',
      PRONTO: 'bg-green-100 text-green-800',
      ENTREGUE: 'bg-green-200 text-green-900',
      CANCELADO: 'bg-red-100 text-red-800',
    };
    return colorMap[status] || 'bg-gray-100 text-gray-800';
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-mcdonalds-red text-white py-4">
        <div className="container mx-auto px-4 flex items-center justify-between">
          <button
            onClick={() => router.push('/')}
            className="flex items-center space-x-2 hover:text-mcdonalds-yellow transition-colors"
          >
            <ArrowLeft size={20} />
            <span>Voltar ao Cardápio</span>
          </button>
          <h1 className="text-2xl font-bold">Meus Pedidos</h1>
        </div>
      </header>

      <div className="container mx-auto px-4 py-8">
        {loading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-4 border-mcdonalds-red border-t-transparent mx-auto"></div>
            <p className="mt-4 text-gray-600">Carregando pedidos...</p>
          </div>
        ) : pedidos.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-gray-600 text-lg mb-4">Você ainda não fez nenhum pedido</p>
            <button onClick={() => router.push('/')} className="btn-primary">
              Ver Cardápio
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {pedidos.map((pedido) => (
              <div key={pedido.id} className="card hover:shadow-2xl transition-shadow">
                <div className="p-6">
                  <div className="flex items-start justify-between mb-4">
                    <div>
                      <h3 className="text-lg font-bold">Pedido #{pedido.id}</h3>
                      <p className="text-sm text-gray-600">{pedido.lojaNome}</p>
                    </div>
                    {getStatusIcon(pedido.status)}
                  </div>

                  <div className={`inline-block px-3 py-1 rounded-full text-sm font-semibold mb-4 ${getStatusColor(pedido.status)}`}>
                    {getStatusText(pedido.status)}
                  </div>

                  <div className="space-y-2 mb-4">
                    <div className="flex justify-between text-sm">
                      <span className="text-gray-600">Itens:</span>
                      <span className="font-semibold">{pedido.quantidadeItens}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-gray-600">Total:</span>
                      <span className="text-xl font-bold text-mcdonalds-red">
                        R$ {pedido.total.toFixed(2)}
                      </span>
                    </div>
                  </div>

                  <div className="text-xs text-gray-500 border-t pt-3">
                    {new Date(pedido.createdAt).toLocaleString('pt-BR')}
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
