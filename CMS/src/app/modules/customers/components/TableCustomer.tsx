import {
  InputRef,
  Space,
  Table,
  TableColumnsType,
  TablePaginationConfig,
} from "antd";
import { getColumnSearchProps } from "./getColumnSearchProp";
import { User } from "../core/_model";
import { useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import {
  FilterValue,
  SorterResult,
  TableCurrentDataSource,
  TableRowSelection,
} from "antd/es/table/interface";
import {
  useQueryResponseData,
  useQueryResponseLoading,
  useQueryResponsePagination,
} from "../core/QueryResponseProvider";
import FooterCustomer from "./FooterCustomer";
import { useListView } from "../core/ListViewProvider";

const TableCustomer = () => {
  const users = useQueryResponseData();
  const isLoading = useQueryResponseLoading();
  const pagination = useQueryResponsePagination();

  const { t } = useTranslation();

  const [searchText, setSearchText] = useState("");
  const [searchedColumn, setSearchedColumn] = useState("");
  const searchInput = useRef<InputRef>(null);

  // Get selected
  const { onSelect } = useListView();

  const columns: TableColumnsType<User> = [
    {
      title: `${t("customers.columns.id.text")}`,
      dataIndex: "id",
      key: "id",
      sorter: (a, b) => a.id - b.id,
      ...getColumnSearchProps(
        "id",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      fixed: false,
      width: 60,
    },
    {
      title: `${t("customers.columns.email.text")}`,
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
      ...getColumnSearchProps(
        "email",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      fixed: true,
    },
    {
      title: `${t("customers.columns.gender.text")}`,
      dataIndex: "gender",
      key: "gender",
      filters: [
        { text: "Female", value: "Female" },
        { text: "Male", value: "Male" },
      ],
      onFilter: (value, record) => record.gender === value,
      sorter: (a, b) => a.gender.localeCompare(b.gender),
      width: 120,
    },
    {
      title: `${t("customers.columns.firstname.text")}`,
      dataIndex: "firstname",
      key: "firstname",
      sorter: (a, b) => a.firstname.localeCompare(b.firstname),
      ...getColumnSearchProps(
        "firstname",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 120,
    },
    {
      title: `${t("customers.columns.middlename.text")}`,
      dataIndex: "middlename",
      key: "middlename",
      sorter: (a, b) => a.middlename.localeCompare(b.middlename),
      ...getColumnSearchProps(
        "middlename",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 120,
    },
    {
      title: `${t("customers.columns.lastname.text")}`,
      dataIndex: "lastname",
      key: "lastname",
      sorter: (a, b) => a.lastname.localeCompare(b.lastname),
      ...getColumnSearchProps(
        "lastname",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 120,
    },
    {
      title: `${t("customers.columns.birthdate.text")}`,
      dataIndex: "birthdate",
      key: "birthdate",
      sorter: (a, b) => a.lastname.localeCompare(b.lastname),
      ...getColumnSearchProps(
        "birthdate",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 120,
    },
    {
      title: `${t("customers.columns.birthplace.text")}`,
      dataIndex: "birthplace",
      key: "birthplace",
      sorter: (a, b) => a.birthplace.localeCompare(b.birthplace),
      ...getColumnSearchProps(
        "birthplace",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 120,
    },
    {
      title: `${t("customers.columns.citizenship.text")}`,
      dataIndex: "citizenship",
      key: "citizenship",
      sorter: (a, b) => a.citizenship.localeCompare(b.citizenship),
      ...getColumnSearchProps(
        "citizenship",
        searchText,
        searchedColumn,
        searchInput,
        setSearchText,
        setSearchedColumn
      ),
      width: 125,
    },
    {
      title: "Action",
      key: "action",
      render: () => (
        <Space size="middle">
          <a>{t("customers.action.view")}</a>
          <a>{t("customers.action.update")}</a>
          <a>{t("customers.action.delete")}</a>
        </Space>
      ),
      fixed: "right",
    },
  ];

  const onChange = (
    pagination: TablePaginationConfig,
    filters: Record<string, FilterValue | null>,
    sorter: SorterResult<User> | SorterResult<User>[],
    extra: TableCurrentDataSource<User>
  ) => {
    console.log("Pagination", pagination);
    console.log("filters", filters);
    console.log("sorter", sorter);
    console.log("extra", extra);
  };

  const handleRowSelection = (): TableRowSelection<User> => ({
    onChange: (selectedRowKeys: React.Key[], selectedRows: User[]) => {
      // console.log("Selected Row Keys:", selectedRowKeys);
      // console.log("Selected Rows:", selectedRows);
      if (selectedRows && selectedRowKeys) {
        onSelect(selectedRowKeys as React.Key[]);
      }
    },
  });

  const handlePaginationChange = (page: number, pageSize: number) => {
    console.log("page, pageSize", page, pageSize);
  };

  return (
    <>
      <Table
        showHeader={true}
        loading={isLoading}
        bordered
        size="small"
        scroll={{
          x: "max-content",
          y: 480,
        }}
        rowSelection={handleRowSelection()}
        onChange={onChange}
        showSorterTooltip={{
          target: "sorter-icon",
        }}
        rowKey={"id"}
        dataSource={users}
        columns={columns}
        pagination={{
          pageSize: pagination.items_per_page,
          showSizeChanger: true,
          total: pagination.total,
          size: "default",
          responsive: true,
          onChange: handlePaginationChange,
        }}
        footer={() => <FooterCustomer />}
      ></Table>
    </>
  );
};

export default TableCustomer;
