'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { authAPI } from '@/services/api';
import Link from 'next/link';

export default function Login() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro('');
    setLoading(true);

    try {
      const response = await authAPI.login(email, senha);
      localStorage.setItem('token', response.token);
      localStorage.setItem('usuarioId', response.usuarioId.toString());
      localStorage.setItem('nomeUsuario', response.nome);
      localStorage.setItem('perfil', response.perfil);

      router.push('/');
    } catch (error: any) {
      setErro(error.response?.data?.mensagem || 'Erro ao fazer login. Verifique suas credenciais.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-mcdonalds-red via-red-600 to-mcdonalds-yellow flex items-center justify-center p-4">
      <div className="max-w-md w-full">
        <div className="text-center mb-8">
          <h1 className="text-6xl mb-4">🍔</h1>
          <h2 className="text-4xl font-bold text-white mb-2">Burger House</h2>
          <p className="text-white/90">Delivery de Comida</p>
        </div>

        <div className="bg-white rounded-2xl shadow-2xl p-8">
          <h3 className="text-2xl font-bold text-center mb-6 text-gray-800">Entrar</h3>

          {erro && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
              {erro}
            </div>
          )}

          <form onSubmit={handleLogin} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Email
              </label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="input-field"
                placeholder="seu@email.com"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Senha
              </label>
              <input
                type="password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
                className="input-field"
                placeholder="••••••••"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="btn-primary w-full"
            >
              {loading ? 'Entrando...' : 'Entrar'}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-gray-600">
              Não tem uma conta?{' '}
              <Link href="/register" className="text-mcdonalds-red font-semibold hover:underline">
                Cadastre-se
              </Link>
            </p>
          </div>

          <div className="mt-6 p-4 bg-blue-50 rounded-lg">
            <p className="text-sm font-semibold text-blue-900 mb-2">Contas de teste:</p>
            <p className="text-xs text-blue-800">
              <strong>Cliente:</strong> cliente@email.com / cliente123
            </p>
            <p className="text-xs text-blue-800">
              <strong>Loja:</strong> burguer@loja.com / loja123
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
