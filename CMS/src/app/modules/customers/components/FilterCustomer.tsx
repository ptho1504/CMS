import { Button } from "antd";
import Modal from "antd/es/modal/Modal";
import { useState } from "react";
import { useTranslation } from "react-i18next";

const FilterCustomer = () => {
  const { t } = useTranslation();
  const [open, setOpen] = useState(false);

  const showModal = () => {
    setOpen(true);
  };
  const handleOk = () => {
    setOpen(false);
  };

  const handleCancel = () => {
    setOpen(false);
  };
  return (
    <>
      <Button size="large" onClick={showModal}>
        {t("customers.filter.text")}
      </Button>
      <Modal
        open={open}
        title="Title"
        onOk={handleOk}
        onCancel={handleCancel}
        cancelText={t("customers.close.text")}
        okText={t("customers.ok.text")}
        footer={(_, { OkBtn, CancelBtn }) => (
          <>
            <CancelBtn />
            <OkBtn />
          </>
        )}
      >
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
      </Modal>
    </>
  );
};

export default FilterCustomer;
