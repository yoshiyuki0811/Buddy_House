import { useQuery } from '@tanstack/react-query';
import { petAPI } from '../../services/api';
import { PawPrint } from 'lucide-react';

const weightCls: Record<string, string> = {
  Toy:    'bg-pink-400/10 text-pink-400 border-pink-400/20',
  Small:  'bg-sky-400/10 text-sky-400 border-sky-400/20',
  Medium: 'bg-green-400/10 text-green-400 border-green-400/20',
  Large:  'bg-orange-400/10 text-orange-400 border-orange-400/20',
  Giant:  'bg-red-400/10 text-red-400 border-red-400/20',
};

export default function PetListAdmin() {
  const { data: pets, isLoading } = useQuery({
    queryKey: ['allPets'],
    queryFn: () => petAPI.getList().then((r) => r.data),
  });

  if (isLoading) return (
    <div className="flex items-center justify-center py-24">
      <div className="w-6 h-6 rounded-full border-2 border-sky-400 border-t-transparent animate-spin" />
    </div>
  );

  return (
    <div className="space-y-6">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">{pets?.length ?? 0} 頭登録中</p>
        <h1 className="text-2xl font-bold text-zinc-100">ペット一覧</h1>
      </div>

      {!pets || pets.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <PawPrint className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">ペットが登録されていません</p>
        </div>
      ) : (
        <div className="grid grid-cols-2 gap-3">
          {pets.map((pet: any) => (
            <div
              key={pet.id}
              className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
            >
              <div className="flex items-start justify-between mb-3">
                <div className="w-10 h-10 rounded-lg bg-sky-500/10 border border-sky-400/20 flex items-center justify-center">
                  <PawPrint className="w-5 h-5 text-sky-400" />
                </div>
                <span className={`px-2 py-0.5 rounded-md text-xs font-mono border ${weightCls[pet.weight] ?? 'bg-zinc-500/10 text-zinc-400 border-zinc-500/20'}`}>
                  {pet.weight}
                </span>
              </div>
              <p className="text-sm font-semibold text-zinc-100">{pet.name}</p>
              <p className="text-xs text-zinc-500 mt-0.5">{pet.breed}</p>
              {pet.age != null && (
                <p className="text-xs font-mono text-zinc-600 mt-1">{pet.age} 歳</p>
              )}
              {pet.feature && (
                <p className="text-xs text-zinc-500 mt-2 line-clamp-2">{pet.feature}</p>
              )}
            </div>
          ))}
        </div>
      )}

    </div>
  );
}
