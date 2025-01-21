// checkout.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-checkout',
  template: `
    <div class="min-h-screen p-8 bg-gray-100">
      <h1 class="text-4xl font-bold text-blue-600 mb-4">
        Hello, this is the Checkout Page
      </h1>

      <!-- Add your checkout form/components here -->

      <div class="mt-8">
        <a
          routerLink="/payment"
          class="px-6 py-3 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
        >
          Proceed to Payment
        </a>
      </div>
    </div>
  `,
  styles: []
})
export class CheckoutComponent {}
