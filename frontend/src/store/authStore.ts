import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface AuthState {
  token: string | null;
  role: 'ADMIN' | 'CUSTOMER' | null;
  email: string | null;
  setAuth: (token: string, role: 'ADMIN' | 'CUSTOMER', email: string) => void;
  logout: () => void;
  isAuthenticated: () => boolean;
}

export const useAuthStore = create<AuthState>(
  persist(
    (set, get) => ({
      token: null,
      role: null,
      email: null,
      setAuth: (token, role, email) =>
        set({ token, role, email }),
      logout: () =>
        set({ token: null, role: null, email: null }),
      isAuthenticated: () => {
        const { token } = get();
        return token !== null;
      },
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({
        token: state.token,
        role: state.role,
        email: state.email,
      }),
    }
  )
);
