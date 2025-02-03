export interface ReviewRequest {
  clientId: number;
  restaurantId: number;
  rating: number;
  comment: string;
}

export interface ReviewResponse {
  id: number;
  clientId: number;
  clientName: string;
  restaurantId: number;
  restaurantName: string;
  rating: number;
  comment: string;
  createdAt: string;
}
