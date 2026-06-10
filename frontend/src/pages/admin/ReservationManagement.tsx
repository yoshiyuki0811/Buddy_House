import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { reservationAPI } from '../../services/api';
import { ClipboardList, X, Ban } from 'lucide-react';

const statusConfig: Record<string, { label: string; cls: string }> = {
  RESERVED:    { label: '予約済',     cls: 'bg-sky-400/10 text-sky-400 border-sky-400/20' },
  CHECKED_IN:  { label: '滞在中',     cls: 'bg-green-400/10 text-green-400 border-green-400/20' },
  CHECKED_OUT: { label: 'チェックアウト済', cls: 'bg-zinc-500/10 text-zinc-400 border-zinc-500/20' },
  CANCELLED:   { label: 'キャンセル', cls: 'bg-red-400/10 text-red-400 border-red-400/20' },
};

export default function ReservationManagement() {
  const qc = useQueryClient();
  const [selectedDate, setSelectedDate] = useState('');

  const { data: reservations, isLoading } = useQuery({
    queryKey: ['allReservations', selectedDate],
    queryFn: () => reservationAPI.getList(selectedDate || undefined).then((r) => r.data),
  });

  const cancelMutation = useMutation({
    mutationFn: (id: number) => reservationAPI.cancel(id),
    onSuccess: () => qc.invalidateQueries({ queryKey: ['allReservations'] }),
  });

  const fmt = (dt: string) =>
    new Date(dt).toLocaleString('ja-JP', {
      year: 'numeric', month: '2-digit', day: '2-digit',
      hour: '2-digit', minute: '2-digit',
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
          <p className="text-xs font-mono text-zinc-500 mb-1">{reservations?.length ?? 0} 件</p>
          <h1 className="text-2xl font-bold text-zinc-100">予約管理</h1>
        </div>
      </div>

      {/* Date filter */}
      <div className="rounded-xl border border-zinc-700 bg-zinc-800/50 px-5 py-4 flex items-center gap-4">
        <label className="text-xs font-mono text-zinc-400 shrink-0">日付で絞り込み</label>
        <input
          type="date"
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.target.value)}
          className="px-3 py-1.5 bg-zinc-900 border border-zinc-700 rounded-lg text-sm text-zinc-100 focus:outline-none focus:ring-1 focus:ring-sky-400 focus:border-sky-400 transition"
        />
        {selectedDate && (
          <button
            onClick={() => setSelectedDate('')}
            className="flex items-center gap-1 text-xs text-zinc-400 hover:text-zinc-200 transition-colors"
          >
            <X className="w-3.5 h-3.5" />
            クリア
          </button>
        )}
      </div>

      {/* Reservation list */}
      {!reservations || reservations.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <ClipboardList className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">予約がありません</p>
        </div>
      ) : (
        <div className="space-y-3">
          {reservations.map((r: any) => {
            const sc = statusConfig[r.status] ?? statusConfig['RESERVED'];
            const canCancel = r.status === 'RESERVED' || r.status === 'CHECKED_IN';
            return (
              <div
                key={r.id}
                className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
              >
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <div className="flex items-center gap-3 mb-2">
                      <p className="text-xs font-mono text-zinc-500">#{r.id}</p>
                      <span className={`px-2 py-0.5 rounded-md text-xs font-mono border ${sc.cls}`}>
                        {sc.label}
                      </span>
                      <p className="text-sm font-semibold text-sky-400">{r.menuName}</p>
                    </div>
                    <p className="text-sm text-zinc-200 mb-1">
                      <span className="text-zinc-500 font-mono text-xs">顧客: </span>
                      {r.customerName}
                    </p>
                    <div className="flex gap-6 text-xs font-mono text-zinc-500 mb-3">
                      <span>開始: <span className="text-zinc-300">{fmt(r.startAt)}</span></span>
                      <span>終了: <span className="text-zinc-300">{fmt(r.endAt)}</span></span>
                    </div>
                    <div className="flex gap-1.5 flex-wrap">
                      {r.petsName?.map((name: string, i: number) => (
                        <span key={i} className="px-2 py-0.5 rounded-md bg-zinc-700/50 text-zinc-300 text-xs">
                          {name}
                        </span>
                      ))}
                    </div>
                  </div>

                  {canCancel && (
                    <button
                      onClick={() => {
                        if (confirm(`予約 #${r.id} をキャンセルしますか？`)) {
                          cancelMutation.mutate(r.id);
                        }
                      }}
                      disabled={cancelMutation.isPending}
                      className="ml-4 shrink-0 flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-medium border border-zinc-700 text-zinc-400 hover:border-red-400/50 hover:text-red-400 transition-colors disabled:opacity-50"
                    >
                      <Ban className="w-3.5 h-3.5" />
                      キャンセル
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}

    </div>
  );
}
