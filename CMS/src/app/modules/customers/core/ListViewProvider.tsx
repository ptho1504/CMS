import {
  calculatedGroupingIsDisabled,
  calculateIsAllDataSelected,
  groupingOnSelect,
  groupingOnSelectAll,
} from "@/app/utils/crud";
import { ID, initialListView, ListViewContextProps } from "@/app/utils/helper";
import { createContext, ReactNode, useContext, useMemo, useState } from "react";
import {
  useQueryResponse,
  useQueryResponseData,
} from "./QueryResponseProvider";

const ListViewContext = createContext<ListViewContextProps>(initialListView);

const ListViewProvider = ({ children }: { children: ReactNode }) => {
  const [selected, setSelected] = useState<Array<ID>>(initialListView.selected);
  const [itemIdForUpdate, setItemIdForUpdate] = useState<ID>(
    initialListView.itemIdForUpdate
  );
  const { isLoading } = useQueryResponse();
  const data = useQueryResponseData();
  const disabled = useMemo(
    () => calculatedGroupingIsDisabled(isLoading, data),
    [isLoading, data]
  );
  const isAllSelected = useMemo(
    () => calculateIsAllDataSelected(data, selected),
    [data, selected]
  );

  return (
    <ListViewContext.Provider
      value={{
        selected,
        itemIdForUpdate,
        setItemIdForUpdate,
        disabled,
        isAllSelected,
        onSelect: (selected: Array<ID>) => {
          // groupingOnSelect(id, selected, setSelected);
          groupingOnSelect(selected, setSelected);
        },
        onSelectAll: () => {
          groupingOnSelectAll(isAllSelected, setSelected, data);
        },
        clearSelected: () => {
          setSelected([]);
        },
      }}
    >
      {children}
    </ListViewContext.Provider>
  );
};

const useListView = () => useContext(ListViewContext);

export { ListViewProvider, useListView };
