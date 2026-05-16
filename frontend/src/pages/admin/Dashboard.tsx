import { useQuery } from '@tanstack/react-query';
import { customerAPI, reservationAPI } from '../../services/api';
import { useNavigate } from 'react-router-dom';

export default function AdminDashboard() {
  const navigate = useNavigate();
  const { data: customers } = useQuery({
    queryKey: ['allCustomers'],
    queryFn: () => customerAPI.getList().then((res) => res.data),
  });

  const { data: reservations } = useQuery({
    queryKey: ['allReservations'],
    queryFn: () => reservationAPI.getList().then((res) => res.data),
  });

  return (
    <div className="space-y-8">
      <div className="bg-white rounded-lg shadow p-6">
        <h1 className="text-3xl font-bold text-gray-800">管理者ダッシュボード</h1>
        <p className="text-gray-600 mt-2">ペットホテル管理システムへようこそ</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white rounded-lg shadow p-6">
          <div className="text-4xl mb-2">👥</div>
          <h3 className="text-gray-600 text-sm font-medium mb-1">総顧客数</h3>
          <p className="text-3xl font-bold text-gray-800">{customers?.length || 0}</p>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <div className="text-4xl mb-2">📅</div>
          <h3 className="text-gray-600 text-sm font-medium mb-1">総予約数</h3>
          <p className="text-3xl font-bold text-gray-800">{reservations?.length || 0}</p>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <div className="text-4xl mb-2">🐾</div>
          <h3 className="text-gray-600 text-sm font-medium mb-1">システムステータス</h3>
          <p className="text-3xl font-bold text-green-600">正常</p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div
          onClick={() => navigate('/admin/customers')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">👥</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">顧客管理</h2>
          <p className="text-gray-600">顧客情報の閲覧・編集</p>
        </div>

        <div
          onClick={() => navigate('/admin/reservations')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">📋</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">予約管理</h2>
          <p className="text-gray-600">予約の確認・管理</p>
        </div>

        <div
          onClick={() => navigate('/admin/menus')}
          className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer"
        >
          <div className="text-4xl mb-3">🍽️</div>
          <h2 className="text-xl font-bold text-gray-800 mb-2">メニュー管理</h2>
          <p className="text-gray-600">メニュー情報の管理</p>
        </div>
      </div>
    </div>
  );
}
