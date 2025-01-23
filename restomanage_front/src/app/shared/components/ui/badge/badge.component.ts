import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

type BadgeVariant = 'success' | 'warning' | 'error' | 'info' | 'default';
type BadgeSize = 'sm' | 'md' | 'lg';

@Component({
  selector: 'app-badge',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span
      class="inline-flex items-center rounded-full font-medium"
      [ngClass]="[
        variantClasses[variant],
        sizeClasses[size],
        pill ? 'rounded-full' : 'rounded'
      ]">
      <span
        *ngIf="dot"
        class="mr-1.5 h-2 w-2 rounded-full"
        [ngClass]="dotClasses[variant]">
      </span>
      <span class="material-icons text-sm mr-1" *ngIf="icon">{{icon}}</span>
      <ng-content></ng-content>
    </span>
  `
})
export class BadgeComponent {
  @Input() variant: BadgeVariant = 'default';
  @Input() size: BadgeSize = 'md';
  @Input() pill = true;
  @Input() dot = false;
  @Input() icon?: string;

  variantClasses: Record<BadgeVariant, string> = {
    success: 'bg-green-100 text-green-800',
    warning: 'bg-yellow-100 text-yellow-800',
    error: 'bg-red-100 text-red-800',
    info: 'bg-blue-100 text-blue-800',
    default: 'bg-gray-100 text-gray-800'
  };

  dotClasses: Record<BadgeVariant, string> = {
    success: 'bg-green-400',
    warning: 'bg-yellow-400',
    error: 'bg-red-400',
    info: 'bg-blue-400',
    default: 'bg-gray-400'
  };

  sizeClasses: Record<BadgeSize, string> = {
    sm: 'px-2 py-0.5 text-xs',
    md: 'px-2.5 py-0.5 text-sm',
    lg: 'px-3 py-1 text-base'
  };
}
