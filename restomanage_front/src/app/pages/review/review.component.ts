// review.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-purple-50">
      <h1 class="text-4xl font-bold text-purple-800 mb-8">Leave a Review ⭐</h1>

      <div class="max-w-2xl mx-auto bg-white p-6 rounded-lg shadow-md">
        <div class="mb-6">
          <label class="block text-gray-700 mb-4">Rating</label>
          <div class="flex gap-2">
            <button
              *ngFor="let star of [1,2,3,4,5]"
              (click)="selectedRating = star"
              class="text-3xl"
              [class.text-yellow-400]="star <= selectedRating"
              [class.text-gray-300]="star > selectedRating"
            >
              ★
            </button>
          </div>
        </div>

        <div class="mb-6">
          <label class="block text-gray-700 mb-4">Comment</label>
          <textarea
            class="w-full p-3 border rounded-lg"
            rows="4"
            placeholder="Tell us about your experience..."
          ></textarea>
        </div>

        <button
          class="w-full px-6 py-3 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
          routerLink="/"
        >
          Submit Review
        </button>
      </div>
    </div>
  `,
  styles: []
})
export class ReviewComponent {
  selectedRating = 0;
}
