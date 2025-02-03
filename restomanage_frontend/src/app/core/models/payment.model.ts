export enum PaymentMethod {
  CREDIT_CARD = 'CREDIT_CARD',
  DEBIT_CARD = 'DEBIT_CARD',
  CASH = 'CASH',
  MOBILE_PAYMENT = 'MOBILE_PAYMENT'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED'
}

export interface PaymentRequest {
  orderId: number;
  amount: number;
  method: PaymentMethod;
}

export interface PaymentResponse {
  id: number;
  orderId: number;
  clientId: number;
  clientName: string;
  amount: number;
  method: PaymentMethod;
  status: PaymentStatus;
  transactionId: string;
  createdAt: string;
  updatedAt: string;
}
