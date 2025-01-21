import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule, RouterModule],
  selector: 'app-navbar',
  template: `
    <nav class="bg-gradient-to-r from-amber-800 to-amber-900 shadow-lg fixed w-full top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
          <!-- Logo/Brand -->
          <div class="flex-shrink-0 flex items-center">
            <span class="text-2xl font-serif text-amber-100">La Maison</span>
          </div>

          <!-- Navigation Links -->
          <div class="hidden md:block">
            <div class="flex items-center space-x-4">
              <a
                *ngFor="let link of links"
                [routerLink]="link.path"
                routerLinkActive="bg-amber-700 text-white"
                [routerLinkActiveOptions]="{ exact: true }"
                class="text-amber-100 hover:bg-amber-700 hover:text-white px-4 py-2 rounded-md text-sm font-medium transition-colors duration-200"
              >
                {{ link.label }}
              </a>
            </div>
          </div>

          <!-- Right Side Actions -->
          <div class="hidden md:flex items-center space-x-4">
            <!-- Cart Button -->
            <a
              routerLink="/checkout"
              class="flex items-center gap-2 bg-amber-600 text-white px-4 py-2 rounded-md hover:bg-amber-500 transition-colors duration-200"
            >
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z"/>
              </svg>
              Cart
            </a>
          </div>

          <!-- Mobile menu button -->
          <div class="md:hidden flex items-center">
            <button
              (click)="isOpen = !isOpen"
              class="inline-flex items-center justify-center p-2 rounded-md text-amber-100 hover:text-white hover:bg-amber-700 focus:outline-none"
            >
              <svg
                class="h-6 w-6"
                [class.hidden]="isOpen"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
              </svg>
              <svg
                class="h-6 w-6"
                [class.hidden]="!isOpen"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Mobile menu -->
      <div class="md:hidden" [class.hidden]="!isOpen">
        <div class="px-2 pt-2 pb-3 space-y-1">
          <a
            *ngFor="let link of links"
            [routerLink]="link.path"
            routerLinkActive="bg-amber-700 text-white"
            [routerLinkActiveOptions]="{ exact: true }"
            class="text-amber-100 hover:bg-amber-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            (click)="isOpen = false"
          >
            {{ link.label }}
          </a>
          <a
            routerLink="/checkout"
            class="flex items-center gap-2 text-amber-100 hover:bg-amber-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            (click)="isOpen = false"
          >
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
              <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z"/>
            </svg>
            Cart
          </a>
        </div>
      </div>
    </nav>
    <!-- Spacer to prevent content from going under fixed navbar -->
    <div class="h-16"></div>
  `,
  styles: []
})
export class NavbarComponent {
  isOpen = false;

  links = [
    { path: '/', label: 'Home' },
    { path: '/menu', label: 'Menu' },
    { path: '/reservation', label: 'Reservation' },
    { path: '/reservation-history', label: 'History' }
  ];
}
