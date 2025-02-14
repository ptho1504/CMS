import React from "react";
import SearchingCustomer from "./SearchingCustomer";
import ExportCustomer from "./ExportCustomer";
import { Menu } from "antd";
import { MailOutlined } from "@ant-design/icons";
import { useListView } from "../core/ListViewProvider";
import FilterCustomer from "./FilterCustomer";
const items = [
  {
    key: "sub1",
    label: "Thêm",
    icon: <MailOutlined />,
    children: [
      {
        key: "3",
        label: "Gửi mail",
      },
    ],
  },
];

const HeaderCustomer = () => {
  const { selected } = useListView();
  return (
    <div className="flex flex-wrap gap-5 justify-between items-center">
      <SearchingCustomer />
      <div className="flex flex-wrap justify-center items-center gap-2">
        <FilterCustomer />
        <ExportCustomer />

        {selected.length > 0 && (
          <Menu
            style={{
              width: 120,
            }}
            defaultSelectedKeys={["1"]}
            mode={"vertical"}
            items={items}
            triggerSubMenuAction="hover"
          />
        )}
      </div>
    </div>
  );
};

export default HeaderCustomer;
