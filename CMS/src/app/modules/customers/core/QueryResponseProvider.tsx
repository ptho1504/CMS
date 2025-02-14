import { createResponseContext, stringifyRequestQuery } from "@/app/utils/crud";
import { User } from "./_model";
import {
  initialQueryResponse,
  initialQueryState,
  PaginationState,
} from "@/app/utils/helper";
import { ReactNode, useContext, useEffect, useMemo, useState } from "react";
import { QueryKey, useQuery } from "@tanstack/react-query";
import { useQueryRequest } from "./QueryRequestProvider";
import { getUsers } from "./_service";
import { QUERIES } from "@/app/utils/const";

const QueryResponseContext = createResponseContext<User>(initialQueryResponse);

const QueryResponseProvider = ({ children }: { children: ReactNode }) => {
  const { state } = useQueryRequest();
  const [query, setQuery] = useState<string>(stringifyRequestQuery(state));
  const updatedQuery = useMemo(() => stringifyRequestQuery(state), [state]);

  useEffect(() => {
    if (query !== updatedQuery) {
      setQuery(updatedQuery);
    }
  }, [updatedQuery]);

  const {
    isFetching,
    refetch,
    data: response,
  } = useQuery(
    [`${QUERIES.USERS_LIST}-${query}`] as unknown as QueryKey,
    () => {
      return getUsers(query);
    },
    { cacheTime: 0, keepPreviousData: true, refetchOnWindowFocus: false }
  );

  return (
    <QueryResponseContext.Provider
      value={{ isLoading: isFetching, refetch, response, query }}
    >
      {children}
    </QueryResponseContext.Provider>
  );
};

const useQueryResponse = () => useContext(QueryResponseContext);

const useQueryResponseData = () => {
  const { response } = useQueryResponse();

  if (!response) {
    return [];
  }

  return response?.data || [];
};
const useQueryResponseLoading = (): boolean => {
  const { isLoading } = useQueryResponse();
  return isLoading;
};
const useQueryResponsePagination = () => {
  const defaultPaginationState: PaginationState = {
    ...initialQueryState,
  };

  const { response } = useQueryResponse();
  if (!response || !response.payload || !response.payload.pagination) {
    return defaultPaginationState;
  }

  return response.payload.pagination;
};

export {
  QueryResponseProvider,
  useQueryResponse,
  useQueryResponseData,
  useQueryResponsePagination,
  useQueryResponseLoading,
};
