import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  template: `
    <div
      *ngIf="show"
      class="fixed inset-0 z-50 overflow-y-auto"
      [@fadeIn]="show"
      (click)="onBackdropClick($event)">
      
      <div class="flex min-h-screen items-center justify-center p-4">
        <!-- Backdrop -->
        <div class="fixed inset-0 bg-black bg-opacity-25 transition-opacity"></div>

        <!-- Modal panel -->
        <div
          class="relative transform overflow-hidden rounded-lg bg-white shadow-xl transition-all sm:w-full"
          [ngClass]="sizeClass[size]">
          
          <!-- Header -->
          <div class="border-b border-gray-200 px-4 py-3 sm:px-6">
            <div class="flex items-center justify-between">
              <h3 class="text-lg font-medium text-gray-900">{{title}}</h3>
              <button
                type="button"
                class="rounded-md text-gray-400 hover:text-gray-500 focus:outline-none"
                (click)="onClose.emit()">
                <span class="material-icons">close</span>
              </button>
            </div>
          </div>

          <!-- Content -->
          <div class="px-4 py-5 sm:px-6">
            <ng-content></ng-content>
          </div>

          <!-- Footer -->
          <div *ngIf="showFooter" class="border-t border-gray-200 px-4 py-3 sm:px-6 bg-gray-50">
            <div class="flex justify-end space-x-3">
              <app-button
                *ngIf="showCancelButton"
                variant="ghost"
                (onClick)="onCancel.emit()">
                {{cancelText}}
              </app-button>
              <app-button
                *ngIf="showConfirmButton"
                [variant]="confirmButtonVariant"
                [loading]="loading"
                (onClick)="onConfirm.emit()">
                {{confirmText}}
              </app-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('150ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('150ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class ModalComponent {
  @Input() show = false;
  @Input() title = '';
  @Input() size: 'sm' | 'md' | 'lg' | 'xl' = 'md';
  @Input() showFooter = true;
  @Input() showCancelButton = true;
  @Input() showConfirmButton = true;
  @Input() cancelText = 'Cancel';
  @Input() confirmText = 'Confirm';
  @Input() confirmButtonVariant: 'primary' | 'danger' = 'primary';
  @Input() loading = false;
  @Input() closeOnBackdropClick = true;

  @Output() onClose = new EventEmitter<void>();
  @Output() onCancel = new EventEmitter<void>();
  @Output() onConfirm = new EventEmitter<void>();

  sizeClass = {
    sm: 'sm:max-w-md',
    md: 'sm:max-w-lg',
    lg: 'sm:max-w-xl',
    xl: 'sm:max-w-2xl'
  };

  onBackdropClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (this.closeOnBackdropClick && target.classList.contains('fixed')) {
      this.onClose.emit();
    }
  }
}
