import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { petAPI } from '../../services/api';
import { PetRequest, WeightCategory } from '../../types';
import { Plus, X, PawPrint, AlertCircle } from 'lucide-react';

const weightLabels: Record<WeightCategory, string> = {
  Toy:    'Toy〜5kg',
  Small:  'Small 5〜10kg',
  Medium: 'Medium 10〜15kg',
  Large:  'Large 15〜20kg',
  Giant:  'Giant 20kg〜',
};

export default function PetManagement() {
  const qc = useQueryClient();
  const [showForm, setShowForm] = useState(false);
  const [selected, setSelected] = useState<any>(null);
  const [formData, setFormData] = useState<PetRequest>({
    name: '', breed: '', weight: WeightCategory.Medium, age: undefined, feature: '',
  });

  const { data: pets, isLoading } = useQuery({
    queryKey: ['myPets'],
    queryFn: () => petAPI.getMyList().then((r) => r.data),
  });

  const createMutation = useMutation({
    mutationFn: (d: PetRequest) => petAPI.create(d),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['myPets'] });
      setShowForm(false);
      setFormData({ name: '', breed: '', weight: WeightCategory.Medium, age: undefined, feature: '' });
    },
  });

  if (isLoading) return (
    <div className="flex items-center justify-center py-24">
      <div className="w-6 h-6 rounded-full border-2 border-sky-400 border-t-transparent animate-spin" />
    </div>
  );

  return (
    <div className="space-y-6">

      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <p className="text-xs font-mono text-zinc-500 mb-1">// {pets?.length ?? 0} 頭登録中</p>
          <h1 className="text-2xl font-bold text-zinc-100">ペット管理</h1>
        </div>
        <button
          onClick={() => { setShowForm(!showForm); setSelected(null); }}
          className="flex items-center gap-2 px-4 py-2 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors"
        >
          {showForm ? <X className="w-4 h-4" /> : <Plus className="w-4 h-4" />}
          {showForm ? 'キャンセル' : '追加'}
        </button>
      </div>

      {/* Add form */}
      {showForm && (
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
          <h2 className="text-sm font-semibold text-zinc-300 mb-5">
            <span className="text-sky-400">#</span> 新しいペットを登録
          </h2>
          <form
            onSubmit={(e) => { e.preventDefault(); createMutation.mutate(formData); }}
            className="space-y-4"
          >
            <div className="grid grid-cols-2 gap-3">
              {[
                { label: 'ペット名', field: 'name',  type: 'text', placeholder: 'ポチ' },
                { label: '犬種',     field: 'breed', type: 'text', placeholder: 'トイプードル' },
              ].map(({ label, field, type, placeholder }) => (
                <div key={field}>
                  <label className="text-xs font-mono text-zinc-400 mb-1.5 block">{label}</label>
                  <input
                    type={type}
                    value={(formData as any)[field]}
                    onChange={(e) => setFormData({ ...formData, [field]: e.target.value })}
                    placeholder={placeholder}
                    className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                    required
                  />
                </div>
              ))}
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">体重区分</label>
                <select
                  value={formData.weight}
                  onChange={(e) => setFormData({ ...formData, weight: e.target.value as WeightCategory })}
                  className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                >
                  {Object.entries(weightLabels).map(([k, v]) => (
                    <option key={k} value={k}>{v}</option>
                  ))}
                </select>
              </div>
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">年齢</label>
                <input
                  type="number"
                  min={0}
                  value={formData.age ?? ''}
                  onChange={(e) => setFormData({ ...formData, age: parseInt(e.target.value) || undefined })}
                  placeholder="任意"
                  className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                />
              </div>
            </div>
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">特徴・メモ</label>
              <textarea
                value={formData.feature ?? ''}
                onChange={(e) => setFormData({ ...formData, feature: e.target.value })}
                placeholder="性格・アレルギー・注意事項など"
                className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition resize-none"
                rows={3}
              />
            </div>
            {createMutation.isError && (
              <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-2 rounded-lg text-sm">
                <AlertCircle className="w-4 h-4 shrink-0" />
                登録に失敗しました
              </div>
            )}
            <button
              type="submit"
              disabled={createMutation.isPending}
              className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50"
            >
              {createMutation.isPending ? '登録中...' : '登録する'}
            </button>
          </form>
        </div>
      )}

      {/* Pet list */}
      {!pets || pets.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <PawPrint className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm mb-3">まだペットが登録されていません</p>
          <button
            onClick={() => setShowForm(true)}
            className="text-sm text-sky-400 hover:text-sky-300 font-medium transition-colors"
          >
            最初のペットを登録する
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-3">
          {pets.map((pet: any) => (
            <button
              key={pet.id}
              onClick={() => setSelected(selected?.id === pet.id ? null : pet)}
              className={`rounded-xl border p-5 hover:border-sky-400/50 transition-all duration-200 text-left
                ${selected?.id === pet.id ? 'border-sky-400/50 bg-zinc-800' : 'border-zinc-700 bg-zinc-800/50'}`}
            >
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-lg bg-sky-500/10 border border-sky-400/20 flex items-center justify-center">
                    <PawPrint className="w-5 h-5 text-sky-400" />
                  </div>
                  <div>
                    <p className="text-sm font-semibold text-zinc-100">{pet.name}</p>
                    <p className="text-xs text-zinc-500">{pet.breed}</p>
                  </div>
                </div>
                <span className="px-2 py-0.5 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20">
                  {pet.weight}
                </span>
              </div>

              {/* Detail (expanded) */}
              {selected?.id === pet.id && (
                <div className="mt-4 pt-4 border-t border-zinc-700 space-y-1.5 text-left">
                  {pet.age != null && (
                    <p className="text-xs text-zinc-400">
                      <span className="font-mono text-zinc-500">年齢: </span>{pet.age} 歳
                    </p>
                  )}
                  {pet.feature && (
                    <p className="text-xs text-zinc-400 leading-relaxed">{pet.feature}</p>
                  )}
                </div>
              )}
            </button>
          ))}
        </div>
      )}

    </div>
  );
}
