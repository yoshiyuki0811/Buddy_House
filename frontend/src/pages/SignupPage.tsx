import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../services/api';
import { AxiosError } from 'axios';
import { Dog, AlertCircle } from 'lucide-react';

export default function SignupPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '', password: '', name: '', address: '', phone: '',
  });
  const [error,   setError]   = useState('');
  const [loading, setLoading] = useState(false);

  const set = (field: string) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setFormData((prev) => ({ ...prev, [field]: e.target.value }));

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await authAPI.signup(formData);
      navigate('/login');
    } catch (err) {
      const axiosError = err as AxiosError<{ message: string }>;
      setError(axiosError.response?.data?.message || '登録に失敗しました');
    } finally {
      setLoading(false);
    }
  };

  const fields = [
    { label: 'お名前',         field: 'name',     type: 'text',     placeholder: '山田 太郎' },
    { label: 'メールアドレス', field: 'email',    type: 'email',    placeholder: 'you@example.com' },
    { label: 'パスワード',     field: 'password', type: 'password', placeholder: '8文字以上' },
    { label: '住所',           field: 'address',  type: 'text',     placeholder: '東京都渋谷区...' },
    { label: '電話番号',       field: 'phone',    type: 'tel',      placeholder: '090-0000-0000' },
  ];

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

        <h1 className="text-2xl font-bold text-zinc-100 mb-1">新規登録</h1>
        <p className="text-zinc-500 text-sm mb-8">新しいアカウントを作成</p>

        {error && (
          <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-3 rounded-lg text-sm mb-5">
            <AlertCircle className="w-4 h-4 shrink-0" />
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          {fields.map(({ label, field, type, placeholder }) => (
            <div key={field}>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">{label}</label>
              <input
                type={type}
                value={formData[field as keyof typeof formData]}
                onChange={set(field)}
                placeholder={placeholder}
                className="w-full px-4 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                required
              />
            </div>
          ))}
          <button
            type="submit"
            disabled={loading}
            className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50 mt-1"
          >
            {loading ? '登録中...' : 'アカウントを作成'}
          </button>
        </form>

        <p className="text-center text-sm text-zinc-500 mt-6">
          すでにアカウントをお持ちですか？{' '}
          <Link to="/login" className="text-sky-400 hover:text-sky-300 font-medium transition-colors">
            ログイン
          </Link>
        </p>

      </div>
    </div>
  );
}
