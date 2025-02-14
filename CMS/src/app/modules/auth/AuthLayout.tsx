import { Outlet } from "react-router-dom";
import bgImage from "../../../assets/image/bg-dark.jpg";
const AuthLayout = () => {
  return (
    <div
      className="min-h-screen bg-cover bg-center flex justify-center items-center"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <Outlet />
    </div>
  );
};

export default AuthLayout;
