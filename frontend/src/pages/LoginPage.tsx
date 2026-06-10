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

      <div className="w-full max-w-sm rounded-2xl border border-zinc-700 bg-zinc-900 overflow-hidden shadow-xl shadow-sky-400/10">

        {/* Gradient banner */}
        <div className="bg-gradient-to-br from-sky-400 to-blue-600 px-6 pt-8 pb-12 text-center">
          <div className="inline-flex items-center justify-center w-12 h-12 rounded-2xl bg-white/15 backdrop-blur-sm mb-3">
            <Dog className="w-6 h-6 text-white" />
          </div>
          <h1 className="text-lg font-bold text-white">
            Buddy House
          </h1>
          <p className="text-xs font-mono text-sky-100 mt-1">// ペットホテル予約管理</p>
        </div>

        {/* Form card (overlaps banner) */}
        <div className="px-6 pb-6 -mt-6">
          <div className="rounded-xl bg-zinc-800/80 border border-zinc-700 p-5">

            {error && (
              <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-3 py-2.5 rounded-lg text-xs mb-4">
                <AlertCircle className="w-4 h-4 shrink-0" />
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-3">
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">
                  メールアドレス
                </label>
                <input
                  type="text"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="you@example.com"
                  className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                  required
                />
              </div>
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">
                  パスワード
                </label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="••••••••"
                  className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                  required
                />
              </div>
              <button
                type="submit"
                disabled={loading}
                className="w-full py-2.5 rounded-lg bg-gradient-to-r from-sky-400 to-blue-600 text-white font-semibold hover:opacity-90 active:opacity-80 transition-opacity disabled:opacity-50 mt-1"
              >
                {loading ? 'ログイン中...' : 'ログイン'}
              </button>
            </form>
          </div>

          {/* New registration */}
          <p className="text-center text-xs text-zinc-500 mt-4">
            アカウントをお持ちでない方は{' '}
            <Link to="/signup" className="text-sky-400 hover:text-sky-300 font-medium transition-colors">
              新規登録
            </Link>
          </p>

          {/* Demo accounts */}
          <div className="mt-4 pt-4 border-t border-zinc-800 flex items-center justify-center gap-2">
            <span className="text-xs font-mono text-zinc-600">demo:</span>
            {[
              { role: 'ADMIN',    email: 'admin',           pass: 'password123' },
              { role: 'CUSTOMER', email: 'test@example.com', pass: 'password123' },
            ].map(({ role, email: e, pass }) => (
              <button
                key={role}
                type="button"
                onClick={() => { setEmail(e); setPassword(pass); }}
                className="px-2.5 py-1 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20 hover:bg-sky-400/20 transition-colors"
              >
                {role}
              </button>
            ))}
          </div>
        </div>

      </div>

    </div>
  );
}
