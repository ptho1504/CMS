import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import AuthLayout from "./AuthLayout";
import { Logout } from "./pages/Logout";

const AuthPage = () => (
  <Routes>
    <Route element={<AuthLayout />}>
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<Signup />} />
      <Route path="logout" element={<Logout />} />
      <Route index element={<Login />} />
    </Route>
  </Routes>
);

export default AuthPage;
