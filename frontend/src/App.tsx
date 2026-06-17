import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useAuthStore } from './store/authStore';

// Pages
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import DashboardLayout from './layouts/DashboardLayout';
import NotFoundPage from './pages/NotFoundPage';

// Customer Routes
import CustomerHome from './pages/customer/Home';
import PetManagement from './pages/customer/PetManagement';
import ReservationBooking from './pages/customer/ReservationBooking';
import ReservationHistory from './pages/customer/ReservationHistory';
import CustomerProfile from './pages/customer/Profile';

// Admin Routes
import AdminDashboard from './pages/admin/Dashboard';
import CustomerManagement from './pages/admin/CustomerManagement';
import PetListAdmin from './pages/admin/PetListAdmin';
import MenuManagement from './pages/admin/MenuManagement';
import FrameManagement from './pages/admin/FrameManagement';
import ReservationManagement from './pages/admin/ReservationManagement';

const queryClient = new QueryClient();

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated());
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />;
}

function RoleRoute({ children, requiredRole }: { children: React.ReactNode; requiredRole: 'ADMIN' | 'CUSTOMER' }) {
  const role = useAuthStore((state) => state.role);
  return role === requiredRole ? <>{children}</> : <Navigate to="/" replace />;
}

export default function App() {
  const token = useAuthStore((state) => state.token);
  const role = useAuthStore((state) => state.role);

  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          {/* Public Routes */}
          {!token ? (
            <>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/signup" element={<SignupPage />} />
              <Route path="*" element={<Navigate to="/login" replace />} />
            </>
          ) : (
            <>
              {/* Customer Routes */}
              {role === 'CUSTOMER' && (
                <Route element={<DashboardLayout />}>
                  <Route path="/" element={<CustomerHome />} />
                  <Route path="/pets" element={<PetManagement />} />
                  <Route path="/reservations/new" element={<ReservationBooking />} />
                  <Route path="/reservations" element={<ReservationHistory />} />
                  <Route path="/profile" element={<CustomerProfile />} />
                </Route>
              )}

              {/* Admin Routes */}
              {role === 'ADMIN' && (
                <Route element={<DashboardLayout />}>
                  <Route path="/" element={<AdminDashboard />} />
                  <Route path="/admin/customers" element={<CustomerManagement />} />
                  <Route path="/admin/pets" element={<PetListAdmin />} />
                  <Route path="/admin/menus" element={<MenuManagement />} />
                  <Route path="/admin/frames" element={<FrameManagement />} />
                  <Route path="/admin/reservations" element={<ReservationManagement />} />
                </Route>
              )}

              <Route path="*" element={<NotFoundPage />} />
            </>
          )}
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
