import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../services/api';
import { useAuthStore } from '../store/authStore';
import { AxiosError } from 'axios';
import { Dog, AlertCircle, ChevronDown } from 'lucide-react';

export default function LoginPage() {
  const navigate = useNavigate();
  const setAuth  = useAuthStore((s) => s.setAuth);
  const [email,    setEmail]    = useState('');
  const [password, setPassword] = useState('');
  const [error,    setError]    = useState('');
  const [loading,  setLoading]  = useState(false);
  const [showDemo, setShowDemo] = useState(false);

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
    <div className="min-h-screen bg-zinc-950 flex flex-col items-center justify-center p-6">

      {/* Card */}
      <div className="w-full max-w-sm">

        {/* Brand header */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-sky-500/10 border border-sky-400/20 mb-4">
            <Dog className="w-7 h-7 text-sky-400" />
          </div>
          <p className="text-xs font-mono text-zinc-500 mb-1">// ペットホテル予約管理</p>
          <h1 className="text-xl font-bold text-zinc-100">
            Buddy <span className="text-sky-400">House</span> にログイン
          </h1>
        </div>

        {/* Login section */}
        <div className="rounded-xl border border-zinc-700 bg-zinc-900 overflow-hidden">
          <div className="p-6">

            {error && (
              <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-3 rounded-lg text-sm mb-5">
                <AlertCircle className="w-4 h-4 shrink-0" />
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">
                  メールアドレス
                </label>
                <input
                  type="text"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="you@example.com"
                  className="w-full px-4 py-2.5 bg-zinc-800 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
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
                  className="w-full px-4 py-2.5 bg-zinc-800 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                  required
                />
              </div>
              <button
                type="submit"
                disabled={loading}
                className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-semibold hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50 mt-2"
              >
                {loading ? 'ログイン中...' : 'ログイン'}
              </button>
            </form>

          </div>

          {/* Divider */}
          <div className="border-t border-zinc-700" />

          {/* New registration section */}
          <div className="px-6 py-5 bg-zinc-800/30 flex items-center justify-between">
            <div>
              <p className="text-xs font-medium text-zinc-300">アカウントをお持ちでない方</p>
              <p className="text-xs text-zinc-500 mt-0.5">無料で新規登録できます</p>
            </div>
            <Link
              to="/signup"
              className="px-4 py-1.5 rounded-lg border border-zinc-600 text-zinc-300 text-sm font-medium hover:border-sky-400/50 hover:text-sky-400 transition-all duration-200 whitespace-nowrap"
            >
              新規登録
            </Link>
          </div>
        </div>

        {/* Demo accounts */}
        <div className="mt-4">
          <button
            type="button"
            onClick={() => setShowDemo(!showDemo)}
            className="w-full flex items-center justify-between px-4 py-3 rounded-lg border border-zinc-800 text-zinc-500 hover:border-zinc-700 hover:text-zinc-400 transition-all duration-200"
          >
            <span className="text-xs font-mono">// デモアカウント</span>
            <ChevronDown className={`w-3.5 h-3.5 transition-transform duration-200 ${showDemo ? 'rotate-180' : ''}`} />
          </button>

          {showDemo && (
            <div className="mt-1 rounded-lg border border-zinc-800 bg-zinc-900/50 overflow-hidden">
              {[
                { role: 'ADMIN',    email: 'admin',           pass: 'password123' },
                { role: 'CUSTOMER', email: 'test@example.com', pass: 'password123' },
              ].map(({ role, email: e, pass }) => (
                <button
                  key={role}
                  type="button"
                  onClick={() => { setEmail(e); setPassword(pass); setShowDemo(false); }}
                  className="w-full text-left px-4 py-3 hover:bg-zinc-800/50 transition-colors border-b border-zinc-800 last:border-b-0"
                >
                  <div className="flex items-center gap-2">
                    <span className="px-2 py-0.5 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20">
                      {role}
                    </span>
                    <span className="text-xs text-zinc-400">{e}</span>
                  </div>
                </button>
              ))}
            </div>
          )}
        </div>

      </div>

      {/* Footer */}
      <div className="mt-10 flex items-center gap-4">
        {['利用規約', 'プライバシーポリシー', 'よくある質問'].map((label) => (
          <span key={label} className="text-xs text-zinc-600 hover:text-zinc-500 cursor-pointer transition-colors">
            {label}
          </span>
        ))}
      </div>
      <p className="text-xs text-zinc-700 mt-3">© 2024 Buddy House</p>

    </div>
  );
}
