import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-home',
  template: `
    <!-- Hero Section -->
    <div class="relative h-screen">
      <div class="absolute inset-0 bg-black/60 z-10"></div>
      <div class="absolute inset-0 bg-[url('https://via.placeholder.com/1920x1080')] bg-cover bg-center"></div>

      <!-- Hero Content -->
      <div class="relative z-20 h-full flex flex-col justify-center items-center text-center px-4">
        <p class="text-amber-400 mb-4">High Quality Fine Dining</p>
        <h1 class="text-5xl md:text-7xl font-serif text-white mb-6">
          We believe Good Food<br/>
          Offer Great Smile
        </h1>
        <p class="text-xl text-gray-200 mb-8 max-w-2xl">Experience the extraordinary in every bite.</p>
        <div class="flex gap-4">
          <a routerLink="/menu" class="px-8 py-3 bg-amber-500 text-white rounded-full hover:bg-amber-600 transition-colors font-medium">
            Order Now
          </a>
          <a routerLink="/menu" class="px-8 py-3 border-2 border-white text-white rounded-full hover:bg-white/10 transition-colors font-medium">
            View Menu
          </a>
        </div>

        <!-- Featured Review -->
        <div class="mt-12 bg-white/10 backdrop-blur-sm p-4 rounded-lg max-w-md">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 bg-gray-200 rounded-full"></div>
            <div class="text-left">
              <p class="text-white">John Doe</p>
              <div class="flex text-amber-400">
                ★★★★★
              </div>
            </div>
            <div class="text-amber-400 font-semibold ml-auto">$15.00</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="bg-gray-50 py-16 px-4">
      <div class="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
        <div class="bg-white p-6 rounded-lg shadow-md text-center">
          <div class="w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="text-2xl">🎁</span>
          </div>
          <h3 class="text-xl font-semibold mb-2">Discount Voucher</h3>
          <p class="text-gray-600">Special offers just for you</p>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-md text-center">
          <div class="w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="text-2xl">🥗</span>
          </div>
          <h3 class="text-xl font-semibold mb-2">Fresh Healthy Food</h3>
          <p class="text-gray-600">Made with premium ingredients</p>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-md text-center">
          <div class="w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <span class="text-2xl">💰</span>
          </div>
          <h3 class="text-xl font-semibold mb-2">Best Prices</h3>
          <p class="text-gray-600">Affordable luxury dining</p>
        </div>
      </div>
    </div>

    <!-- Browse Menu Section -->
    <div class="py-16 px-4">
      <h2 class="text-3xl font-serif text-center mb-12">Browse Our Menu</h2>
      <div class="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div *ngFor="let item of menuItems" class="bg-white rounded-lg p-4 shadow-md flex items-center gap-4">
          <div class="w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center">
            <span class="text-2xl">🍽️</span>
          </div>
          <div class="flex-1">
            <h3 class="font-semibold">{{item.name}}</h3>
            <p class="text-gray-600 text-sm">{{item.description}}</p>
          </div>
          <div class="text-amber-600 font-semibold">12</div>
        </div>
      </div>
    </div>

    <!-- Today's Special -->
    <div class="bg-gray-900 py-16 px-4 text-white">
      <div class="max-w-7xl mx-auto">
        <h2 class="text-3xl font-serif text-center mb-12">Today's Special</h2>
        <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
          <div *ngFor="let special of specialItems" class="bg-white/5 backdrop-blur-sm rounded-lg overflow-hidden">
            <div class="h-48 bg-[url('https://via.placeholder.com/400x300')] bg-cover bg-center"></div>
            <div class="p-4">
              <h3 class="text-xl font-semibold mb-2">{{special.name}}</h3>
              <p class="text-gray-300">{{special.description}}</p>
              <p class="text-amber-400 font-semibold mt-2">15</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer class="bg-gray-900 text-gray-300 py-16 px-4">
      <div class="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-4 gap-12">
        <div>
          <h3 class="text-2xl font-serif text-white mb-4">SWIDO</h3>
          <p class="text-gray-400">Where taste meets excellence</p>
        </div>
        <div>
          <h4 class="font-semibold text-white mb-4">Contact</h4>
          <p>123 Restaurant St.</p>
          <p>Cuisine City, CC 12345</p>
          <p>Tel: (555) 123-4567</p>
        </div>
        <div>
          <h4 class="font-semibold text-white mb-4">Hours</h4>
          <p>Mon-Thu: 11am - 10pm</p>
          <p>Fri-Sat: 11am - 11pm</p>
          <p>Sun: 11am - 9pm</p>
        </div>
        <div>
          <h4 class="font-semibold text-white mb-4">Newsletter</h4>
          <div class="flex gap-2">
            <input type="email" placeholder="Your email" class="px-4 py-2 rounded-lg bg-gray-800 text-white flex-1">
            <button class="px-4 py-2 bg-amber-500 text-white rounded-lg hover:bg-amber-600 transition-colors">
              Subscribe
            </button>
          </div>
        </div>
      </div>
    </footer>
  `,
  styles: []
})
export class HomeComponent {
  menuItems = [
    { name: 'Pasta', price: '24.00', description: 'Fresh homemade pasta' },
    { name: 'Pancake', price: '12.00', description: 'With maple syrup' },
    { name: 'Nasi Rice', price: '18.00', description: 'Traditional recipe' },
    { name: 'Burger', price: '22.00', description: '100% beef patty' }
  ];

  specialItems = [
    { name: 'Pepperoni Pizza', price: '28.00', description: 'Classic Italian recipe' },
    { name: 'Pancake Stack', price: '16.00', description: 'With fresh berries' },
    { name: 'Vegetable Stir-fry', price: '22.00', description: 'Seasonal vegetables' },
    { name: 'Eggs Benedict', price: '18.00', description: 'Free range eggs' }
  ];
}
