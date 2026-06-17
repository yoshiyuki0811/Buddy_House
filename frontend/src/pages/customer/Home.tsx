import { useQuery } from '@tanstack/react-query';
import { customerAPI } from '../../services/api';
import { useNavigate } from 'react-router-dom';

export default function CustomerHome() {
  const navigate = useNavigate();
  const { data: customer, isLoading } = useQuery({
    queryKey: ['customerMe'],
    queryFn: () => customerAPI.getMe().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-8">
      <div className="bg-white rounded-lg shadow p-6">
        <h1 className="text-3xl font-bold text-gray-800 mb-2">
          ようこそ、{customer?.name}様
        </h1>
        <p className="text-gray-600">ペットホテルへようこそ！</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div
          onClick={() => navigate('/pets')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">🐕</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">ペット管理</h2>
          <p className="text-gray-600">ペット情報の登録・管理</p>
        </div>

        <div
          onClick={() => navigate('/reservations/new')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">📅</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">予約作成</h2>
          <p className="text-gray-600">新規予約の作成</p>
        </div>

        <div
          onClick={() => navigate('/reservations')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">📋</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">予約履歴</h2>
          <p className="text-gray-600">過去の予約確認</p>
        </div>
      </div>
    </div>
  );
}
