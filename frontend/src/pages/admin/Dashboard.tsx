import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { customerAPI, reservationAPI, petAPI } from '../../services/api';
import { Users, PawPrint, CalendarDays, ClipboardList, UtensilsCrossed, CalendarRange, ChevronRight } from 'lucide-react';

const statusConfig: Record<string, { label: string; cls: string }> = {
  RESERVED:    { label: '予約済',     cls: 'bg-sky-400/10 text-sky-400 border-sky-400/20' },
  CHECKED_IN:  { label: '滞在中',     cls: 'bg-green-400/10 text-green-400 border-green-400/20' },
  CHECKED_OUT: { label: 'チェックアウト済', cls: 'bg-zinc-500/10 text-zinc-400 border-zinc-500/20' },
  CANCELLED:   { label: 'キャンセル', cls: 'bg-red-400/10 text-red-400 border-red-400/20' },
};

export default function AdminDashboard() {
  const navigate = useNavigate();

  const { data: customers    } = useQuery({ queryKey: ['allCustomers'],    queryFn: () => customerAPI.getList().then((r) => r.data) });
  const { data: reservations } = useQuery({ queryKey: ['allReservations'], queryFn: () => reservationAPI.getList().then((r) => r.data) });
  const { data: pets         } = useQuery({ queryKey: ['allPets'],         queryFn: () => petAPI.getList().then((r) => r.data) });

  const stats = [
    { label: '顧客',   value: customers?.length    ?? '–', icon: Users },
    { label: '予約',   value: reservations?.length ?? '–', icon: CalendarDays },
    { label: 'ペット', value: pets?.length          ?? '–', icon: PawPrint },
  ];

  const links = [
    { to: '/admin/reservations', icon: ClipboardList,   label: '予約管理',     desc: '確認・キャンセル' },
    { to: '/admin/customers',    icon: Users,            label: '顧客管理',     desc: '顧客情報の管理' },
    { to: '/admin/frames',       icon: CalendarRange,    label: '予約枠管理',   desc: '空き枠の設定' },
    { to: '/admin/menus',        icon: UtensilsCrossed,  label: 'メニュー管理', desc: 'サービスの設定' },
  ];

  const fmt = (dt: string) =>
    new Date(dt).toLocaleDateString('ja-JP', { month: 'short', day: 'numeric' });

  return (
    <div className="space-y-8">

      {/* Header */}
      <div>
        <p className="text-xs font-mono text-zinc-500 mb-1">// 管理ポータル</p>
        <h1 className="text-2xl font-bold text-zinc-100">ダッシュボード</h1>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-3 gap-4">
        {stats.map(({ label, value, icon: Icon }) => (
          <div
            key={label}
            className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200"
          >
            <Icon className="w-4 h-4 text-sky-400 mb-3" />
            <p className="text-3xl font-bold text-zinc-100 font-mono">{value}</p>
            <p className="text-xs text-zinc-500 mt-1">{label}</p>
          </div>
        ))}
      </div>

      {/* Quick links */}
      <div>
        <h2 className="text-sm font-semibold text-zinc-300 mb-4">
          <span className="text-sky-400">#</span> クイックアクション
        </h2>
        <div className="grid grid-cols-2 gap-3">
          {links.map(({ to, icon: Icon, label, desc }) => (
            <button
              key={to}
              onClick={() => navigate(to)}
              className="rounded-xl border border-zinc-700 bg-zinc-800/50 p-5 hover:border-sky-400/50 transition-all duration-200 text-left group"
            >
              <div className="flex items-center justify-between mb-3">
                <Icon className="w-5 h-5 text-sky-400" />
                <ChevronRight className="w-4 h-4 text-zinc-600 group-hover:text-sky-400 transition-colors" />
              </div>
              <p className="text-sm font-semibold text-zinc-100">{label}</p>
              <p className="text-xs text-zinc-500 mt-0.5">{desc}</p>
            </button>
          ))}
        </div>
      </div>

      {/* Recent reservations */}
      {reservations && reservations.length > 0 && (
        <div>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-sm font-semibold text-zinc-300">
              <span className="text-sky-400">#</span> 直近の予約
            </h2>
            <button
              onClick={() => navigate('/admin/reservations')}
              className="flex items-center gap-1 text-xs text-sky-400 hover:text-sky-300 transition-colors"
            >
              すべて見る <ChevronRight className="w-3 h-3" />
            </button>
          </div>
          <div className="space-y-2">
            {reservations.slice(0, 5).map((r: any) => {
              const sc = statusConfig[r.status] ?? statusConfig['RESERVED'];
              return (
                <div
                  key={r.id}
                  className="rounded-xl border border-zinc-700 bg-zinc-800/50 px-5 py-4 hover:border-sky-400/50 transition-all duration-200"
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                      <p className="text-xs font-mono text-zinc-500">#{r.id}</p>
                      <div>
                        <p className="text-sm font-medium text-zinc-100">{r.customerName}</p>
                        <p className="text-xs text-zinc-500">{r.menuName}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <p className="text-xs font-mono text-zinc-500">{fmt(r.startAt)}</p>
                      <span className={`px-2 py-0.5 rounded-md text-xs font-mono border ${sc.cls}`}>
                        {sc.label}
                      </span>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}

    </div>
  );
}
