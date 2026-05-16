import { useQuery } from '@tanstack/react-query';
import { petAPI } from '../../services/api';

export default function PetListAdmin() {
  const { data: pets, isLoading } = useQuery({
    queryKey: ['allPets'],
    queryFn: () => petAPI.getList().then((res) => res.data),
  });

  if (isLoading) {
    return <div className="text-center py-8">ロード中...</div>;
  }

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-800">ペット一覧</h1>

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
