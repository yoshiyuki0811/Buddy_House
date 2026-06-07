import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import {
  LayoutGrid, PawPrint, CalendarPlus, CalendarDays, User,
  Users, UtensilsCrossed, CalendarRange, ClipboardList, LogOut, Dog,
} from 'lucide-react';

const customerNav = [
  { to: '/',               icon: LayoutGrid,    label: 'ホーム' },
  { to: '/pets',           icon: PawPrint,      label: 'ペット管理' },
  { to: '/reservations/new', icon: CalendarPlus, label: '予約作成' },
  { to: '/reservations',   icon: CalendarDays,  label: '予約履歴' },
  { to: '/profile',        icon: User,          label: 'プロフィール' },
];

const adminNav = [
  { to: '/',                   icon: LayoutGrid,    label: 'ダッシュボード' },
  { to: '/admin/customers',    icon: Users,         label: '顧客管理' },
  { to: '/admin/pets',         icon: PawPrint,      label: 'ペット一覧' },
  { to: '/admin/menus',        icon: UtensilsCrossed, label: 'メニュー管理' },
  { to: '/admin/frames',       icon: CalendarRange, label: '予約枠管理' },
  { to: '/admin/reservations', icon: ClipboardList, label: '予約管理' },
];

export default function DashboardLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const logout   = useAuthStore((s) => s.logout);
  const role     = useAuthStore((s) => s.role);
  const email    = useAuthStore((s) => s.email);

  const nav = role === 'CUSTOMER' ? customerNav : adminNav;

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userRole');
    logout();
    navigate('/login');
  };

  return (
    <div className="flex h-screen bg-zinc-950 text-zinc-100">

      {/* ── Sidebar ── */}
      <aside className="w-60 border-r border-zinc-800 bg-zinc-900 flex flex-col shrink-0">

        {/* Logo */}
        <div className="h-14 flex items-center px-5 border-b border-zinc-800">
          <Dog className="w-5 h-5 text-sky-400 mr-2 shrink-0" />
          <span className="text-sm font-semibold tracking-tight select-none">
            Buddy <span className="text-sky-400 font-mono">House</span>
          </span>
        </div>

        {/* Nav items */}
        <nav className="flex-1 py-4 px-3 space-y-0.5 overflow-y-auto">
          {nav.map(({ to, icon: Icon, label }) => {
            const active = location.pathname === to;
            return (
              <button
                key={to}
                onClick={() => navigate(to)}
                className={`w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm transition-all duration-150 text-left
                  ${active
                    ? 'bg-zinc-800 text-sky-400'
                    : 'text-zinc-400 hover:bg-zinc-800/50 hover:text-zinc-100'
                  }`}
              >
                <Icon className="w-4 h-4 shrink-0" />
                {label}
              </button>
            );
          })}
        </nav>

        {/* User area */}
        <div className="border-t border-zinc-800 p-3 space-y-0.5">
          <div className="flex items-center gap-3 px-3 py-2">
            <div className="w-6 h-6 rounded-full bg-sky-500/20 border border-sky-400/30 flex items-center justify-center text-sky-400 text-xs font-semibold shrink-0">
              {email?.charAt(0).toUpperCase()}
            </div>
            <span className="text-xs text-zinc-500 truncate">{email}</span>
          </div>
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-3 py-2 rounded-lg text-sm text-zinc-500 hover:text-red-400 hover:bg-red-400/10 transition-colors"
          >
            <LogOut className="w-4 h-4 shrink-0" />
            ログアウト
          </button>
        </div>

      </aside>

      {/* ── Main ── */}
      <main className="flex-1 overflow-auto bg-zinc-950">
        <div className="max-w-5xl mx-auto px-8 py-8">
          <Outlet />
        </div>
      </main>

    </div>
  );
}
