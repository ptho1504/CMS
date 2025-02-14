import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import translationEN from "../locales/en/translation.json";
import translationVN from "../locales/vn/translation.json";
import { FLAG_LOCAL_STORAGE_KEY } from "./helper";
const resources = {
  en: {
    translation: translationEN,
  },
  vn: {
    translation: translationVN,
  },
};

const savedLanguage = localStorage.getItem(FLAG_LOCAL_STORAGE_KEY) || "vn";

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    resources,
    lng: savedLanguage,

    interpolation: {
      escapeValue: false, // react already safes from xss
    },
  });

export default i18n;
