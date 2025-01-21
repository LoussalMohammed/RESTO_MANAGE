// order-progress.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-blue-50">
      <h1 class="text-4xl font-bold text-blue-800 mb-6">
        Order Progress 🚚
      </h1>
      <p class="text-lg mb-8">Your order is being prepared!</p>

      <div class="space-y-4">
        <div class="p-4 bg-white rounded-lg shadow-md">
          <h3 class="font-semibold text-blue-600">Estimated Time:</h3>
          <p>20-30 minutes</p>
        </div>

        <a
          routerLink="/review"
          class="inline-block px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
        >
          Leave a Review
        </a>
      </div>
    </div>
  `,
  styles: []
})
export class OrderProgressComponent {}
