import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import AuthLayout from "./AuthLayout";

const AuthPage = () => (
  <Routes>
    <Route element={<AuthLayout />}>
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<Signup />} />
      <Route index element={<Login />} />
    </Route>
  </Routes>
);

export default AuthPage;
