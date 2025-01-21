// reservation-history.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-purple-50">
      <h1 class="text-4xl font-bold text-purple-800 mb-8">
        Reservation History 📆
      </h1>

      <div class="max-w-2xl mx-auto space-y-4">
        <div
          *ngFor="let reservation of [1,2,3]"
          class="bg-white p-4 rounded-lg shadow-md"
        >
          <div class="flex justify-between items-center">
            <div>
              <h3 class="font-semibold">Reservation #{{ reservation }}</h3>
              <p class="text-gray-600">2024-03-20 19:00</p>
            </div>
            <span class="bg-green-100 text-green-800 px-3 py-1 rounded-full">
              Completed
            </span>
          </div>
        </div>

        <a
          routerLink="/reservation"
          class="inline-block mt-6 px-6 py-3 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
        >
          New Reservation
        </a>
      </div>
    </div>
  `,
  styles: []
})
export class ReservationHistoryComponent {}
