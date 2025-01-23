import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  selector: 'app-root',
  template: `
    <div class="container mx-auto p-4">
      HAMZA MESKI app
    </div>
  `,
  styles: []
})
export class AppComponent {}
