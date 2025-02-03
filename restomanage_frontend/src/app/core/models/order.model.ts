export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  IN_PREPARATION = 'IN_PREPARATION',
  READY = 'READY',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface OrderItem {
  mealId: number;
  quantity: number;
}

export interface OrderRequest {
  clientId: number;
  restaurantId: number;
  items: OrderItem[];
  specialInstructions?: string;
}

export interface OrderResponse {
  id: number;
  clientId: number;
  clientName: string;
  restaurantId: number;
  restaurantName: string;
  items: OrderItem[];
  status: OrderStatus;
  totalAmount: number;
  specialInstructions?: string;
  createdAt: string;
  updatedAt: string;
}
