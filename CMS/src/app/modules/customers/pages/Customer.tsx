import { useBreadcrumb } from "@/app/layout/core/BreadcrumContext";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import TableCustomer from "../components/TableCustomer";
import HeaderCustomer from "../components/HeaderCustomer";

const Customer = () => {
  const { setBreadcrumb } = useBreadcrumb();
  const { t, i18n } = useTranslation();

  // Set dashboard
  useEffect(() => {
    setBreadcrumb([
      `${t("dashboard.customer.text")}`,
      `${t("dashboard.customer.manager.text")}`,
    ]);
  }, [t, i18n, setBreadcrumb]);

  return (
    <div className="flex flex-col gap-10">
      <HeaderCustomer />
      <TableCustomer />
    </div>
  );
};

export default Customer;
