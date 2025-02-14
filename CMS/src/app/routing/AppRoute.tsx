import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { Logout } from "../modules/auth/pages/Logout";
import PrivateRoutes from "./PrivateRoutes";
import AuthPage from "../modules/auth/AuthPage";
import ErrorPage from "../modules/error/ErrorPage";
import { useAuth } from "../modules/auth";
const { BASE_URL } = import.meta.env;
const AppRoute = () => {
  const currentUser = true;
  // const { currentUser } = useAuth();
  // console.log(currentUser);
  return (
    <BrowserRouter basename={BASE_URL}>
      <Routes>
        <Route>
          <Route path="error/*" element={<ErrorPage />} />
          <Route path="logout" element={<Logout />} />
          {currentUser ? (
            <>
              <Route path="/*" element={<PrivateRoutes />} />
              <Route index element={<Navigate to="/dashboard" />} />
            </>
          ) : (
            <>
              <Route path="auth/*" element={<AuthPage />} />
              <Route path="*" element={<Navigate to="/auth" />} />
            </>
          )}
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoute;
