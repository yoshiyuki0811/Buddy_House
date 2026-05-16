// Auth Types
export interface AuthRequest {
  email: string;
  password: string;
}

export interface TokenResponse {
  accessToken: string;
}

export interface SignupRequest {
  email: string;
  password: string;
  name: string;
  address: string;
  phone: string;
}

// Customer Types
export interface Customer {
  id: Long;
  name: string;
  address: string;
  email: string;
  phone: string;
}

export interface CustomerDetail extends Customer {}

export interface CustomerRequest {
  name: string;
  address: string;
  phone: string;
}

// Pet Types
export enum WeightCategory {
  Toy = 'Toy',      // ～5kg
  Small = 'Small',  // 5～10kg
  Medium = 'Medium', // 10～15kg
  Large = 'Large',  // 15～20kg
  Giant = 'Giant'   // 20kg～
}

export interface Pet {
  id: Long;
  name: string;
  breed: string;
  weight: WeightCategory;
  age?: number;
  feature?: string;
}

export interface PetRequest {
  name: string;
  breed: string;
  weight: WeightCategory;
  age?: number;
  feature?: string;
}

// Menu Types
export enum ReservationType {
  OVERNIGHT = 'OVERNIGHT',
  DAYCARE = 'DAYCARE'
}

export interface Menu {
  id: Long;
  name: string;
  description?: string;
  price: number;
  type: ReservationType;
}

export interface CreateMenuRequest {
  name: string;
  description?: string;
  price: number;
}

// Reservation Frame Types
export interface ReservationFrame {
  id: Long;
  type: ReservationType;
  startAt: string; // ISO 8601 DateTime
  endAt: string;   // ISO 8601 DateTime
  maxDogs: number;
}

export interface FrameRequest {
  reservationType: ReservationType;
  startAt: string;
  endAt: string;
  maxDogs: number;
}

// Reservation Types
export interface Reservation {
  id: Long;
  customerName: string;
  menuName: string;
  petsName: string[];
  startAt: string;
  endAt: string;
}

export interface ReservationDetail extends Reservation {}

export interface ReservationRequest {
  reservationFrameId: Long;
  menuId: Long;
  petIds: Long[];
}

// Auth State
export interface AuthState {
  token: string | null;
  role: 'ADMIN' | 'CUSTOMER' | null;
  isAuthenticated: boolean;
}

export type Long = number;
