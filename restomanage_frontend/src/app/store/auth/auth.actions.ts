import { createAction, props } from '@ngrx/store';
import { LoginRequest, RegisterRequest, ManagerRegisterRequest, AuthResponse } from '../../core/models/auth.model';

// Client Login Actions
export const clientLogin = createAction(
  '[Auth] Client Login',
  props<{ credentials: LoginRequest }>()
);

export const clientLoginSuccess = createAction(
  '[Auth] Client Login Success',
  props<{ response: AuthResponse }>()
);

export const clientLoginFailure = createAction(
  '[Auth] Client Login Failure',
  props<{ error: string }>()
);

// Manager Login Actions
export const managerLogin = createAction(
  '[Auth] Manager Login',
  props<{ credentials: LoginRequest }>()
);

export const managerLoginSuccess = createAction(
  '[Auth] Manager Login Success',
  props<{ response: AuthResponse }>()
);

export const managerLoginFailure = createAction(
  '[Auth] Manager Login Failure',
  props<{ error: string }>()
);

// Client Register Actions
export const clientRegister = createAction(
  '[Auth] Client Register',
  props<{ data: RegisterRequest }>()
);

export const clientRegisterSuccess = createAction(
  '[Auth] Client Register Success',
  props<{ response: AuthResponse }>()
);

export const clientRegisterFailure = createAction(
  '[Auth] Client Register Failure',
  props<{ error: string }>()
);

// Manager Register Actions
export const managerRegister = createAction(
  '[Auth] Manager Register',
  props<{ data: ManagerRegisterRequest }>()
);

export const managerRegisterSuccess = createAction(
  '[Auth] Manager Register Success',
  props<{ response: AuthResponse }>()
);

export const managerRegisterFailure = createAction(
  '[Auth] Manager Register Failure',
  props<{ error: string }>()
);

// Logout Actions
export const logout = createAction('[Auth] Logout');
export const logoutSuccess = createAction('[Auth] Logout Success');
export const logoutFailure = createAction(
  '[Auth] Logout Failure',
  props<{ error: string }>()
);
