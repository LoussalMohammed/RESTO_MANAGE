import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { map, take } from 'rxjs/operators';
import { selectIsAuthenticated, selectUserRole } from '../../store/auth/auth.selectors';

export const authGuard = (allowedRoles?: string[]) => {
  const router = inject(Router);
  const store = inject(Store);

  return store.select(selectIsAuthenticated).pipe(
    take(1),
    map(isAuthenticated => {
      if (!isAuthenticated) {
        router.navigate(['/auth/login']);
        return false;
      }

      if (!allowedRoles) {
        return true;
      }

      return store.select(selectUserRole).pipe(
        take(1),
        map(role => {
          if (role && allowedRoles.includes(role)) {
            return true;
          }
          
          router.navigate(['/auth/login']);
          return false;
        })
      );
    })
  );
};
