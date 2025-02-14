import { Outlet } from "react-router-dom";

const CustomerWrapper = () => {
  console.log("CustomerWrapper");
  return (
    <>
      <Outlet />
    </>
  );
};

export default CustomerWrapper;
