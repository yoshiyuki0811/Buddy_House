import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';

export default function DashboardLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const logout = useAuthStore((state) => state.logout);
  const role = useAuthStore((state) => state.role);

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userRole');
    logout();
    navigate('/login');
  };

  const isActive = (path: string) => location.pathname === path;

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <div className="w-64 bg-gradient-to-b from-purple-600 to-purple-800 text-white p-6">
        <div className="mb-8">
          <h1 className="text-2xl font-bold mb-2">🐾 Buddy House</h1>
          <p className="text-purple-200 text-sm">ペットホテル管理システム</p>
        </div>

        <nav className="space-y-2">
          {role === 'CUSTOMER' ? (
            <>
              <NavLink
                to="/"
                label="ダッシュボード"
                isActive={isActive('/')}
              />
              <NavLink
                to="/pets"
                label="ペット管理"
                isActive={isActive('/pets')}
              />
              <NavLink
                to="/reservations/new"
                label="予約作成"
                isActive={isActive('/reservations/new')}
              />
              <NavLink
                to="/reservations"
                label="予約履歴"
                isActive={isActive('/reservations')}
              />
              <NavLink
                to="/profile"
                label="プロフィール"
                isActive={isActive('/profile')}
              />
            </>
          ) : (
            <>
              <NavLink
                to="/"
                label="ダッシュボード"
                isActive={isActive('/')}
              />
              <NavLink
                to="/admin/customers"
                label="顧客管理"
                isActive={isActive('/admin/customers')}
              />
              <NavLink
                to="/admin/pets"
                label="ペット管理"
                isActive={isActive('/admin/pets')}
              />
              <NavLink
                to="/admin/menus"
                label="メニュー管理"
                isActive={isActive('/admin/menus')}
              />
              <NavLink
                to="/admin/frames"
                label="予約枠管理"
                isActive={isActive('/admin/frames')}
              />
              <NavLink
                to="/admin/reservations"
                label="予約管理"
                isActive={isActive('/admin/reservations')}
              />
            </>
          )}
        </nav>

        <button
          onClick={handleLogout}
          className="w-full mt-8 bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded-lg transition"
        >
          ログアウト
        </button>
      </div>

      {/* Main Content */}
      <div className="flex-1 overflow-auto">
        <div className="p-8">
          <Outlet />
        </div>
      </div>
    </div>
  );
}

function NavLink({
  to,
  label,
  isActive,
}: {
  to: string;
  label: string;
  isActive: boolean;
}) {
  const navigate = useNavigate();
  return (
    <button
      onClick={() => navigate(to)}
      className={`w-full text-left px-4 py-2 rounded-lg transition ${
        isActive
          ? 'bg-purple-500 text-white'
          : 'text-purple-100 hover:bg-purple-700'
      }`}
    >
      {label}
    </button>
  );
}
