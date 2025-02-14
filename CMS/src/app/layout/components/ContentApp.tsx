import { theme } from "antd";
import { Outlet } from "react-router-dom";
import BreadCrumb from "./BreadCrumb";

const ContentApp = () => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  return (
    <>
      <BreadCrumb />
      <div
        style={{
          padding: 24,
          // minHeight: 360,
          background: colorBgContainer,
          borderRadius: borderRadiusLG,
        }}
      >
        <Outlet />
      </div>
    </>
  );
};

export default ContentApp;
