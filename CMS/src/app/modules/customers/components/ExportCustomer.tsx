import { useTranslation } from "react-i18next";

const ExportCustomer = () => {
  const { t } = useTranslation();
  return (
    <div
      className="bg-blue-600 py-2 px-4 border-2 rounded-lg text-md font-medium text-white hover:cursor-pointer
     hover:text-blue-500  hover:bg-white hover:border-2"
    >
      {t("customers.export.text")}
    </div>
  );
};

export default ExportCustomer;
