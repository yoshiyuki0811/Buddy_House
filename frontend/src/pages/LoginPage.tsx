import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../services/api';
import { useAuthStore } from '../store/authStore';
import { AxiosError } from 'axios';
import { Dog, AlertCircle } from 'lucide-react';

export default function LoginPage() {
  const navigate = useNavigate();
  const setAuth  = useAuthStore((s) => s.setAuth);
  const [email,    setEmail]    = useState('');
  const [password, setPassword] = useState('');
  const [error,    setError]    = useState('');
  const [loading,  setLoading]  = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const { data } = await authAPI.login({ email, password });
      const payload = JSON.parse(atob(data.accessToken.split('.')[1]));
      const role = payload.roles?.[0]?.replace('ROLE_', '') || 'CUSTOMER';
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('userRole', role);
      setAuth(data.accessToken, role as 'ADMIN' | 'CUSTOMER', email);
      window.location.replace('/');
    } catch (err) {
      const e = err as AxiosError<{ message: string }>;
      setError(e.response?.data?.message || 'メールアドレスまたはパスワードが違います');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-zinc-950 flex items-center justify-center p-6">
      <div className="w-full max-w-sm">

        {/* Logo */}
        <div className="flex items-center gap-2 mb-10">
          <Dog className="w-6 h-6 text-sky-400" />
          <span className="text-lg font-semibold text-zinc-100">
            Buddy <span className="text-sky-400 font-mono">House</span>
          </span>
        </div>

        <h1 className="text-2xl font-bold text-zinc-100 mb-1">ログイン</h1>
        <p className="text-zinc-500 text-sm mb-8">アカウントにサインイン</p>

        {error && (
          <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-3 rounded-lg text-sm mb-5">
            <AlertCircle className="w-4 h-4 shrink-0" />
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="text-xs font-mono text-zinc-400 mb-1.5 block">メールアドレス</label>
            <input
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="you@example.com"
              className="w-full px-4 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
              required
            />
          </div>
          <div>
            <label className="text-xs font-mono text-zinc-400 mb-1.5 block">パスワード</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              className="w-full px-4 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
              required
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50"
          >
            {loading ? 'ログイン中...' : 'ログイン'}
          </button>
        </form>

        <p className="text-center text-sm text-zinc-500 mt-6">
          アカウントをお持ちでない方は{' '}
          <Link to="/signup" className="text-sky-400 hover:text-sky-300 font-medium transition-colors">
            新規登録
          </Link>
        </p>

        {/* Demo accounts */}
        <div className="mt-8 rounded-xl border border-zinc-700 bg-zinc-800/50 p-4">
          <p className="text-xs font-mono text-zinc-500 mb-3">// デモアカウント</p>
          <div className="space-y-1.5">
            {[
              { role: 'ADMIN', email: 'admin', pass: 'password123' },
              { role: 'CUSTOMER', email: 'test@example.com', pass: 'password123' },
            ].map(({ role, email: e, pass }) => (
              <button
                key={role}
                type="button"
                onClick={() => { setEmail(e); setPassword(pass); }}
                className="w-full text-left px-3 py-2 rounded-lg hover:bg-zinc-700/50 transition-colors"
              >
                <span className="px-2 py-0.5 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20 mr-2">
                  {role}
                </span>
                <span className="text-xs text-zinc-400">{e}</span>
              </button>
            ))}
          </div>
        </div>

      </div>
    </div>
  );
}
