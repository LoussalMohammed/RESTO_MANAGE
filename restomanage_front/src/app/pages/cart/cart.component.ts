// cart.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface CartItem {
  name: string;
  price: string;
  quantity: number;
  image: string;
}

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="min-h-screen p-8 bg-[#fffbeb]">
      <h1 class="text-4xl font-bold text-[#7b360e] mb-8">Your Cart 🛒</h1>
      <div class="max-w-4xl mx-auto">
        <!-- Cart Items -->
        <div class="bg-white rounded-lg shadow-md p-6 mb-6">
          <div *ngIf="cartItems.length > 0; else emptyCart">
            <div *ngFor="let item of cartItems" class="flex items-center justify-between py-4 border-b">
              <div class="flex items-center gap-4">
                <img [src]="item.image" class="w-20 h-20 object-cover rounded-lg" alt="Item image">
                <div>
                  <h3 class="font-semibold text-lg text-[#7b360e]">{{ item.name }}</h3>
                  <p class="text-gray-600">{{ item.price }}</p>
                </div>
              </div>
              <div class="flex items-center gap-4">
                <div class="flex items-center gap-2">
                  <button
                    (click)="updateQuantity(item, -1)"
                    class="px-3 py-1 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors"
                  >
                    -
                  </button>
                  <span class="w-8 text-center text-[#7b360e]">{{ item.quantity }}</span>
                  <button
                    (click)="updateQuantity(item, 1)"
                    class="px-3 py-1 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors"
                  >
                    +
                  </button>
                </div>
                <button
                  (click)="removeItem(item)"
                  class="text-red-500 hover:text-red-700"
                >
                  Remove
                </button>
              </div>
            </div>
            <!-- Total -->
            <div class="mt-6 text-xl font-bold text-right text-[#7b360e]">
              Total: 12
            </div>
          </div>
          <ng-template #emptyCart>
            <div class="text-center py-12 text-gray-500">
              Your cart is empty. Start shopping!
            </div>
          </ng-template>
        </div>
        <!-- Actions -->
        <div class="flex justify-between">
          <a
            routerLink="/menu"
            class="px-6 py-3 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors"
          >
            Continue Shopping
          </a>
          <a
            routerLink="/checkout"
            class="px-6 py-3 bg-[#7b360e] text-white rounded-lg hover:bg-[#5a280a] transition-colors"
            [class.opacity-50]="cartItems.length === 0"
            [class.pointer-events-none]="cartItems.length === 0"
          >
            Checkout
          </a>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class CartComponent {
  cartItems: CartItem[] = [
    {
      name: 'Margherita Pizza',
      price: '$12.99',
      quantity: 2,
      image: 'https://via.placeholder.com/100'
    },
    {
      name: 'Caesar Salad',
      price: '$8.99',
      quantity: 1,
      image: 'https://via.placeholder.com/100'
    }
  ];

  updateQuantity(item: CartItem, change: number): void {
    item.quantity = Math.max(1, item.quantity + change);
  }

  removeItem(item: CartItem): void {
    const index = this.cartItems.indexOf(item);
    if (index > -1) {
      this.cartItems.splice(index, 1);
    }
  }

  calculateTotal(): string {
    return this.cartItems.reduce((acc, item) =>
      acc + (parseFloat(item.price.replace('$', '')) * item.quantity), 0
    ).toFixed(2);
  }
}
