export interface Page<T> {
  content: readonly T[],
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  },
}
