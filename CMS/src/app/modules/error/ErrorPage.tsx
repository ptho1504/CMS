import { Route, Routes } from "react-router-dom";
import Error404 from "./errors/Error404";
import ErrorLayout from "./ErrorLayout";

const ErrorPage = () => {
  return (
    <Routes>
      <Route element={<ErrorLayout />}>
        <Route path="404" element={<Error404 />} />
        <Route index element={<Error404 />} />
      </Route>
    </Routes>
  );
};

export default ErrorPage;
