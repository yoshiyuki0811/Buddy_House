import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { frameAPI } from '../../services/api';
import { ReservationType } from '../../types';

export default function FrameManagement() {
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    reservationType: ReservationType.DAYCARE,
    startAt: '',
    endAt: '',
    maxDogs: 5,
  });

  const createMutation = useMutation({
    mutationFn: (data: any) => frameAPI.create(data),
    onSuccess: () => {
      setShowForm(false);
      setFormData({
        reservationType: ReservationType.DAYCARE,
        startAt: '',
        endAt: '',
        maxDogs: 5,
      });
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createMutation.mutate(formData);
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">予約枠管理</h1>
        <button
          onClick={() => setShowForm(!showForm)}
          className="bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition"
        >
          {showForm ? 'キャンセル' : '予約枠を追加'}
        </button>
      </div>

      {showForm && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">新規予約枠作成</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                予約タイプ
              </label>
              <select
                value={formData.reservationType}
                onChange={(e) =>
                  setFormData({ ...formData, reservationType: e.target.value as ReservationType })
                }
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
              >
                <option value={ReservationType.DAYCARE}>日帰り</option>
                <option value={ReservationType.OVERNIGHT}>宿泊</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                開始日時
              </label>
              <input
                type="datetime-local"
                value={formData.startAt}
                onChange={(e) => setFormData({ ...formData, startAt: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                終了日時
              </label>
              <input
                type="datetime-local"
                value={formData.endAt}
                onChange={(e) => setFormData({ ...formData, endAt: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                最大頭数
              </label>
              <input
                type="number"
                value={formData.maxDogs}
                onChange={(e) => setFormData({ ...formData, maxDogs: parseInt(e.target.value) })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>

            <button
              type="submit"
              disabled={createMutation.isPending}
              className="w-full bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition disabled:opacity-50"
            >
              {createMutation.isPending ? '作成中...' : '作成'}
            </button>
          </form>
        </div>
      )}
    </div>
  );
}
