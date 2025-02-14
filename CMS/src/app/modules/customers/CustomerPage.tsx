import { Navigate, Route, Routes } from "react-router-dom";
import CustomerWrapper from "./pages/CustomerWrapper";
import Customer from "./pages/Customer";
import AddCustomer from "./pages/AddCustomer";

const CustomerPage = () => {
  return (
    <Routes>
      <Route element={<CustomerWrapper />}>
        <Route index element={<Customer />} />
        <Route
          path="/create"
          element={
            <>
              <AddCustomer />
            </>
          }
        />
      </Route>
      <Route path="*" element={<Navigate to="/apps/customers/" />} />
    </Routes>
  );
};

export default CustomerPage;
