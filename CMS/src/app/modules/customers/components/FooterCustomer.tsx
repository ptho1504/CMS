import { useListView } from "../core/ListViewProvider";
import { useQueryResponsePagination } from "../core/QueryResponseProvider";

const FooterCustomer = () => {
  const pagination = useQueryResponsePagination();
  const { selected } = useListView();
  // console.log("SElected", selected);
  return <div>{`${selected.length} /  ${pagination.total} `} Customer</div>;
};

export default FooterCustomer;
