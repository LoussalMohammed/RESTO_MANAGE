// reservation.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-green-50">
      <h1 class="text-4xl font-bold text-green-800 mb-8">
        Make a Reservation 🕒
      </h1>

      <div class="max-w-md mx-auto bg-white p-6 rounded-lg shadow-md">
        <div class="mb-6">
          <label class="block text-gray-700 mb-2">Select Date/Time</label>
          <input type="datetime-local" class="w-full p-2 border rounded">
        </div>

        <a
          routerLink="/payment"
          class="w-full text-center px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
        >
          Confirm Reservation
        </a>
      </div>
    </div>
  `,
  styles: []
})
export class ReservationComponent {}
