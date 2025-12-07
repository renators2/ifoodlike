'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { authAPI } from '@/services/api';
import Link from 'next/link';

export default function Register() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    senha: '',
    cpf: '',
    telefone: '',
    endereco: '',
  });
  const [erro, setErro] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro('');
    setLoading(true);

    try {
      const response = await authAPI.register({
        ...formData,
        perfil: 'CLIENTE',
      });

      localStorage.setItem('token', response.token);
      localStorage.setItem('usuarioId', response.usuarioId.toString());
      localStorage.setItem('nomeUsuario', response.nome);
      localStorage.setItem('perfil', response.perfil);

      router.push('/');
    } catch (error: any) {
      setErro(error.response?.data?.mensagem || 'Erro ao criar conta.');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-mcdonalds-red via-red-600 to-mcdonalds-yellow flex items-center justify-center p-4">
      <div className="max-w-md w-full">
        <div className="text-center mb-8">
          <h1 className="text-6xl mb-4">🍔</h1>
          <h2 className="text-4xl font-bold text-white mb-2">Burger House</h2>
          <p className="text-white/90">Crie sua conta</p>
        </div>

        <div className="bg-white rounded-2xl shadow-2xl p-8">
          <h3 className="text-2xl font-bold text-center mb-6 text-gray-800">Cadastro</h3>

          {erro && (
            <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
              {erro}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Nome Completo</label>
              <input
                type="text"
                name="nome"
                value={formData.nome}
                onChange={handleChange}
                required
                className="input-field"
                placeholder="João Silva"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
                className="input-field"
                placeholder="seu@email.com"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Senha</label>
              <input
                type="password"
                name="senha"
                value={formData.senha}
                onChange={handleChange}
                required
                minLength={6}
                className="input-field"
                placeholder="Mínimo 6 caracteres"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">CPF (opcional)</label>
              <input
                type="text"
                name="cpf"
                value={formData.cpf}
                onChange={handleChange}
                className="input-field"
                placeholder="000.000.000-00"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Telefone (opcional)</label>
              <input
                type="tel"
                name="telefone"
                value={formData.telefone}
                onChange={handleChange}
                className="input-field"
                placeholder="(11) 99999-9999"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Endereço (opcional)</label>
              <input
                type="text"
                name="endereco"
                value={formData.endereco}
                onChange={handleChange}
                className="input-field"
                placeholder="Rua, Número - Bairro"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="btn-primary w-full"
            >
              {loading ? 'Criando conta...' : 'Criar Conta'}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-gray-600">
              Já tem uma conta?{' '}
              <Link href="/login" className="text-mcdonalds-red font-semibold hover:underline">
                Faça login
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
