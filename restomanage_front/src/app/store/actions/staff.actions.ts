import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Staff } from '../../core/models/staff.model';
import { PaginatedResponse, QueryParams } from '../state/base.state';

export const StaffActions = createActionGroup({
  source: 'Staff',
  events: {
    // Load staff
    'Load Staff': props<{ params: QueryParams }>(),
    'Load Staff Success': props<{ response: PaginatedResponse<Staff> }>(),
    'Load Staff Failure': props<{ error: string }>(),

    // Load single staff
    'Load Staff By Id': props<{ id: number }>(),
    'Load Staff By Id Success': props<{ staff: Staff }>(),
    'Load Staff By Id Failure': props<{ error: string }>(),

    // Create staff
    'Create Staff': props<{ staff: Partial<Staff> }>(),
    'Create Staff Success': props<{ staff: Staff }>(),
    'Create Staff Failure': props<{ error: string }>(),

    // Update staff
    'Update Staff': props<{ id: number; staff: Partial<Staff> }>(),
    'Update Staff Success': props<{ staff: Staff }>(),
    'Update Staff Failure': props<{ error: string }>(),

    // Delete staff
    'Delete Staff': props<{ id: number }>(),
    'Delete Staff Success': props<{ id: number }>(),
    'Delete Staff Failure': props<{ error: string }>(),

    // Set selected staff
    'Set Selected Staff': props<{ id: number | null }>(),

    // Update filters
    'Update Filters': props<{ filters: Partial<StaffState['filters']> }>(),
    
    // Clear state
    'Clear Staff': emptyProps()
  }
});
