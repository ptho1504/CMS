import { useBreadcrumb } from "@/app/layout/core/BreadcrumContext";
import { useEffect } from "react";
import { useTranslation } from "react-i18next";

const AddCustomer = () => {
  const { setBreadcrumb } = useBreadcrumb();
  const { t, i18n } = useTranslation();
  useEffect(() => {
    setBreadcrumb([
      `${t("dashboard.customer.text")}`,
      `${t("dashboard.customer.create.text")}`,
    ]);
  }, [t, i18n, setBreadcrumb]);
  return <div>AddCustomer</div>;
};

export default AddCustomer;
