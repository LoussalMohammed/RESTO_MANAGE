// menu.component.ts
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-[#fffbeb]">
      <!-- Header -->
      <header class="text-center mb-12">
        <h1 class="text-5xl font-bold text-[#7b360e] mb-4">Our Menu</h1>
        <p class="text-lg text-[#7b360e]/80">Discover our delicious offerings</p>
      </header>

      <!-- Menu Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <!-- Menu Item 1 -->
        <div class="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
          <img src="https://via.placeholder.com/400x250" alt="Margherita Pizza" class="w-full h-48 object-cover">
          <div class="p-6">
            <h3 class="text-2xl font-semibold text-[#7b360e] mb-2">Margherita Pizza</h3>
            <p class="text-gray-600 mb-4">Classic pizza with fresh mozzarella, tomatoes, and basil.</p>
            <div class="flex justify-between items-center">
              <span class="text-xl font-bold text-[#7b360e]">$12.99</span>
              <button class="px-6 py-2 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors">
                Add to Cart
              </button>
            </div>
          </div>
        </div>

        <!-- Menu Item 2 -->
        <div class="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
          <img src="https://via.placeholder.com/400x250" alt="Caesar Salad" class="w-full h-48 object-cover">
          <div class="p-6">
            <h3 class="text-2xl font-semibold text-[#7b360e] mb-2">Caesar Salad</h3>
            <p class="text-gray-600 mb-4">Crisp romaine lettuce, croutons, and parmesan cheese.</p>
            <div class="flex justify-between items-center">
              <span class="text-xl font-bold text-[#7b360e]">$8.99</span>
              <button class="px-6 py-2 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors">
                Add to Cart
              </button>
            </div>
          </div>
        </div>

        <!-- Menu Item 3 -->
        <div class="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow">
          <img src="https://via.placeholder.com/400x250" alt="Pasta Carbonara" class="w-full h-48 object-cover">
          <div class="p-6">
            <h3 class="text-2xl font-semibold text-[#7b360e] mb-2">Pasta Carbonara</h3>
            <p class="text-gray-600 mb-4">Creamy pasta with pancetta, eggs, and parmesan.</p>
            <div class="flex justify-between items-center">
              <span class="text-xl font-bold text-[#7b360e]">$14.99</span>
              <button class="px-6 py-2 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors">
                Add to Cart
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- View Cart Button -->
      <div class="mt-12 text-center">
        <a
          routerLink="/cart"
          class="inline-block px-8 py-3 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors text-lg"
        >
          View Cart
        </a>
      </div>
    </div>
  `,
  styles: []
})
export class MenuComponent {}
