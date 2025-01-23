import { isDevMode } from '@angular/core';
import { ActionReducerMap, MetaReducer } from '@ngrx/store';
import { routerReducer, RouterReducerState } from '@ngrx/router-store';

export interface AppState {
  router: RouterReducerState;
  // Other feature states will be added here
}

export const reducers: ActionReducerMap<AppState> = {
  router: routerReducer,
  // Other feature reducers will be added here
};

export const metaReducers: MetaReducer<AppState>[] = isDevMode() ? [] : [];
