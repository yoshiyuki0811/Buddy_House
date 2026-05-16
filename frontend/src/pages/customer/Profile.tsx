import { useQuery } from '@tanstack/react-query';
import { customerAPI } from '../../services/api';

export default function CustomerProfile() {
  const { data: customer, isLoading } = useQuery({
    queryKey: ['customerMe'],
    queryFn: () => customerAPI.getMe().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="max-w-2xl">
      <h1 className="text-3xl font-bold text-gray-800 mb-6">プロフィール</h1>

      <div className="bg-white rounded-lg shadow p-8 space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            お名前
          </label>
          <p className="text-lg text-gray-800">{customer?.name}</p>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            メールアドレス
          </label>
          <p className="text-lg text-gray-800">{customer?.email}</p>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            住所
          </label>
          <p className="text-lg text-gray-800">{customer?.address}</p>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            電話番号
          </label>
          <p className="text-lg text-gray-800">{customer?.phone}</p>
        </div>
      </div>
    </div>
  );
}
