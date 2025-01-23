import { Component, Input, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

export interface SelectOption {
  label: string;
  value: any;
  disabled?: boolean;
}

@Component({
  selector: 'app-select',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="w-full">
      <label *ngIf="label" [for]="id" class="block text-sm font-medium text-gray-700 mb-1">
        {{label}}
        <span *ngIf="required" class="text-red-500">*</span>
      </label>

      <div class="relative">
        <select
          [id]="id"
          [name]="name"
          [required]="required"
          [disabled]="disabled"
          [value]="value"
          (change)="onChange($event)"
          (blur)="onBlur()"
          class="block w-full rounded-md border-gray-300 focus:border-primary-500 focus:ring-primary-500 sm:text-sm"
          [ngClass]="{'border-red-300': error, 'bg-gray-100': disabled}">
          
          <option value="" *ngIf="placeholder">{{placeholder}}</option>
          
          <option
            *ngFor="let option of options"
            [value]="option.value"
            [disabled]="option.disabled">
            {{option.label}}
          </option>
        </select>

        <span class="absolute right-0 top-0 bottom-0 flex items-center pr-2 pointer-events-none">
          <span class="material-icons text-gray-400">expand_more</span>
        </span>
      </div>

      <p *ngIf="error" class="mt-1 text-sm text-red-600">{{error}}</p>
      <p *ngIf="hint && !error" class="mt-1 text-sm text-gray-500">{{hint}}</p>
    </div>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectComponent),
      multi: true
    }
  ]
})
export class SelectComponent implements ControlValueAccessor {
  @Input() label?: string;
  @Input() id = '';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() required = false;
  @Input() disabled = false;
  @Input() error?: string;
  @Input() hint?: string;
  @Input() options: SelectOption[] = [];

  value: any = '';
  onChange: any = () => {};
  onTouch: any = () => {};

  writeValue(value: any): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onBlur(): void {
    this.onTouch();
  }
}
