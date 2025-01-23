export interface EntityState<T> {
  entities: { [id: string]: T };
  ids: string[];
  loading: boolean;
  loaded: boolean;
  error: string | null;
  selectedId: string | null;
}

export const initialEntityState = {
  entities: {},
  ids: [],
  loading: false,
  loaded: false,
  error: null,
  selectedId: null
};

export interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
}

export interface QueryParams {
  page?: number;
  pageSize?: number;
  sortBy?: string;
  sortDirection?: 'asc' | 'desc';
  search?: string;
  [key: string]: any;
}
