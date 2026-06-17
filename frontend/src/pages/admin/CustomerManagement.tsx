import { useQuery } from '@tanstack/react-query';
import { customerAPI } from '../../services/api';

export default function CustomerManagement() {
  const { data: customers, isLoading } = useQuery({
    queryKey: ['allCustomers'],
    queryFn: () => customerAPI.getList().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">顧客管理</h1>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-100 border-b">
            <tr>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">ID</th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">名前</th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">メール</th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">電話</th>
              <th className="px-6 py-3 text-left text-sm font-semibold text-gray-700">住所</th>
            </tr>
          </thead>
          <tbody>
            {customers?.map((customer: any) => (
              <tr key={customer.id} className="border-b hover:bg-gray-50">
                <td className="px-6 py-3 text-sm text-gray-800">{customer.id}</td>
                <td className="px-6 py-3 text-sm text-gray-800">{customer.name}</td>
                <td className="px-6 py-3 text-sm text-gray-800">{customer.email}</td>
                <td className="px-6 py-3 text-sm text-gray-800">{customer.phone}</td>
                <td className="px-6 py-3 text-sm text-gray-800">{customer.address}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
