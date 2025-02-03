import { Routes } from '@angular/router';

export const CLIENT_ROUTES: Routes = [
  {
    path: '',
    redirectTo: 'meals',
    pathMatch: 'full'
  }
  // Other client routes will be added here
];
