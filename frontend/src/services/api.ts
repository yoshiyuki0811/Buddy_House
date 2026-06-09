import axios, { AxiosInstance } from 'axios';
import { TokenResponse, AuthRequest, SignupRequest, PetRequest } from '../types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

const createAxiosInstance = (): AxiosInstance => {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // Request Interceptor: Add JWT token
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

  // Response Interceptor: Handle errors
  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        // Token expired or invalid
        localStorage.removeItem('accessToken');
        localStorage.removeItem('userRole');
        window.location.href = '/login';
      }
      return Promise.reject(error);
    }
  );

  return instance;
};

export const apiClient = createAxiosInstance();

// Auth APIs
export const authAPI = {
  login: (credentials: AuthRequest) =>
    apiClient.post<TokenResponse>('/api/auth/login', credentials),

  signup: (data: SignupRequest) =>
    apiClient.post('/api/auth/signup', data),
};

// Customer APIs
export const customerAPI = {
  getMe: () => apiClient.get('/api/customers/me'),
  
  createCustomer: (data: any) =>
    apiClient.post('/api/customers/me', data),

  // Admin
  getList: () => apiClient.get('/api/admin/customers'),
  
  getById: (id: number) =>
    apiClient.get(`/api/admin/customers/${id}`),

  update: (id: number, data: any) =>
    apiClient.patch(`/api/admin/customers/${id}`, data),

  delete: (id: number) =>
    apiClient.patch(`/api/admin/customers/${id}/delete`),
};

// Pet APIs
export const petAPI = {
  getMyList: () => apiClient.get('/api/pets/me'),
  
  getById: (id: number) =>
    apiClient.get(`/api/pets/${id}`),

  create: (data: any) =>
    apiClient.post('/api/pets/me', data),

  updateMe: (id: number, data: PetRequest) =>
    apiClient.patch(`/api/pets/me/${id}`, data),

  // Admin
  getList: () => apiClient.get('/api/admin/pets'),
  
  getListByCustomer: (customerId: number) =>
    apiClient.get(`/api/admin/pets/${customerId}/pets`),

  createForCustomer: (customerId: number, data: any) =>
    apiClient.post(`/api/admin/pets/${customerId}/pets`, data),

  update: (id: number, data: any) =>
    apiClient.patch(`/api/admin/pets/${id}`, data),

  delete: (id: number) =>
    apiClient.patch(`/api/admin/pets/${id}/deleted`),
};

// Menu APIs
export const menuAPI = {
  getList: () => apiClient.get('/api/menus'),

  // Admin
  adminGetList: () => apiClient.get('/api/admin/menus'),
  
  create: (type: string, data: any) =>
    apiClient.post(`/api/admin/menus/${type}`, data),

  delete: (id: number) =>
    apiClient.patch(`/api/admin/menus/${id}/deleted`),
};

// Reservation Frame APIs
export const frameAPI = {
  getList: () => apiClient.get('/api/reservationFrame'),

  getListByDate: (date: string) =>
    apiClient.get('/api/reservationFrame/by-date', { params: { dateTime: date } }),

  create: (data: any) =>
    apiClient.post('/api/admin/reservationFrame', data),

  delete: (id: number) =>
    apiClient.patch(`/api/admin/reservationFrame/${id}/deleted`),

  close: (id: number) =>
    apiClient.patch(`/api/admin/reservationFrame/${id}/close`),
};

// Reservation APIs
export const reservationAPI = {
  getMyList: () => apiClient.get('/api/reservation/me'),
  
  create: (data: any) =>
    apiClient.post('/api/reservation', data),

  // Admin
  getList: (date?: string) => {
    const params = date ? { date } : {};
    return apiClient.get('/api/admin/reservation', { params });
  },

  getById: (id: number) =>
    apiClient.get(`/api/admin/reservation/${id}`),

  getByCustomerId: (customerId: number) =>
    apiClient.get(`/api/admin/reservation/customers/${customerId}`),

  cancel: (id: number) =>
    apiClient.patch(`/api/admin/reservation/${id}/cancel`),
};
