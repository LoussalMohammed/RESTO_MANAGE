export interface BusinessHours {
  dayOfWeek: string;
  openTime: string;
  closeTime: string;
}

export interface RestaurantSettings {
  id: number;
  restaurantId: number;
  name: string;
  description: string;
  address: string;
  phoneNumber: string;
  email: string;
  logo: string;
  maxCapacity: number;
  averagePreparationTime: number;
  businessHours: BusinessHours[];
  createdAt: string;
  updatedAt: string;
}

export interface SalesReport {
  id: number;
  restaurantId: number;
  startDate: string;
  endDate: string;
  totalOrders: number;
  totalRevenue: number;
  averageOrderValue: number;
  topSellingMeals: {
    mealId: number;
    mealName: string;
    quantity: number;
    revenue: number;
  }[];
  paymentMethodStats: {
    method: string;
    count: number;
    total: number;
  }[];
  createdAt: string;
}
