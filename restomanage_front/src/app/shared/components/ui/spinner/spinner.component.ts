import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-spinner',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="flex items-center justify-center" [ngClass]="containerClass">
      <div
        class="animate-spin rounded-full border-t-2 border-b-2"
        [ngClass]="[
          sizeClasses[size],
          colorClasses[color]
        ]">
      </div>
      <span *ngIf="text" class="ml-2 text-sm text-gray-500">{{text}}</span>
    </div>
  `
})
export class SpinnerComponent {
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() color: 'primary' | 'secondary' | 'white' = 'primary';
  @Input() text?: string;
  @Input() containerClass = '';

  sizeClasses = {
    sm: 'h-4 w-4 border-2',
    md: 'h-8 w-8 border-2',
    lg: 'h-12 w-12 border-3'
  };

  colorClasses = {
    primary: 'border-primary-600',
    secondary: 'border-gray-600',
    white: 'border-white'
  };
}
