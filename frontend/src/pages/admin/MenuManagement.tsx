import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { menuAPI } from '../../services/api';
import { ReservationType } from '../../types';
import { Plus, X, Trash2, UtensilsCrossed, AlertCircle } from 'lucide-react';

const inputCls = `w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm
  text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400
  focus:border-sky-400 transition`;

export default function MenuManagement() {
  const qc = useQueryClient();
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '', feature: '', reservationType: ReservationType.DAYCARE,
  });
  const [error, setError] = useState('');

  const { data: menus, isLoading } = useQuery({
    queryKey: ['allMenus'],
    queryFn: () => menuAPI.adminGetList().then((r) => r.data),
  });

  const createMutation = useMutation({
    mutationFn: ({ type, data }: { type: string; data: any }) => menuAPI.create(type, data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['allMenus'] });
      setShowForm(false);
      setFormData({ name: '', feature: '', reservationType: ReservationType.DAYCARE });
      setError('');
    },
    onError: () => setError('メニューの作成に失敗しました。'),
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => menuAPI.delete(id),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['allMenus'] }),
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    createMutation.mutate({
      type: formData.reservationType,
      data: { name: formData.name, feature: formData.feature },
    });
  };

  const typeLabel = (type: string) => type === 'DAYCARE' ? '日帰り' : '宿泊';

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
          <p className="text-xs font-mono text-zinc-500 mb-1">// {menus?.length ?? 0} 件</p>
          <h1 className="text-2xl font-bold text-zinc-100">メニュー管理</h1>
        </div>
        <button
          onClick={() => { setShowForm(!showForm); setError(''); }}
          className="flex items-center gap-2 px-4 py-2 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors"
        >
          {showForm ? <X className="w-4 h-4" /> : <Plus className="w-4 h-4" />}
          {showForm ? 'キャンセル' : 'メニュー追加'}
        </button>
      </div>

      {/* Create form */}
      {showForm && (
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
          <h2 className="text-sm font-semibold text-zinc-300 mb-5">
            <span className="text-sky-400">#</span> 新規メニュー作成
          </h2>
          {error && (
            <div className="flex items-center gap-2 bg-red-400/10 border border-red-400/20 text-red-400 px-4 py-2 rounded-lg text-sm mb-4">
              <AlertCircle className="w-4 h-4 shrink-0" />
              {error}
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">予約タイプ</label>
              <div className="flex gap-3">
                {[
                  { value: ReservationType.DAYCARE,   label: '日帰り' },
                  { value: ReservationType.OVERNIGHT, label: '宿泊' },
                ].map(({ value, label }) => (
                  <button
                    key={value}
                    type="button"
                    onClick={() => setFormData({ ...formData, reservationType: value })}
                    className={`flex-1 py-2 rounded-lg text-sm font-medium border transition-all duration-150
                      ${formData.reservationType === value
                        ? 'bg-sky-500 text-white border-sky-500'
                        : 'bg-transparent text-zinc-400 border-zinc-700 hover:border-sky-400/50'
                      }`}
                  >
                    {label}
                  </button>
                ))}
              </div>
            </div>
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">メニュー名</label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                placeholder="スタンダードプラン"
                className={inputCls}
                required
              />
            </div>
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">説明</label>
              <textarea
                value={formData.feature}
                onChange={(e) => setFormData({ ...formData, feature: e.target.value })}
                placeholder="サービスの詳細"
                className={`${inputCls} resize-none`}
                rows={3}
              />
            </div>
            <button
              type="submit"
              disabled={createMutation.isPending}
              className="w-full py-2.5 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors disabled:opacity-50"
            >
              {createMutation.isPending ? '作成中...' : '作成する'}
            </button>
          </form>
        </div>
      )}

      {/* Menu list */}
      {!menus || menus.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <UtensilsCrossed className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">メニューがまだ登録されていません</p>
        </div>
      ) : (
        <div className="grid grid-cols-2 gap-3">
          {menus.map((menu: any) => (
            <div
              key={menu.id}
              className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
            >
              <div className="flex items-start justify-between mb-3">
                <span className={`px-2 py-0.5 rounded-md text-xs font-mono border
                  ${menu.reservationType === 'DAYCARE'
                    ? 'bg-sky-400/10 text-sky-400 border-sky-400/20'
                    : 'bg-violet-400/10 text-violet-400 border-violet-400/20'
                  }`}
                >
                  {typeLabel(menu.reservationType)}
                </span>
                <button
                  onClick={() => {
                    if (confirm(`「${menu.name}」を削除しますか？`)) deleteMutation.mutate(menu.id);
                  }}
                  className="text-zinc-600 hover:text-red-400 transition-colors"
                >
                  <Trash2 className="w-4 h-4" />
                </button>
              </div>
              <p className="text-sm font-semibold text-zinc-100">{menu.name}</p>
              {menu.feature && (
                <p className="text-xs text-zinc-500 mt-1 line-clamp-2">{menu.feature}</p>
              )}
            </div>
          ))}
        </div>
      )}

    </div>
  );
}
