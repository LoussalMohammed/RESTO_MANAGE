import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="bg-white rounded-lg shadow-card hover:shadow-card-hover transition-shadow duration-300">
      <div *ngIf="title || subtitle" class="p-4 border-b border-gray-200">
        <h3 class="text-lg font-semibold text-gray-900">{{title}}</h3>
        <p *ngIf="subtitle" class="mt-1 text-sm text-gray-500">{{subtitle}}</p>
      </div>
      <div [ngClass]="contentClass">
        <ng-content></ng-content>
      </div>
      <div *ngIf="footer" class="p-4 border-t border-gray-200">
        <ng-content select="[footer]"></ng-content>
      </div>
    </div>
  `
})
export class CardComponent {
  @Input() title?: string;
  @Input() subtitle?: string;
  @Input() footer = false;
  @Input() contentClass = 'p-4';
}
