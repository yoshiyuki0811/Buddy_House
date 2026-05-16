import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { menuAPI } from '../../services/api';
import { ReservationType } from '../../types';

export default function MenuManagement() {
  const [showForm, setShowForm] = useState(false);
  const { data: menus, isLoading } = useQuery({
    queryKey: ['allMenus'],
    queryFn: () => menuAPI.adminGetList().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">メニュー管理</h1>
        <button
          onClick={() => setShowForm(!showForm)}
          className="bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition"
        >
          {showForm ? 'キャンセル' : 'メニュー追加'}
        </button>
      </div>

      {showForm && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">新規メニュー作成</h2>
          <p className="text-gray-600 text-sm mb-4">APIを通じてメニューを作成してください</p>
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {menus?.map((menu: any) => (
          <div key={menu.id} className="bg-white rounded-lg shadow p-6">
            <h3 className="text-xl font-bold text-gray-800 mb-2">{menu.name}</h3>
            <p className="text-gray-600 mb-2">タイプ: {menu.type}</p>
            <p className="text-2xl font-bold text-purple-600">¥{menu.price}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
