import { SearchOutlined } from "@ant-design/icons";
import { Button, Input, Space } from "antd";

import Highlighter from "react-highlight-words";
import type { InputRef, TableColumnType } from "antd";
import { RefObject } from "react";
import { User } from "../core/_model";
import { FilterDropdownProps } from "antd/es/table/interface";
import i18next from "i18next";

type DataIndex = keyof User;
export const getColumnSearchProps = (
  dataIndex: DataIndex,
  searchText: string,
  searchedColumn: string,
  searchInput: RefObject<InputRef>,
  setSearchText: (text: string) => void,
  setSearchedColumn: (column: string) => void
): TableColumnType<User> => ({
  filterDropdown: ({
    setSelectedKeys,
    selectedKeys,
    confirm,
    clearFilters,
    close,
  }: FilterDropdownProps) => {
    return (
      <div style={{ padding: 8 }} onKeyDown={(e) => e.stopPropagation()}>
        <Input
          ref={searchInput}
          placeholder={`${i18next.t(`customers.columns.${dataIndex}.place_holder`)}`}
          value={selectedKeys[0]}
          onChange={(e) =>
            setSelectedKeys(e.target.value ? [e.target.value] : [])
          }
          onPressEnter={() => {
            confirm();
            setSearchText(selectedKeys[0] as string);
            setSearchedColumn(dataIndex);
          }}
          style={{ marginBottom: 8, display: "block" }}
        />
        <Space>
          <Button
            type="primary"
            onClick={() => {
              confirm();
              setSearchText(selectedKeys[0] as string);
              setSearchedColumn(dataIndex);
            }}
            icon={<SearchOutlined />}
            size="small"
            style={{ width: 120 }}
          >
            {i18next.t("customers.search.text")}
          </Button>
          <Button
            onClick={() => {
              clearFilters?.();
              setSearchText("");
            }}
            size="small"
            style={{ width: 90 }}
          >
            {i18next.t("customers.reset.text")}
          </Button>
          <Button type="link" size="small" onClick={close}>
          {i18next.t("customers.close.text")}
          </Button>
        </Space>
      </div>
    );
  },
  filterIcon: (filtered: boolean) => (
    <SearchOutlined style={{ color: filtered ? "#1677ff" : undefined }} />
  ),
  onFilter: (value: boolean | React.Key, record: User) => {
    return record[dataIndex] !== undefined
      ? record[dataIndex]
          .toString()
          .toLowerCase()
          .includes((value as string).toLowerCase())
      : false;
  },
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  render: (text: any) =>
    searchedColumn === dataIndex ? (
      <Highlighter
        highlightStyle={{ backgroundColor: "#ffc069", padding: 0 }}
        searchWords={[searchText]}
        autoEscape
        textToHighlight={text ? text.toString() : ""}
      />
    ) : (
      text
    )
});
