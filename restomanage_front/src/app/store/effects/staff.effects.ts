import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, catchError, switchMap, tap } from 'rxjs/operators';
import { StaffActions } from '../actions/staff.actions';
import { StaffService } from '../../features/staff/services/staff.service';

@Injectable()
export class StaffEffects {
  loadStaff$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StaffActions.loadStaff),
      switchMap(({ params }) =>
        this.staffService.getStaff(params).pipe(
          map(response => StaffActions.loadStaffSuccess({ response })),
          catchError(error => of(StaffActions.loadStaffFailure({ error: error.message })))
        )
      )
    )
  );

  loadStaffById$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StaffActions.loadStaffById),
      switchMap(({ id }) =>
        this.staffService.getStaffById(id).pipe(
          map(staff => StaffActions.loadStaffByIdSuccess({ staff })),
          catchError(error => of(StaffActions.loadStaffByIdFailure({ error: error.message })))
        )
      )
    )
  );

  createStaff$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StaffActions.createStaff),
      switchMap(({ staff }) =>
        this.staffService.createStaff(staff).pipe(
          map(created => StaffActions.createStaffSuccess({ staff: created })),
          catchError(error => of(StaffActions.createStaffFailure({ error: error.message })))
        )
      )
    )
  );

  updateStaff$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StaffActions.updateStaff),
      switchMap(({ id, staff }) =>
        this.staffService.updateStaff(id, staff).pipe(
          map(updated => StaffActions.updateStaffSuccess({ staff: updated })),
          catchError(error => of(StaffActions.updateStaffFailure({ error: error.message })))
        )
      )
    )
  );

  deleteStaff$ = createEffect(() =>
    this.actions$.pipe(
      ofType(StaffActions.deleteStaff),
      switchMap(({ id }) =>
        this.staffService.deleteStaff(id).pipe(
          map(() => StaffActions.deleteStaffSuccess({ id })),
          catchError(error => of(StaffActions.deleteStaffFailure({ error: error.message })))
        )
      )
    )
  );

  constructor(
    private actions$: Actions,
    private staffService: StaffService
  ) {}
}
