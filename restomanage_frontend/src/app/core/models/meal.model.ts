export enum MealCategory {
  APPETIZER = 'APPETIZER',
  MAIN_COURSE = 'MAIN_COURSE',
  DESSERT = 'DESSERT',
  BEVERAGE = 'BEVERAGE',
  SIDE_DISH = 'SIDE_DISH'
}

export interface Ingredient {
  id: number;
  name: string;
  unit: string;
  quantity: number;
  minQuantity: number;
  requiredQuantity: number;
}

export interface MealRequest {
  name: string;
  description: string;
  price: number;
  category: MealCategory;
  available: boolean;
  imageUrl: string;
  preparationTime: number;
  restaurantId: number;
  ingredients: Ingredient[];
}

export interface MealResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  category: MealCategory;
  available: boolean;
  imageUrl: string;
  preparationTime: number;
  restaurantId: number;
  restaurantName: string;
  ingredients: Ingredient[];
  createdAt: string;
  updatedAt: string;
}
