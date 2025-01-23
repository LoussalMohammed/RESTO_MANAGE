import { createReducer, on } from '@ngrx/store';
import { StaffActions } from '../actions/staff.actions';
import { StaffState, initialStaffState } from '../state/staff.state';

export const staffReducer = createReducer(
  initialStaffState,

  // Load staff
  on(StaffActions.loadStaff, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(StaffActions.loadStaffSuccess, (state, { response }) => {
    const entities = response.items.reduce((acc, staff) => ({
      ...acc,
      [staff.id!]: staff
    }), {});

    return {
      ...state,
      entities,
      ids: response.items.map(staff => staff.id!.toString()),
      loading: false,
      loaded: true,
      error: null
    };
  }),

  on(StaffActions.loadStaffFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Load single staff
  on(StaffActions.loadStaffById, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(StaffActions.loadStaffByIdSuccess, (state, { staff }) => ({
    ...state,
    entities: {
      ...state.entities,
      [staff.id!]: staff
    },
    loading: false,
    error: null
  })),

  on(StaffActions.loadStaffByIdFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Create staff
  on(StaffActions.createStaff, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(StaffActions.createStaffSuccess, (state, { staff }) => ({
    ...state,
    entities: {
      ...state.entities,
      [staff.id!]: staff
    },
    ids: [...state.ids, staff.id!.toString()],
    loading: false,
    error: null
  })),

  on(StaffActions.createStaffFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Update staff
  on(StaffActions.updateStaff, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(StaffActions.updateStaffSuccess, (state, { staff }) => ({
    ...state,
    entities: {
      ...state.entities,
      [staff.id!]: staff
    },
    loading: false,
    error: null
  })),

  on(StaffActions.updateStaffFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Delete staff
  on(StaffActions.deleteStaff, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(StaffActions.deleteStaffSuccess, (state, { id }) => {
    const { [id]: removed, ...entities } = state.entities;
    return {
      ...state,
      entities,
      ids: state.ids.filter(staffId => staffId !== id.toString()),
      loading: false,
      error: null
    };
  }),

  on(StaffActions.deleteStaffFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),

  // Set selected staff
  on(StaffActions.setSelectedStaff, (state, { id }) => ({
    ...state,
    selectedId: id?.toString() ?? null
  })),

  // Update filters
  on(StaffActions.updateFilters, (state, { filters }) => ({
    ...state,
    filters: {
      ...state.filters,
      ...filters
    }
  })),

  // Clear state
  on(StaffActions.clearStaff, () => initialStaffState)
);
