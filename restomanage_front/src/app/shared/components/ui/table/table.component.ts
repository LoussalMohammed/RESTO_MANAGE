import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';

export interface Column {
  key: string;
  label: string;
  sortable?: boolean;
  format?: (value: any) => string;
}

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  template: `
    <div class="overflow-x-auto">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th *ngFor="let col of columns" 
                scope="col" 
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                [ngClass]="{'cursor-pointer': col.sortable}"
                (click)="col.sortable && onSort.emit(col.key)">
              {{col.label}}
              <span *ngIf="col.sortable" class="ml-1">
                <span class="material-icons text-xs">unfold_more</span>
              </span>
            </th>
            <th *ngIf="actions" scope="col" class="relative px-6 py-3">
              <span class="sr-only">Actions</span>
            </th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr *ngFor="let item of data">
            <td *ngFor="let col of columns" class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
              {{col.format ? col.format(item[col.key]) : item[col.key]}}
            </td>
            <td *ngIf="actions" class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium space-x-2">
              <ng-container *ngTemplateOutlet="actions; context: { $implicit: item }">
              </ng-container>
            </td>
          </tr>
          <tr *ngIf="!data?.length" class="hover:bg-gray-50">
            <td [attr.colspan]="columns.length + (actions ? 1 : 0)" class="px-6 py-4 text-center text-sm text-gray-500">
              {{emptyMessage}}
            </td>
          </tr>
        </tbody>
      </table>
      
      <div *ngIf="showPagination" class="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
        <div class="flex-1 flex justify-between sm:hidden">
          <app-button 
            variant="secondary" 
            [disabled]="currentPage === 1"
            (onClick)="onPageChange.emit(currentPage - 1)">
            Previous
          </app-button>
          <app-button 
            variant="secondary" 
            [disabled]="currentPage === totalPages"
            (onClick)="onPageChange.emit(currentPage + 1)">
            Next
          </app-button>
        </div>
        <div class="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
          <div>
            <p class="text-sm text-gray-700">
              Showing
              <span class="font-medium">{{(currentPage - 1) * pageSize + 1}}</span>
              to
              <span class="font-medium">{{Math.min(currentPage * pageSize, totalItems)}}</span>
              of
              <span class="font-medium">{{totalItems}}</span>
              results
            </p>
          </div>
          <div>
            <nav class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px">
              <app-button 
                variant="ghost"
                [disabled]="currentPage === 1"
                (onClick)="onPageChange.emit(currentPage - 1)">
                Previous
              </app-button>
              <app-button 
                variant="ghost"
                [disabled]="currentPage === totalPages"
                (onClick)="onPageChange.emit(currentPage + 1)">
                Next
              </app-button>
            </nav>
          </div>
        </div>
      </div>
    </div>
  `
})
export class TableComponent {
  @Input() columns: Column[] = [];
  @Input() data: any[] = [];
  @Input() actions?: any;
  @Input() emptyMessage = 'No data available';
  
  // Pagination
  @Input() showPagination = false;
  @Input() currentPage = 1;
  @Input() pageSize = 10;
  @Input() totalItems = 0;
  @Input() totalPages = 1;

  @Output() onSort = new EventEmitter<string>();
  @Output() onPageChange = new EventEmitter<number>();

  Math = Math;
}
