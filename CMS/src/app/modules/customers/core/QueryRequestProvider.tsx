import {
  initialQueryRequest,
  QueryRequestContextProps,
  QueryState,
} from "@/app/utils/helper";
import { createContext, ReactNode, useContext, useState } from "react";

const QueryRequestContext =
  createContext<QueryRequestContextProps>(initialQueryRequest);

const QueryRequestProvider = ({ children }: { children: ReactNode }) => {
  const [state, setState] = useState<QueryState>(initialQueryRequest.state);

  const updateState = (updates: Partial<QueryState>) => {
    const updatedState = { ...state, ...updates } as QueryState;
    setState(updatedState);
  };

  return (
    <QueryRequestContext.Provider value={{ state, updateState }}>
      {children}
    </QueryRequestContext.Provider>
  );
};

const useQueryRequest = () => useContext(QueryRequestContext);
export { QueryRequestProvider, useQueryRequest };
