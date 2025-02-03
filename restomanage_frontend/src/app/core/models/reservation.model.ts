export interface ReservationRequest {
  clientId: number;
  restaurantId: number;
  date: string;
  time: string;
  numberOfGuests: number;
  specialRequests?: string;
}

export interface ReservationResponse {
  id: number;
  clientId: number;
  clientName: string;
  restaurantId: number;
  restaurantName: string;
  date: string;
  time: string;
  numberOfGuests: number;
  specialRequests?: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}
