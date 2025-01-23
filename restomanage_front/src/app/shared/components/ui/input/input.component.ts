import { Component, Input, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="w-full">
      <label *ngIf="label" [for]="id" class="block text-sm font-medium text-gray-700 mb-1">
        {{label}}
        <span *ngIf="required" class="text-red-500">*</span>
      </label>
      
      <div class="relative">
        <span *ngIf="prefix" class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
          <span class="text-gray-500 sm:text-sm">{{prefix}}</span>
        </span>
        
        <input
          [type]="type"
          [id]="id"
          [name]="name"
          [placeholder]="placeholder"
          [required]="required"
          [disabled]="disabled"
          [value]="value"
          (input)="onInput($event)"
          (blur)="onBlur()"
          [ngClass]="[
            'block w-full rounded-md border-gray-300 shadow-sm',
            'focus:border-primary-500 focus:ring-primary-500 sm:text-sm',
            error ? 'border-red-300' : '',
            disabled ? 'bg-gray-100 cursor-not-allowed' : '',
            prefix ? 'pl-7' : '',
            suffix ? 'pr-7' : ''
          ]"
        />
        
        <span *ngIf="suffix" class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
          <span class="text-gray-500 sm:text-sm">{{suffix}}</span>
        </span>
      </div>
      
      <p *ngIf="error" class="mt-1 text-sm text-red-600">{{error}}</p>
      <p *ngIf="hint && !error" class="mt-1 text-sm text-gray-500">{{hint}}</p>
    </div>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputComponent),
      multi: true
    }
  ]
})
export class InputComponent implements ControlValueAccessor {
  @Input() label?: string;
  @Input() type = 'text';
  @Input() id = '';
  @Input() name = '';
  @Input() placeholder = '';
  @Input() required = false;
  @Input() disabled = false;
  @Input() error?: string;
  @Input() hint?: string;
  @Input() prefix?: string;
  @Input() suffix?: string;

  value: any = '';
  touched = false;
  onChange: any = () => {};
  onTouch: any = () => {};

  onInput(event: any) {
    this.value = event.target.value;
    this.onChange(this.value);
  }

  onBlur() {
    this.touched = true;
    this.onTouch();
  }

  writeValue(value: any) {
    this.value = value;
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouch = fn;
  }

  setDisabledState(isDisabled: boolean) {
    this.disabled = isDisabled;
  }
}
