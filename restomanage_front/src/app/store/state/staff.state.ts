import { EntityState } from './base.state';
import { Staff } from '../../core/models/staff.model';

export interface StaffState extends EntityState<Staff> {
  filters: {
    role?: string;
    active?: boolean;
    search?: string;
  };
}

export const initialStaffState: StaffState = {
  entities: {},
  ids: [],
  loading: false,
  loaded: false,
  error: null,
  selectedId: null,
  filters: {}
};
