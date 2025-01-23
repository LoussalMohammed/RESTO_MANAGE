import { createFeatureSelector, createSelector } from '@ngrx/store';
import { StaffState } from '../state/staff.state';

export const selectStaffState = createFeatureSelector<StaffState>('staff');

export const selectAllStaff = createSelector(
  selectStaffState,
  (state) => state.ids.map(id => state.entities[id])
);

export const selectStaffEntities = createSelector(
  selectStaffState,
  (state) => state.entities
);

export const selectStaffIds = createSelector(
  selectStaffState,
  (state) => state.ids
);

export const selectSelectedStaffId = createSelector(
  selectStaffState,
  (state) => state.selectedId
);

export const selectSelectedStaff = createSelector(
  selectStaffEntities,
  selectSelectedStaffId,
  (entities, selectedId) => selectedId ? entities[selectedId] : null
);

export const selectStaffLoading = createSelector(
  selectStaffState,
  (state) => state.loading
);

export const selectStaffLoaded = createSelector(
  selectStaffState,
  (state) => state.loaded
);

export const selectStaffError = createSelector(
  selectStaffState,
  (state) => state.error
);

export const selectStaffFilters = createSelector(
  selectStaffState,
  (state) => state.filters
);

export const selectFilteredStaff = createSelector(
  selectAllStaff,
  selectStaffFilters,
  (staff, filters) => {
    return staff.filter(member => {
      if (filters.role && member.role !== filters.role) return false;
      if (filters.active !== undefined && member.active !== filters.active) return false;
      if (filters.search) {
        const search = filters.search.toLowerCase();
        return (
          member.firstName.toLowerCase().includes(search) ||
          member.lastName.toLowerCase().includes(search) ||
          member.email.toLowerCase().includes(search)
        );
      }
      return true;
    });
  }
);
