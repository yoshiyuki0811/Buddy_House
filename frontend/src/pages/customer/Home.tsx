import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { customerAPI, reservationAPI, frameAPI } from '../../services/api';
import { CalendarPlus, PawPrint, User, ChevronRight, Calendar } from 'lucide-react';

const statusConfig: Record<string, { label: string; cls: string }> = {
  RESERVED:    { label: '予約済',     cls: 'bg-sky-400/10 text-sky-400 border-sky-400/20' },
  CHECKED_IN:  { label: '滞在中',     cls: 'bg-green-400/10 text-green-400 border-green-400/20' },
  CHECKED_OUT: { label: 'チェックアウト済', cls: 'bg-zinc-500/10 text-zinc-400 border-zinc-500/20' },
  CANCELLED:   { label: 'キャンセル', cls: 'bg-red-400/10 text-red-400 border-red-400/20' },
};

export default function CustomerHome() {
  const navigate = useNavigate();

  const { data: customer } = useQuery({
    queryKey: ['customerMe'],
    queryFn: () => customerAPI.getMe().then((r) => r.data),
  });
  const { data: reservations } = useQuery({
    queryKey: ['myReservations'],
    queryFn: () => reservationAPI.getMyList().then((r) => r.data),
  });
  const { data: frames } = useQuery({
    queryKey: ['frames'],
    queryFn: () => frameAPI.getList().then((r) => r.data),
  });

  const availableFrames = (frames ?? []).filter(
    (f: any) => f.maxDogs - f.usedDogs > 0
  );

  const fmt = (dt: string) =>
    new Date(dt).toLocaleDateString('ja-JP', { month: 'short', day: 'numeric' });

  return (
    <div className="space-y-8">

      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <p className="text-xs font-mono text-zinc-500 mb-1">// ようこそ</p>
          <h1 className="text-2xl font-bold text-zinc-100">
            {customer?.name ?? '...'}
          </h1>
        </div>
        <button
          onClick={() => navigate('/reservations/new')}
          className="flex items-center gap-2 px-4 py-2 rounded-lg bg-sky-500 text-white font-medium hover:bg-sky-400 active:bg-sky-600 transition-colors"
        >
          <CalendarPlus className="w-4 h-4" />
          予約する
        </button>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-3 gap-4">
        {[
          { value: reservations?.length ?? 0, label: '予約数', icon: Calendar },
          { value: availableFrames.length,    label: '空き枠数', icon: CalendarPlus },
          { value: (reservations ?? []).filter((r: any) => r.status === 'RESERVED').length, label: '確定予約', icon: PawPrint },
        ].map(({ value, label, icon: Icon }) => (
          <div
            key={label}
            className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
          >
            <Icon className="w-4 h-4 text-sky-400 mb-3" />
            <p className="text-2xl font-bold text-zinc-100 font-mono">{value}</p>
            <p className="text-xs text-zinc-500 mt-1">{label}</p>
          </div>
        ))}
      </div>

      {/* Available frames */}
      {availableFrames.length > 0 && (
        <div>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-sm font-semibold text-zinc-300">
              <span className="text-sky-400">#</span> 空き予約枠
            </h2>
            <button
              onClick={() => navigate('/reservations/new')}
              className="flex items-center gap-1 text-xs text-sky-400 hover:text-sky-300 transition-colors"
            >
              すべて見る <ChevronRight className="w-3 h-3" />
            </button>
          </div>
          <div className="grid grid-cols-2 gap-3">
            {availableFrames.slice(0, 4).map((f: any) => (
              <button
                key={f.id}
                onClick={() => navigate('/reservations/new')}
                className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-4 hover:border-sky-400/50 transition-all duration-200 text-left"
              >
                <span className="px-2 py-0.5 rounded-md bg-sky-400/10 text-sky-400 text-xs font-mono border border-sky-400/20">
                  {f.reservationType === 'DAYCARE' ? 'DAYCARE' : 'OVERNIGHT'}
                </span>
                <p className="text-sm text-zinc-200 font-medium mt-2">{fmt(f.startAt)}</p>
                <p className="text-xs text-zinc-500 mt-0.5">残り {f.maxDogs - f.usedDogs} 頭</p>
              </button>
            ))}
          </div>
        </div>
      )}

      {/* Recent reservations */}
      {reservations && reservations.length > 0 && (
        <div>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-sm font-semibold text-zinc-300">
              <span className="text-sky-400">#</span> 最近の予約
            </h2>
            <button
              onClick={() => navigate('/reservations')}
              className="flex items-center gap-1 text-xs text-sky-400 hover:text-sky-300 transition-colors"
            >
              すべて見る <ChevronRight className="w-3 h-3" />
            </button>
          </div>
          <div className="space-y-3">
            {reservations.slice(0, 3).map((r: any) => {
              const sc = statusConfig[r.status] ?? statusConfig['RESERVED'];
              return (
                <div
                  key={r.id}
                  className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-4 hover:border-sky-400/50 transition-all duration-200"
                >
                  <div className="flex items-start justify-between">
                    <div>
                      <p className="text-sm font-semibold text-zinc-100">{r.menuName}</p>
                      <p className="text-xs text-zinc-500 mt-0.5">
                        {fmt(r.startAt)} → {fmt(r.endAt)}
                      </p>
                      <div className="flex gap-1.5 flex-wrap mt-2">
                        {r.petsName?.map((n: string, i: number) => (
                          <span key={i} className="px-2 py-0.5 rounded-md bg-zinc-700/50 text-zinc-300 text-xs">
                            {n}
                          </span>
                        ))}
                      </div>
                    </div>
                    <span className={`px-2 py-0.5 rounded-md text-xs font-mono border ${sc.cls}`}>
                      {sc.label}
                    </span>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}

      {/* Quick actions */}
      <div className="grid grid-cols-2 gap-4">
        {[
          { icon: PawPrint, label: 'ペット管理', desc: '登録ペットの確認・追加', to: '/pets' },
          { icon: User,     label: 'プロフィール', desc: 'アカウント情報の確認', to: '/profile' },
        ].map(({ icon: Icon, label, desc, to }) => (
          <button
            key={to}
            onClick={() => navigate(to)}
            className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200 text-left"
          >
            <Icon className="w-5 h-5 text-sky-400 mb-3" />
            <p className="text-sm font-semibold text-zinc-100">{label}</p>
            <p className="text-xs text-zinc-500 mt-0.5">{desc}</p>
          </button>
        ))}
      </div>

    </div>
  );
}
