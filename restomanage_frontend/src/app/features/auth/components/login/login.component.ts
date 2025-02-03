import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { clientLogin, managerLogin } from '../../../../store/auth/auth.actions';
import { selectLoading, selectError } from '../../../../store/auth/auth.selectors';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  template: `
    <div class="text-center mb-8">
      <h2 class="text-3xl font-bold text-gray-900">Welcome Back</h2>
      <p class="mt-2 text-gray-600">Please sign in to your account</p>
    </div>

    <div *ngIf="error$ | async" class="mb-4 p-4 bg-red-100 text-red-700 rounded">
      {{ error$ | async }}
    </div>

    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="space-y-6">
      <div>
        <label class="block text-sm font-medium text-gray-700">Username/Email</label>
        <input
          type="text"
          formControlName="username"
          class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          [class.border-red-500]="isFieldInvalid('username')"
        />
        <p *ngIf="isFieldInvalid('username')" class="mt-1 text-sm text-red-600">
          Username/Email is required
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
          Password is required
        </p>
      </div>

      <div>
        <label class="inline-flex items-center">
          <input
            type="radio"
            formControlName="userType"
            value="client"
            class="form-radio text-indigo-600"
          />
          <span class="ml-2">Client</span>
        </label>
        <label class="inline-flex items-center ml-6">
          <input
            type="radio"
            formControlName="userType"
            value="manager"
            class="form-radio text-indigo-600"
          />
          <span class="ml-2">Manager</span>
        </label>
      </div>

      <div>
        <button
          type="submit"
          [disabled]="loginForm.invalid || (loading$ | async)"
          class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
        >
          <span *ngIf="loading$ | async">Loading...</span>
          <span *ngIf="!(loading$ | async)">Sign In</span>
        </button>
      </div>

      <div class="flex items-center justify-between mt-4">
        <a routerLink="/auth/client/register" class="text-sm text-indigo-600 hover:text-indigo-500">
          Register as Client
        </a>
        <a routerLink="/auth/manager/register" class="text-sm text-indigo-600 hover:text-indigo-500">
          Register as Manager
        </a>
      </div>
    </form>
  `
})
export class LoginComponent implements OnInit {
  private store = inject(Store);
  private fb = inject(FormBuilder);
  
  loginForm!: FormGroup;
  loading$ = this.store.select(selectLoading);
  error$ = this.store.select(selectError);

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      userType: ['client', Validators.required]
    });
  }

  isFieldInvalid(field: string): boolean {
    const formControl = this.loginForm.get(field);
    return formControl ? formControl.invalid && formControl.touched : false;
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password, userType } = this.loginForm.value;
      const credentials = { username, password };

      if (userType === 'client') {
        this.store.dispatch(clientLogin({ credentials }));
      } else {
        this.store.dispatch(managerLogin({ credentials }));
      }
    } else {
      Object.keys(this.loginForm.controls).forEach(key => {
        const control = this.loginForm.get(key);
        if (control) {
          control.markAsTouched();
        }
      });
    }
  }
}
