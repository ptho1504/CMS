import { Input } from "antd";
import { SearchOutlined } from "@ant-design/icons";
import { useTranslation } from "react-i18next";
import { useQueryRequest } from "../core/QueryRequestProvider";
import { useEffect, useState } from "react";
import { useDebounce } from "@/app/utils/crud";
import { initialQueryState } from "@/app/utils/helper";

const SearchingCustomer = () => {
  const { t } = useTranslation();
  const { updateState } = useQueryRequest();
  const [searchTerm, setSearchTerm] = useState<string>("");
  const debouncedSearchTerm = useDebounce(searchTerm, 300);

  useEffect(() => {
    if (debouncedSearchTerm !== undefined && searchTerm !== undefined) {
      updateState({ search: debouncedSearchTerm, ...initialQueryState });
    }
  }, [debouncedSearchTerm]);
  // eslint-disable-next-line @typescript-eslint/no-explicit-any

  return (
    <div className="relative w-full max-w-[300px]">
      <Input
        className="w-full max-w-[300px] rounded-lg border border-gray-300 px-4 py-2 text-base shadow-sm 
        transition-all focus:border-blue-500 focus:ring-2 focus:ring-blue-300"
        placeholder={`${t("customers.search.text")}`}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{
          width: 250,
        }}
        size="large"
        allowClear
        suffix={<SearchOutlined />}
      />
    </div>
  );
};

export default SearchingCustomer;
