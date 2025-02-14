import { Menu } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { FLAG_LOCAL_STORAGE_KEY } from "@/app/utils/helper";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import enFlag from "@/assets/icons/en.svg";
import vnFlag from "@/assets/icons/vn.svg";
const items = [
  {
    key: "sub1",
    label: "User",
    icon: <UserOutlined />,
    children: [
      {
        key: "3",
        label: "Thông tin",
      },
      {
        key: "4",
        label: "Đăng xuất",
      },
    ],
  },
];
const HeaderApp = () => {
  const { i18n } = useTranslation();
  const [language, setLanguage] = useState(
    localStorage.getItem(FLAG_LOCAL_STORAGE_KEY) || "vn"
  );

  const changeLanguage = (lang: string) => {
    setLanguage(lang === "vn" ? "vn" : "en");
    localStorage.setItem(FLAG_LOCAL_STORAGE_KEY, lang);
    i18n.changeLanguage(lang === "vn" ? "vn" : "en");
  };
  return (
    <div className="flex flex-row justify-end items-center gap-2">
      <Menu
        style={{
          width: 120,
        }}
        defaultSelectedKeys={["1"]}
        defaultOpenKeys={["sub1"]}
        mode={"vertical"}
        // theme={theme}
        items={items}
      />
      {/* Change Language */}
      <div className="flex items-center justify-center gap-1">
        <button
          onClick={() => changeLanguage("en")}
          className={`cursor-pointer flex items-center gap-2 pointer rounded-md p-2 ${
            language == "en" ? "bg-blue-400" : ""
          }`}
        >
          <img src={enFlag} alt="English" className="h-6 w-6" />
        </button>

        <button
          onClick={() => changeLanguage("vn")}
          className={`cursor-pointer flex items-center gap-2 pointer rounded-md p-2 ${
            language == "vn" ? "bg-blue-400" : ""
          }`}
        >
          <img src={vnFlag} alt="Vietnamese" className="h-6 w-6" />
        </button>
      </div>
    </div>
  );
};

export default HeaderApp;
