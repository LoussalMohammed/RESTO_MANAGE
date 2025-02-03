import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, catchError, exhaustMap, tap } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';
import * as AuthActions from './auth.actions';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  private actions$ = inject(Actions);
  private authService = inject(AuthService);
  private router = inject(Router);

  clientLogin$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.clientLogin),
      exhaustMap(({ credentials }) =>
        this.authService.clientLogin(credentials).pipe(
          map(response => {
            this.authService.setAuthToken(response.token);
            this.authService.setUserInfo(response);
            return AuthActions.clientLoginSuccess({ response });
          }),
          catchError(error => 
            of(AuthActions.clientLoginFailure({ error: error.error.message || 'Login failed' }))
          )
        )
      )
    )
  );

  managerLogin$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.managerLogin),
      exhaustMap(({ credentials }) =>
        this.authService.managerLogin(credentials).pipe(
          map(response => {
            this.authService.setAuthToken(response.token);
            this.authService.setUserInfo(response);
            return AuthActions.managerLoginSuccess({ response });
          }),
          catchError(error => 
            of(AuthActions.managerLoginFailure({ error: error.error.message || 'Login failed' }))
          )
        )
      )
    )
  );

  clientRegister$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.clientRegister),
      exhaustMap(({ data }) =>
        this.authService.clientRegister(data).pipe(
          map(response => {
            this.authService.setAuthToken(response.token);
            this.authService.setUserInfo(response);
            return AuthActions.clientRegisterSuccess({ response });
          }),
          catchError(error => 
            of(AuthActions.clientRegisterFailure({ error: error.error.message || 'Registration failed' }))
          )
        )
      )
    )
  );

  managerRegister$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.managerRegister),
      exhaustMap(({ data }) =>
        this.authService.managerRegister(data).pipe(
          map(response => {
            this.authService.setAuthToken(response.token);
            this.authService.setUserInfo(response);
            return AuthActions.managerRegisterSuccess({ response });
          }),
          catchError(error => 
            of(AuthActions.managerRegisterFailure({ error: error.error.message || 'Registration failed' }))
          )
        )
      )
    )
  );

  logout$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.logout),
      exhaustMap(() =>
        this.authService.logout().pipe(
          map(() => {
            this.authService.clearAuth();
            return AuthActions.logoutSuccess();
          }),
          catchError(error => {
            this.authService.clearAuth();
            return of(AuthActions.logoutFailure({ error: error.error.message || 'Logout failed' }));
          })
        )
      )
    )
  );

  // Navigation effects
  authSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(
        AuthActions.clientLoginSuccess,
        AuthActions.managerLoginSuccess,
        AuthActions.clientRegisterSuccess,
        AuthActions.managerRegisterSuccess
      ),
      tap(({ response }) => {
        if (response.role === 'CLIENT') {
          this.router.navigate(['/client/meals']);
        } else {
          this.router.navigate(['/manager/dashboard']);
        }
      })
    ),
    { dispatch: false }
  );

  logoutSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.logoutSuccess),
      tap(() => {
        this.router.navigate(['/auth/login']);
      })
    ),
    { dispatch: false }
  );
}
