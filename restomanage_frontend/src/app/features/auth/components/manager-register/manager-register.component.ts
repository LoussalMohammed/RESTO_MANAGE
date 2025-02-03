import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { managerRegister } from '../../../../store/auth/auth.actions';
import { selectLoading, selectError } from '../../../../store/auth/auth.selectors';

@Component({
  selector: 'app-manager-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  template: `
    <div class="text-center mb-8">
      <h2 class="text-3xl font-bold text-gray-900">Create Manager Account</h2>
      <p class="mt-2 text-gray-600">Register to manage your restaurant</p>
    </div>

    <div *ngIf="error$ | async" class="mb-4 p-4 bg-red-100 text-red-700 rounded">
      {{ error$ | async }}
    </div>

    <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="space-y-6">
      <div class="grid grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-gray-700">First Name</label>
          <input
            type="text"
            formControlName="firstName"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            [class.border-red-500]="isFieldInvalid('firstName')"
          />
          <p *ngIf="isFieldInvalid('firstName')" class="mt-1 text-sm text-red-600">
            First name is required
          </p>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">Last Name</label>
          <input
            type="text"
            formControlName="lastName"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            [class.border-red-500]="isFieldInvalid('lastName')"
          />
          <p *ngIf="isFieldInvalid('lastName')" class="mt-1 text-sm text-red-600">
            Last name is required
          </p>
        </div>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Username</label>
        <input
          type="text"
          formControlName="username"
          class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          [class.border-red-500]="isFieldInvalid('username')"
        />
        <p *ngIf="isFieldInvalid('username')" class="mt-1 text-sm text-red-600">
          Username is required
        </p>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Email</label>
        <input
          type="email"
          formControlName="email"
          class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          [class.border-red-500]="isFieldInvalid('email')"
        />
        <p *ngIf="isFieldInvalid('email')" class="mt-1 text-sm text-red-600">
          Please enter a valid email address
        </p>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Phone Number</label>
        <input
          type="tel"
          formControlName="phoneNumber"
          class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          [class.border-red-500]="isFieldInvalid('phoneNumber')"
        />
        <p *ngIf="isFieldInvalid('phoneNumber')" class="mt-1 text-sm text-red-600">
          Please enter a valid phone number
        </p>
      </div>

      <div>
        <label class="block text-sm font-medium text-gray-700">Password</label>
        <input
          type="password"
          formControlName="password"
          class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          [class.border-red-500]="isFieldInvalid('password')"
        />
        <p *ngIf="isFieldInvalid('password')" class="mt-1 text-sm text-red-600">
          Password must be at least 8 characters long and contain at least one digit,
          one lowercase letter, one uppercase letter, and one special character
        </p>
      </div>

      <div>
        <button
          type="submit"
          [disabled]="registerForm.invalid || (loading$ | async)"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
        >
          <span *ngIf="loading$ | async">Registering...</span>
          <span *ngIf="!(loading$ | async)">Register</span>
        </button>
      </div>

      <div class="text-center mt-4">
        <a routerLink="/auth/login" class="text-sm text-indigo-600 hover:text-indigo-500">
          Already have an account? Sign in
        </a>
      </div>
    </form>
  `
})
export class ManagerRegisterComponent implements OnInit {
  private store = inject(Store);
  private fb = inject(FormBuilder);
  
  registerForm!: FormGroup;
  loading$ = this.store.select(selectLoading);
  error$ = this.store.select(selectError);

  ngOnInit() {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^\\+?[0-9]{8,15}$')]],
      password: ['', [
        Validators.required,
        Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$')
      ]]
    });
  }

  isFieldInvalid(field: string): boolean {
    const formControl = this.registerForm.get(field);
    return formControl ? formControl.invalid && formControl.touched : false;
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.store.dispatch(managerRegister({ data: this.registerForm.value }));
    } else {
      Object.keys(this.registerForm.controls).forEach(key => {
        const control = this.registerForm.get(key);
        if (control) {
          control.markAsTouched();
        }
      });
    }
  }
}
