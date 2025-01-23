export enum StaffRole {
  ADMIN = 'ADMIN',
  MANAGER = 'MANAGER',
  WAITER = 'WAITER',
  CHEF = 'CHEF',
  CASHIER = 'CASHIER'
}

export interface Staff {
  id?: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  role: StaffRole;
  active: boolean;
  restaurantId: number;
}
