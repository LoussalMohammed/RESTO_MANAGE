import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { Role } from './core/models/auth.model';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/auth/login',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES)
  },
  {
    path: 'client',
    canActivate: [() => authGuard([Role.CLIENT])],
    loadChildren: () => import('./features/client/client.routes').then(m => m.CLIENT_ROUTES)
  },
  {
    path: 'manager',
    canActivate: [() => authGuard([Role.MANAGER])],
    loadChildren: () => import('./features/manager/manager.routes').then(m => m.MANAGER_ROUTES)
  },
  {
    path: '**',
    redirectTo: '/auth/login'
  }
];
