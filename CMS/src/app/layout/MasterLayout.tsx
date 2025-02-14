import {
  DesktopOutlined,
  HistoryOutlined,
  TeamOutlined,
  UserOutlined,
} from "@ant-design/icons";
import type { MenuProps } from "antd";
import { Layout, Menu, theme } from "antd";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import HeaderApp from "./components/HeaderApp";
import ContentApp from "./components/ContentApp";
import { BreadcrumbProvider } from "./core/BreadcrumContext";
import { useNavigate } from "react-router-dom";
type MenuItem = Required<MenuProps>["items"][number];
const { Header, Content, Sider } = Layout;

function getItem(
  label: React.ReactNode,
  key: React.Key,
  icon?: React.ReactNode,
  children?: MenuItem[]
): MenuItem {
  return {
    key,
    icon,
    children,
    label,
  } as MenuItem;
}

const MasterLayout = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const { t } = useTranslation();
  // Menu
  const items: MenuItem[] = [
    getItem(`${t("dashboard.text")}`, "/dashboard", <DesktopOutlined />),
    getItem(`${t("dashboard.customer.text")}`, "sub1", <UserOutlined />, [
      getItem(`${t("dashboard.customer.manager.text")}`, "/apps/customers/"),
      getItem(
        `${t("dashboard.customer.create.text")}`,
        "/apps/customers/create"
      ),
    ]),
    getItem(`${t("dashboard.contract.text")}`, "sub2", <TeamOutlined />, [
      getItem(`${t("dashboard.contract.manager.text")}`, "/apps/contracts/"),
      getItem(`${t("dashboard.contract.create.text")}`, "/apps/contracts/create"),
    ]),
    getItem(`${t("dashboard.transaction.text")}`, "9", <HistoryOutlined />),
  ];

  return (
    <>
      <BreadcrumbProvider>
        <Layout style={{ minHeight: "100vh" }}>
          <Sider
            collapsible
            collapsed={collapsed}
            onCollapse={(value) => setCollapsed(value)}
            width={225}
          >
            <div className="demo-logo-vertical" />
            <Menu
              theme="dark"
              defaultSelectedKeys={["1"]}
              mode="inline"
              items={items}
              onClick={({ key }) => navigate(key)}
            />
          </Sider>

          <Layout>
            {/* Header */}
            <Header
              className="flex items-center justify-end"
              style={{
                padding: "0 16px",
                background: colorBgContainer,
              }}
            >
              <HeaderApp />
            </Header>
            {/* Content */}
            <Content style={{ margin: "0 16px" }}>
              <ContentApp />
            </Content>
          </Layout>
        </Layout>
      </BreadcrumbProvider>
    </>
  );
};

export default MasterLayout;
