import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { frameAPI } from '../../services/api';
import { ReservationType } from '../../types';
import { Plus, X, Trash2, CalendarRange, Ban } from 'lucide-react';

const inputCls = `w-full px-3.5 py-2.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm
  text-zinc-100 placeholder-zinc-600 focus:outline-none focus:ring-1 focus:ring-sky-400
  focus:border-sky-400 transition`;

export default function FrameManagement() {
  const qc = useQueryClient();
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    reservationType: ReservationType.DAYCARE,
    startAt: '',
    endAt: '',
    maxDogs: 5,
  });

  const { data: frames, isLoading } = useQuery({
    queryKey: ['adminFrames'],
    queryFn: () => frameAPI.getList().then((r) => r.data),
  });

  const createMutation = useMutation({
    mutationFn: (data: any) => frameAPI.create(data),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['adminFrames'] });
      setShowForm(false);
      setFormData({ reservationType: ReservationType.DAYCARE, startAt: '', endAt: '', maxDogs: 5 });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: (id: number) => frameAPI.delete(id),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['adminFrames'] }),
  });

  const closeMutation = useMutation({
    mutationFn: (id: number) => frameAPI.close(id),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['adminFrames'] }),
  });

  const typeLabel = (type: string) => type === 'DAYCARE' ? '日帰り' : '宿泊';

  const fmt = (dt: string) =>
    new Date(dt).toLocaleDateString('ja-JP', { month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });

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
          <p className="text-xs font-mono text-zinc-500 mb-1">{frames?.length ?? 0} 枠</p>
          <h1 className="text-2xl font-bold text-zinc-100">予約枠管理</h1>
        </div>
        <button
          onClick={() => setShowForm(!showForm)}
          className="flex items-center gap-2 px-4 py-2 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors"
        >
          {showForm ? <X className="w-4 h-4" /> : <Plus className="w-4 h-4" />}
          {showForm ? 'キャンセル' : '予約枠を追加'}
        </button>
      </div>

      {/* Create form */}
      {showForm && (
        <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-6">
          <h2 className="text-sm font-semibold text-zinc-300 mb-5">
            <span className="text-sky-400">#</span> 新規予約枠作成
          </h2>
          <form onSubmit={(e) => { e.preventDefault(); createMutation.mutate(formData); }} className="space-y-4">
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
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">開始日時</label>
                <input
                  type="datetime-local"
                  value={formData.startAt}
                  onChange={(e) => setFormData({ ...formData, startAt: e.target.value })}
                  className={inputCls}
                  required
                />
              </div>
              <div>
                <label className="text-xs font-mono text-zinc-400 mb-1.5 block">終了日時</label>
                <input
                  type="datetime-local"
                  value={formData.endAt}
                  onChange={(e) => setFormData({ ...formData, endAt: e.target.value })}
                  className={inputCls}
                  required
                />
              </div>
            </div>
            <div>
              <label className="text-xs font-mono text-zinc-400 mb-1.5 block">最大頭数</label>
              <input
                type="number"
                min={1}
                value={formData.maxDogs}
                onChange={(e) => setFormData({ ...formData, maxDogs: parseInt(e.target.value) })}
                className={inputCls}
                required
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

      {/* Frame list */}
      {!frames || frames.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <CalendarRange className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">予約枠がまだ登録されていません</p>
        </div>
      ) : (
        <div className="space-y-3">
          {frames.map((frame: any) => {
            const available = frame.maxDogs - frame.usedDogs;
            const isFull = available <= 0;
            return (
              <div
                key={frame.id}
                className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
              >
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <div className="flex items-center gap-2 mb-2">
                      <span className={`px-2 py-0.5 rounded-md text-xs font-mono border
                        ${frame.reservationType === 'DAYCARE'
                          ? 'bg-sky-400/10 text-sky-400 border-sky-400/20'
                          : 'bg-violet-400/10 text-violet-400 border-violet-400/20'
                        }`}
                      >
                        {typeLabel(frame.reservationType)}
                      </span>
                      {isFull && (
                        <span className="px-2 py-0.5 rounded-md text-xs font-mono border bg-red-400/10 text-red-400 border-red-400/20">
                          満室
                        </span>
                      )}
                    </div>
                    <p className="text-sm font-medium text-zinc-100">
                      {fmt(frame.startAt)} 〜 {fmt(frame.endAt)}
                    </p>
                    <p className="text-xs font-mono mt-1">
                      <span className="text-zinc-500">使用: </span>
                      <span className="text-zinc-300">{frame.usedDogs} / {frame.maxDogs} 頭</span>
                      {!isFull && (
                        <span className="text-green-400 ml-2">（残り {available} 頭）</span>
                      )}
                    </p>
                  </div>
                  <div className="flex gap-2 ml-4 shrink-0">
                    <button
                      onClick={() => {
                        if (confirm('この予約枠を満室にしますか？')) closeMutation.mutate(frame.id);
                      }}
                      disabled={isFull}
                      className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium border border-zinc-700 text-zinc-400 hover:border-orange-400/50 hover:text-orange-400 transition-colors disabled:opacity-30 disabled:cursor-not-allowed"
                    >
                      <Ban className="w-3.5 h-3.5" />
                      満室にする
                    </button>
                    <button
                      onClick={() => {
                        if (confirm('この予約枠を削除しますか？')) deleteMutation.mutate(frame.id);
                      }}
                      className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium border border-zinc-700 text-zinc-400 hover:border-red-400/50 hover:text-red-400 transition-colors"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                      削除
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

    </div>
  );
}
