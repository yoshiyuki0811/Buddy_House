import { useState } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { petAPI, menuAPI, reservationAPI } from '../../services/api';
import { ReservationType } from '../../types';

export default function ReservationBooking() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    reservationFrameId: '',
    menuId: '',
    petIds: [] as number[],
    reservationType: ReservationType.DAYCARE as ReservationType,
  });

  const { data: myPets } = useQuery({
    queryKey: ['myPets'],
    queryFn: () => petAPI.getMyList().then((res) => res.data),
  });

  const { data: menus } = useQuery({
    queryKey: ['menus'],
    queryFn: () => menuAPI.getList().then((res) => res.data),
  });

  const createReservationMutation = useMutation({
    mutationFn: (data: any) => reservationAPI.create(data),
    onSuccess: () => {
      navigate('/reservations');
    },
  });

  const handlePetToggle = (petId: number) => {
    setFormData((prev) => ({
      ...prev,
      petIds: prev.petIds.includes(petId)
        ? prev.petIds.filter((id) => id !== petId)
        : [...prev.petIds, petId],
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createReservationMutation.mutate({
      reservationFrameId: parseInt(formData.reservationFrameId),
      menuId: parseInt(formData.menuId),
      petIds: formData.petIds,
    });
  };

  const filteredMenus = menus?.filter((menu: any) => menu.type === formData.reservationType) || [];

  return (
    <div className="max-w-2xl">
      <h1 className="text-3xl font-bold text-gray-800 mb-6">予約作成</h1>

      <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow p-6 space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            予約タイプ
          </label>
          <select
            value={formData.reservationType}
            onChange={(e) =>
              setFormData({
                ...formData,
                reservationType: e.target.value as ReservationType,
                menuId: '',
              })
            }
            className="w-full px-4 py-2 border border-gray-300 rounded-lg"
          >
            <option value={ReservationType.DAYCARE}>日帰り</option>
            <option value={ReservationType.OVERNIGHT}>宿泊</option>
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            メニュー
          </label>
          <select
            value={formData.menuId}
            onChange={(e) => setFormData({ ...formData, menuId: e.target.value })}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg"
            required
          >
            <option value="">選択してください</option>
            {filteredMenus.map((menu: any) => (
              <option key={menu.id} value={menu.id}>
                {menu.name} - ¥{menu.price}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            予約するペット
          </label>
          <div className="space-y-2">
            {myPets?.map((pet: any) => (
              <label key={pet.id} className="flex items-center">
                <input
                  type="checkbox"
                  checked={formData.petIds.includes(pet.id)}
                  onChange={() => handlePetToggle(pet.id)}
                  className="w-4 h-4 rounded"
                />
                <span className="ml-3 text-gray-700">
                  {pet.name} ({pet.breed})
                </span>
              </label>
            ))}
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-2">
            予約枠ID
          </label>
          <input
            type="number"
            value={formData.reservationFrameId}
            onChange={(e) => setFormData({ ...formData, reservationFrameId: e.target.value })}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg"
            placeholder="予約枠のIDを入力"
            required
          />
        </div>

        <button
          type="submit"
          disabled={createReservationMutation.isPending}
          className="w-full bg-purple-600 hover:bg-purple-700 text-white font-semibold py-2 px-4 rounded-lg transition disabled:opacity-50"
        >
          {createReservationMutation.isPending ? '予約中...' : '予約を確定'}
        </button>
      </form>
    </div>
  );
}
