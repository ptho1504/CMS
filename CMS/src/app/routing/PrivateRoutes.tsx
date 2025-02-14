import { Route, Routes } from "react-router-dom";
import MasterLayout from "../layout/MasterLayout";
import { Navigate } from "react-router-dom";
import DashBoardWrapper from "../modules/dashboard/DashBoardWrapper";
import CustomerPage from "../modules/customers/CustomerPage";

const PrivateRoutes = () => {
  return (
    <Routes>
      <Route element={<MasterLayout />}>
        <Route path="auth/*" element={<Navigate to="/dashboard" />} />
        <Route path="dashboard" element={<DashBoardWrapper />} />
        {/* Customer */}
        <Route path="/apps/customers/*" element={<CustomerPage />} />

        {/* Contracts */}

        {/* Transaction History */}

        {/* Errors */}
        <Route path="*" element={<Navigate to="/error/404" />} />
      </Route>
    </Routes>
  );
};

export default PrivateRoutes;
