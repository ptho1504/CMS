import { Outlet } from "react-router-dom";
import { QueryRequestProvider } from "../core/QueryRequestProvider";
import { QueryResponseProvider } from "../core/QueryResponseProvider";
import { ListViewProvider } from "../core/ListViewProvider";

const CustomerWrapper = () => {
  return (
    <QueryRequestProvider>
      <QueryResponseProvider>
        <ListViewProvider>
          <Outlet />
        </ListViewProvider>
      </QueryResponseProvider>
    </QueryRequestProvider>
  );
};

export default CustomerWrapper;
