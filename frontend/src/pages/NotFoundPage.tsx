import { useNavigate } from 'react-router-dom';
import { Dog } from 'lucide-react';

export default function NotFoundPage() {
  const navigate = useNavigate();
  return (
    <div className="min-h-screen bg-zinc-950 flex flex-col items-center justify-center text-center p-8">
      <Dog className="w-16 h-16 text-zinc-700 mb-6" />
      <p className="text-7xl font-bold font-mono text-zinc-800 mb-2">404</p>
      <p className="text-zinc-400 text-sm mb-8">ページが見つかりませんでした</p>
      <button
        onClick={() => navigate('/')}
        className="px-5 py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors text-sm"
      >
        ホームへ戻る
      </button>
    </div>
  );
}
