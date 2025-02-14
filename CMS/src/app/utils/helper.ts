import { Dispatch, SetStateAction } from "react";

export const toAbsoluteUrl = (pathname: string) =>
  import.meta.env.BASE_URL + pathname;

export const FLAG_LOCAL_STORAGE_KEY = "FLAG_LSK";

// ID
export type ID = undefined | null | number | string | bigint;

// State
export type PaginationState = {
  page: number;
  items_per_page: 10 | 30 | 50 | 100;
  total?: number;
};

export type SortState = {
  sort?: string;
  order?: "ascend" | "descend";
};

export type FilterState = {
  filter?: unknown;
};

export type SearchState = {
  search?: string;
};

export type QueryState = PaginationState &
  SortState &
  FilterState &
  SearchState;

// Request
export type QueryRequestContextProps = {
  state: QueryState;
  updateState: (updates: Partial<QueryState>) => void;
};

export const initialQueryState: QueryState = {
  page: 1,
  items_per_page: 10,
  total: 0,
};
//  Request
export const initialQueryRequest: QueryRequestContextProps = {
  state: initialQueryState,
  updateState: () => {},
};

// Response
export type Response<T> = {
  data?: T;
  payload?: {
    message?: string;
    errors?: {
      [key: string]: Array<string>;
    };
    pagination?: PaginationState;
  };
};

export type QueryResponseContextProps<T> = {
  response?: Response<Array<T>> | undefined;
  refetch: () => void;
  isLoading: boolean;
  query: string;
};
export const initialQueryResponse = {
  refetch: () => {},
  isLoading: false,
  query: "",
};
// List View
export type ListViewContextProps = {
  selected: Array<ID>;
  onSelect: (selectedId: Array<ID>) => void;
  onSelectAll: () => void;
  clearSelected: () => void;
  // NULL => (CREATION MODE) | MODAL IS OPENED
  // NUMBER => (EDIT MODE) | MODAL IS OPENED
  // UNDEFINED => MODAL IS CLOSED
  itemIdForUpdate?: ID;
  setItemIdForUpdate: Dispatch<SetStateAction<ID>>;
  isAllSelected: boolean;
  disabled: boolean;
};

export const initialListView: ListViewContextProps = {
  selected: [],
  onSelect: () => {},
  onSelectAll: () => {},
  clearSelected: () => {},
  setItemIdForUpdate: () => {},
  isAllSelected: false,
  disabled: false,
};
