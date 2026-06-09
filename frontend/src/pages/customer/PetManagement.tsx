import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { petAPI } from '../../services/api';
import { Pet, PetRequest, WeightCategory } from '../../types';
import { Plus, X, PawPrint, AlertCircle, Pencil } from 'lucide-react';

const weightLabels: Record<WeightCategory, string> = {
  Toy:    'Toy〜5kg',
  Small:  'Small 5〜10kg',
  Medium: 'Medium 10〜15kg',
  Large:  'Large 15〜20kg',
  Giant:  'Giant 20kg〜',
};

const emptyForm = (): PetRequest => ({
  name: '', breed: '', weight: WeightCategory.Medium, age: undefined, feature: '',
});

export default function PetManagement() {
  const qc = useQueryClient();
  const [showAddForm, setShowAddForm] = useState(false);
  const [addData, setAddData] = useState<PetRequest>(emptyForm());
  const [expandedId, setExpandedId] = useState<number | null>(null);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editData, setEditData] = useState<PetRequest>(emptyForm());

  const { data: pets, isLoading } = useQuery<Pet[]>({
    queryKey: ['myPets'],
    queryFn: () => petAPI.getMyList().then((r) => r.data),
  });

  const createMutation = useMutation({
    mutationFn: (d: PetRequest) => petAPI.create(d),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['myPets'] });
      setShowAddForm(false);
      setAddData(emptyForm());
    },
  });

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: PetRequest }) =>
      petAPI.updateMe(id, data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['myPets'] });
      setEditingId(null);
      setExpandedId(null);
    },
  });

  const handleCardClick = (petId: number) => {
    if (editingId === petId) return;
    setExpandedId(expandedId === petId ? null : petId);
    setShowAddForm(false);
  };

  const openEdit = (pet: Pet) => {
    setEditingId(pet.id);
    setEditData({
      name: pet.name,
      breed: pet.breed,
      weight: pet.weight,
      age: pet.age ?? undefined,
      feature: pet.feature ?? '',
    });
  };

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
          onClick={() => {
            setShowAddForm(!showAddForm);
            setExpandedId(null);
            setEditingId(null);
          }}
          className="flex items-center gap-2 px-4 py-2 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors"
        >
          {showAddForm ? <X className="w-4 h-4" /> : <Plus className="w-4 h-4" />}
          {showAddForm ? 'キャンセル' : '追加'}
        </button>
      </div>

      {/* Add form */}
      {showAddForm && (
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
          <h2 className="text-sm font-semibold text-zinc-300 mb-5">
            <span className="text-sky-400">#</span> 新しいペットを登録
          </h2>
          <form
            onSubmit={(e) => { e.preventDefault(); createMutation.mutate(addData); }}
            className="space-y-4"
          >
            <div className="grid grid-cols-2 gap-3">
              {([
                { label: 'ペット名', field: 'name'  as keyof PetRequest, placeholder: 'ポチ' },
                { label: '犬種',    field: 'breed' as keyof PetRequest, placeholder: 'トイプードル' },
              ] as { label: string; field: keyof PetRequest; placeholder: string }[]).map(({ label, field, placeholder }) => (
                <div key={field}>
                  <label className="text-xs font-mono text-zinc-400 mb-1.5 block">{label}</label>
                  <input
                    type="text"
                    value={addData[field] as string ?? ''}
                    onChange={(e) => setAddData({ ...addData, [field]: e.target.value })}
                    placeholder={placeholder}
                    className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                    required
                  />
                </div>
              ))}
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">体重区分</label>
                <select
                  value={addData.weight}
                  onChange={(e) => setAddData({ ...addData, weight: e.target.value as WeightCategory })}
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
                  value={addData.age ?? ''}
                  onChange={(e) => setAddData({ ...addData, age: e.target.value === '' ? undefined : parseInt(e.target.value) })}
                  placeholder="任意"
                  className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                />
              </div>
            </div>
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">特徴・メモ</label>
              <textarea
                value={addData.feature ?? ''}
                onChange={(e) => setAddData({ ...addData, feature: e.target.value })}
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
            onClick={() => setShowAddForm(true)}
            className="text-sm text-sky-400 hover:text-sky-300 font-medium transition-colors"
          >
            最初のペットを登録する
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-3">
          {pets.map((pet) => {
            const isEditing = editingId === pet.id;
            const isExpanded = expandedId === pet.id;

            return (
              <div
                key={pet.id}
                className={`rounded-xl border transition-all duration-200
                  ${isEditing || isExpanded
                    ? 'border-sky-400/50 bg-zinc-800'
                    : 'border-zinc-700 bg-zinc-800/50 hover:border-sky-400/50'}`}
              >
                {/* Card header */}
                <button
                  className="w-full p-5 text-left"
                  onClick={() => handleCardClick(pet.id)}
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
                </button>

                {/* Expanded: detail + edit button */}
                {isExpanded && !isEditing && (
                  <div className="px-5 pb-5 border-t border-zinc-700">
                    <div className="pt-4 space-y-1.5">
                      {pet.age != null && (
                        <p className="text-xs text-zinc-400">
                          <span className="font-mono text-zinc-500">年齢: </span>{pet.age} 歳
                        </p>
                      )}
                      {pet.feature && (
                        <p className="text-xs text-zinc-400 leading-relaxed">{pet.feature}</p>
                      )}
                    </div>
                    <div className="mt-4">
                      <button
                        onClick={() => openEdit(pet)}
                        className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-zinc-700 text-zinc-300 text-xs font-medium hover:bg-zinc-600 hover:text-zinc-100 transition-colors"
                      >
                        <Pencil className="w-3.5 h-3.5" />
                        編集する
                      </button>
                    </div>
                  </div>
                )}

                {/* Editing: inline edit form */}
                {isEditing && (
                  <div className="px-5 pb-5 border-t border-zinc-700">
                    <div className="flex items-center justify-between pt-4 mb-4">
                      <p className="text-xs font-mono text-zinc-400">
                        <span className="text-sky-400">#</span> 情報を編集
                      </p>
                      <button
                        onClick={() => setEditingId(null)}
                        className="text-zinc-500 hover:text-zinc-300 transition-colors"
                      >
                        <X className="w-4 h-4" />
                      </button>
                    </div>
                    <form
                      onSubmit={(e) => {
                        e.preventDefault();
                        updateMutation.mutate({ id: pet.id, data: editData });
                      }}
                      className="space-y-4"
                    >
                      <div className="grid grid-cols-2 gap-3">
                        {([
                          { label: 'ペット名', field: 'name'  as keyof PetRequest, placeholder: 'ポチ' },
                          { label: '犬種',    field: 'breed' as keyof PetRequest, placeholder: 'トイプードル' },
                        ] as { label: string; field: keyof PetRequest; placeholder: string }[]).map(({ label, field, placeholder }) => (
                          <div key={field}>
                            <label className="text-xs font-mono text-zinc-400 mb-1.5 block">{label}</label>
                            <input
                              type="text"
                              value={editData[field] as string ?? ''}
                              onChange={(e) => setEditData({ ...editData, [field]: e.target.value })}
                              placeholder={placeholder}
                              className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                              required
                            />
                          </div>
                        ))}
                        <div>
                          <label className="text-xs font-mono text-zinc-400 mb-1.5 block">体重区分</label>
                          <select
                            value={editData.weight}
                            onChange={(e) => setEditData({ ...editData, weight: e.target.value as WeightCategory })}
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
                            value={editData.age ?? ''}
                            onChange={(e) => setEditData({ ...editData, age: e.target.value === '' ? undefined : parseInt(e.target.value) })}
                            placeholder="任意"
                            className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
                          />
                        </div>
                      </div>
                      <div>
                        <label className="text-xs font-mono text-zinc-400 mb-1.5 block">特徴・メモ</label>
                        <textarea
                          value={editData.feature ?? ''}
                          onChange={(e) => setEditData({ ...editData, feature: e.target.value })}
                          placeholder="性格・アレルギー・注意事項など"
                          className="w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition resize-none"
                          rows={3}
                        />
                      </div>
                      {updateMutation.isError && (
                        <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-2 rounded-lg text-sm">
                          <AlertCircle className="w-4 h-4 shrink-0" />
                          更新に失敗しました
                        </div>
                      )}
                      <button
                        type="submit"
                        disabled={updateMutation.isPending}
                        className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50"
                      >
                        {updateMutation.isPending ? '保存中...' : '保存する'}
                      </button>
                    </form>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}

    </div>
  );
}
