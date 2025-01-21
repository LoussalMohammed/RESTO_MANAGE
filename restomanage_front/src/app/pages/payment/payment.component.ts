// payment.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-gray-50">
      <h1 class="text-4xl font-bold text-gray-800 mb-8">
        Payment Gateway 💳
      </h1>

      <div class="max-w-md mx-auto bg-white p-6 rounded-lg shadow-md">
        <div class="mb-6">
          <label class="block text-gray-700 mb-2">Card Number</label>
          <input type="text" class="w-full p-2 border rounded">
        </div>

        <div class="flex gap-4">
          <a
            routerLink="/order-progress"
            class="flex-1 text-center px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
          >
            Pay for Order
          </a>
          <a
            routerLink="/reservation-history"
            class="flex-1 text-center px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
          >
            Pay for Reservation
          </a>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class PaymentComponent {}
