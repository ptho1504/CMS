import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import bg_404 from "@/assets/image/404-error.png";
const Error404 = () => {
  const { t } = useTranslation();
  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100 p-6 text-center">
      {/* Title */}
      <h1 className="mb-4 text-4xl font-bold text-gray-900">
        {t("errors.404.text")}
      </h1>

      {/* Message */}
      <p className="mb-6 text-lg text-gray-500">
        {t("errors.404.description")}
      </p>

      {/* Illustration */}
      <div className="mb-4">
        <img
          src={bg_404}
          className="hidden w-full max-w-xs sm:block dark:hidden"
          alt="404 Error"
        />
        <img
          src={bg_404}
          className="hidden w-full max-w-xs dark:block"
          alt="404 Error Dark"
        />
      </div>

      <Link
        to="/dashboard"
        className="rounded bg-blue-500 px-4 py-2 text-white hover:bg-blue-600"
      >
        {t("errors.404.return_home")}
      </Link>
    </div>
  );
};

export default Error404;
