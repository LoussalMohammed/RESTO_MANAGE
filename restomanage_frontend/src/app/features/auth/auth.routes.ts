import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './components/auth-layout/auth-layout.component';
import { LoginComponent } from './components/login/login.component';
import { ClientRegisterComponent } from './components/client-register/client-register.component';
import { ManagerRegisterComponent } from './components/manager-register/manager-register.component';

export const AUTH_ROUTES: Routes = [
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'client/register', component: ClientRegisterComponent },
      { path: 'manager/register', component: ManagerRegisterComponent }
    ]
  }
];
