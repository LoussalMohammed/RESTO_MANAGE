import { createReducer, on } from '@ngrx/store';
import { initialAuthState } from './auth.state';
import * as AuthActions from './auth.actions';

export const authReducer = createReducer(
  initialAuthState,

  // Client Login
  on(AuthActions.clientLogin, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AuthActions.clientLoginSuccess, (state, { response }) => ({
    ...state,
    user: response,
    loading: false,
    error: null
  })),
  on(AuthActions.clientLoginFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Manager Login
  on(AuthActions.managerLogin, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AuthActions.managerLoginSuccess, (state, { response }) => ({
    ...state,
    user: response,
    loading: false,
    error: null
  })),
  on(AuthActions.managerLoginFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Client Register
  on(AuthActions.clientRegister, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AuthActions.clientRegisterSuccess, (state, { response }) => ({
    ...state,
    user: response,
    loading: false,
    error: null
  })),
  on(AuthActions.clientRegisterFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Manager Register
  on(AuthActions.managerRegister, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AuthActions.managerRegisterSuccess, (state, { response }) => ({
    ...state,
    user: response,
    loading: false,
    error: null
  })),
  on(AuthActions.managerRegisterFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Logout
  on(AuthActions.logout, (state) => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AuthActions.logoutSuccess, () => ({
    ...initialAuthState
  })),
  on(AuthActions.logoutFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  }))
);
