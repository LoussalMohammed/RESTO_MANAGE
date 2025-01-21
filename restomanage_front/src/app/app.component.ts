import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './shared/navbar/navbar.component';

@Component({
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent],
  selector: 'app-root',
  template: `
    <app-navbar></app-navbar>
    <div class="container mx-auto p-4">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: []
})
export class AppComponent {}
