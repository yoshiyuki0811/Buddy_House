import { useQuery } from '@tanstack/react-query';
import { reservationAPI } from '../../services/api';
import { CalendarDays } from 'lucide-react';

const statusConfig: Record<string, { label: string; cls: string }> = {
  RESERVED:    { label: '予約済',     cls: 'bg-sky-400/10 text-sky-400 border-sky-400/20' },
  CHECKED_IN:  { label: '滞在中',     cls: 'bg-green-400/10 text-green-400 border-green-400/20' },
  CHECKED_OUT: { label: 'チェックアウト済', cls: 'bg-zinc-500/10 text-zinc-400 border-zinc-500/20' },
  CANCELLED:   { label: 'キャンセル', cls: 'bg-red-400/10 text-red-400 border-red-400/20' },
};

export default function ReservationHistory() {
  const { data: reservations, isLoading } = useQuery({
    queryKey: ['myReservations'],
    queryFn: () => reservationAPI.getMyList().then((r) => r.data),
  });

  const fmt = (dt: string) =>
    new Date(dt).toLocaleDateString('ja-JP', { year: 'numeric', month: 'short', day: 'numeric' });

  if (isLoading) return (
    <div className="flex items-center justify-center py-24">
      <div className="w-6 h-6 rounded-full border-2 border-sky-400 border-t-transparent animate-spin" />
    </div>
  );

  return (
    <div className="space-y-6">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">// {reservations?.length ?? 0} 件の予約</p>
        <h1 className="text-2xl font-bold text-zinc-100">予約履歴</h1>
      </div>

      {!reservations || reservations.length === 0 ? (
        <div className="rounded-xl border border-dashed border-zinc-700 p-16 text-center">
          <CalendarDays className="w-10 h-10 text-zinc-600 mx-auto mb-3" />
          <p className="text-zinc-500 text-sm">まだ予約がありません</p>
        </div>
      ) : (
        <div className="space-y-3">
          {reservations.map((r: any) => {
            const sc = statusConfig[r.status] ?? statusConfig['RESERVED'];
            return (
              <div
                key={r.id}
                className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
              >
                <div className="flex items-start justify-between mb-3">
                  <div>
                    <p className="text-sm font-semibold text-zinc-100">{r.menuName}</p>
                    <p className="text-xs font-mono text-zinc-500 mt-0.5">#{r.id}</p>
                  </div>
                  <span className={`px-2 py-0.5 rounded-md text-xs font-mono border ${sc.cls}`}>
                    {sc.label}
                  </span>
                </div>

                <div className="flex items-center gap-1.5 text-xs text-zinc-400 mb-3">
                  <span className="font-mono">{fmt(r.startAt)}</span>
                  <span className="text-zinc-600">→</span>
                  <span className="font-mono">{fmt(r.endAt)}</span>
                </div>

                <div className="flex gap-1.5 flex-wrap">
                  {r.petsName?.map((name: string, i: number) => (
                    <span
                      key={i}
                      className="px-2 py-0.5 rounded-md bg-zinc-700/50 text-zinc-300 text-xs"
                    >
                      {name}
                    </span>
                  ))}
                </div>
              </div>
            );
          })}
        </div>
      )}

    </div>
  );
}
