import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { petAPI } from '../../services/api';
import { PetRequest, WeightCategory } from '../../types';

export default function PetManagement() {
  const queryClient = useQueryClient();
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState<PetRequest>({
    name: '',
    breed: '',
    weight: WeightCategory.Medium,
    age: undefined,
    feature: '',
  });

  const { data: pets, isLoading } = useQuery({
    queryKey: ['myPets'],
    queryFn: () => petAPI.getMyList().then((res) => res.data),
  });

  const createMutation = useMutation({
    mutationFn: (data: PetRequest) => petAPI.create(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['myPets'] });
      setShowForm(false);
      setFormData({
        name: '',
        breed: '',
        weight: WeightCategory.Medium,
        age: undefined,
        feature: '',
      });
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createMutation.mutate(formData);
  };

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-800">ペット管理</h1>
        <button
          onClick={() => setShowForm(!showForm)}
          className="bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition"
        >
          {showForm ? 'キャンセル' : 'ペットを登録'}
        </button>
      </div>

      {showForm && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-800 mb-4">新規ペット登録</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                ペット名
              </label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                犬種
              </label>
              <input
                type="text"
                value={formData.breed}
                onChange={(e) => setFormData({ ...formData, breed: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                体重区分
              </label>
              <select
                value={formData.weight}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    weight: e.target.value as WeightCategory,
                  })
                }
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
              >
                {Object.values(WeightCategory).map((cat) => (
                  <option key={cat} value={cat}>
                    {cat}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                年齢
              </label>
              <input
                type="number"
                value={formData.age || ''}
                onChange={(e) =>
                  setFormData({ ...formData, age: parseInt(e.target.value) || undefined })
                }
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                特徴
              </label>
              <textarea
                value={formData.feature || ''}
                onChange={(e) => setFormData({ ...formData, feature: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                rows={3}
              />
            </div>

            <button
              type="submit"
              disabled={createMutation.isPending}
              className="w-full bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition disabled:opacity-50"
            >
              {createMutation.isPending ? '登録中...' : '登録'}
            </button>
          </form>
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {pets?.map((pet: any) => (
          <div key={pet.id} className="bg-white rounded-lg shadow p-6">
            <h3 className="text-xl font-bold text-gray-800 mb-2">{pet.name}</h3>
            <p className="text-gray-600 mb-2">犬種: {pet.breed}</p>
            <p className="text-gray-600 mb-2">体重区分: {pet.weight}</p>
            {pet.age && <p className="text-gray-600 mb-2">年齢: {pet.age}才</p>}
            {pet.feature && <p className="text-gray-600">特徴: {pet.feature}</p>}
          </div>
        ))}
      </div>
    </div>
  );
}
