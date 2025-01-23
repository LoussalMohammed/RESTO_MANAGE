import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { animate, style, transition, trigger } from '@angular/animations';

type AlertType = 'success' | 'error' | 'warning' | 'info';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div
      *ngIf="show"
      [@slideIn]
      class="fixed top-4 right-4 w-96 shadow-lg rounded-lg overflow-hidden"
      [ngClass]="typeClasses[type]">
      <div class="p-4">
        <div class="flex items-start">
          <div class="flex-shrink-0">
            <span class="material-icons">{{typeIcons[type]}}</span>
          </div>
          <div class="ml-3 w-0 flex-1 pt-0.5">
            <p class="text-sm font-medium">{{title}}</p>
            <p class="mt-1 text-sm opacity-90">{{message}}</p>
          </div>
          <div class="ml-4 flex-shrink-0 flex">
            <button
              type="button"
              class="rounded-md inline-flex text-sm focus:outline-none focus:ring-2 focus:ring-offset-2"
              [ngClass]="typeClasses[type]"
              (click)="onClose.emit()">
              <span class="material-icons">close</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  animations: [
    trigger('slideIn', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('150ms ease-out', style({ transform: 'translateX(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('100ms ease-in', style({ transform: 'translateX(100%)', opacity: 0 }))
      ])
    ])
  ]
})
export class AlertComponent {
  @Input() show = false;
  @Input() type: AlertType = 'info';
  @Input() title = '';
  @Input() message = '';
  @Output() onClose = new EventEmitter<void>();

  typeClasses: Record<AlertType, string> = {
    success: 'bg-green-50 text-green-800',
    error: 'bg-red-50 text-red-800',
    warning: 'bg-yellow-50 text-yellow-800',
    info: 'bg-blue-50 text-blue-800'
  };

  typeIcons: Record<AlertType, string> = {
    success: 'check_circle',
    error: 'error',
    warning: 'warning',
    info: 'info'
  };
}
