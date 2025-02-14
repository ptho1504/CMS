import { useBreadcrumb } from "@/app/layout/core/BreadcrumContext";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";

const Customer = () => {
  const { setBreadcrumb } = useBreadcrumb();
  const { t, i18n } = useTranslation();
  useEffect(() => {
    setBreadcrumb([
      `${t("dashboard.customer.text")}`,
      `${t("dashboard.customer.manager.text")}`,
    ]);
  }, [t, i18n, setBreadcrumb]);
  return <div>Customer</div>;
};

export default Customer;
