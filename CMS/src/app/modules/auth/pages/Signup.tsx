/* eslint-disable @typescript-eslint/no-explicit-any */
import { useTranslation } from "react-i18next";
import enFlag from "../../../../assets/icons/en.svg";
import vnFlag from "../../../../assets/icons/vn.svg";
import { useState } from "react";
import { FLAG_LOCAL_STORAGE_KEY } from "../../../utils/helper";

const Signup = () => {
  const { t, i18n } = useTranslation();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [errors, setErrors] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [language, setLanguage] = useState(
    localStorage.getItem(FLAG_LOCAL_STORAGE_KEY) || "vn"
  );

  const changeLanguage = (lang: string) => {
    localStorage.setItem(FLAG_LOCAL_STORAGE_KEY, lang);
    setLanguage(lang === "vn" ? "vn" : "en");
    i18n.changeLanguage(lang === "vn" ? "vn" : "en");

    // Reset Form
    setErrors({ email: "", password: "", confirmPassword: "" });
    setFormData({ email: "", password: "", confirmPassword: "" });
  };
  const handleChange = (e: any) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validate = () => {
    let valid = true;
    const newErrors = { email: "", password: "", confirmPassword: "" };

    // Email validation
    if (!formData.email) {
      newErrors.email = t("login.email.validation.email_required");
      valid = false;
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = t("login.email.validation.email_invalid");
      valid = false;
    }

    // Password validation (min 3 characters)
    if (!formData.password) {
      newErrors.password = t("login.password.validation.password_required");
      valid = false;
    } else if (formData.password.length < 3) {
      newErrors.password = t("login.password.validation.password_short");
      valid = false;
    }

    if (!formData.confirmPassword) {
      newErrors.confirmPassword = t(
        "login.password.validation.password_required"
      );
      valid = false;
    } else if (formData.password.length < 3) {
      newErrors.confirmPassword = t("login.password.validation.password_short");
      valid = false;
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = t(
        "signup.cf-password.validation.password_not_equal"
      );
      valid = false;
    }

    setErrors(newErrors);
    return valid;
  };

  const handleSubmit = (e: any) => {
    e.preventDefault();
    console.log("Sign Up Data:", formData);
    // Add authentication logic here
    if (validate()) {
      console.log("Sign up successful:", formData);
    }
  };

  return (
    <div className="flex rounded-md md:w-[50%] w-[100%] items-center justify-center">
      <div className="w-full max-w-md rounded-lg bg-white p-8 shadow-lg">
        <h2 className="mb-6 text-center text-2xl font-semibold text-gray-700">
          {t("signup.text")}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Email Field */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-600">
              {t("signup.email.text")}
            </label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              className="w-full rounded-lg border border-2 p-3 focus:border-blue-500 focus:outline-none"
              placeholder={`${t("signup.email.place_holder")}`}
            />
            {errors.email && (
              <p className="mt-1 text-sm text-red-500">{errors.email}</p>
            )}
          </div>

          {/* Password Field */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-600">
              {t("signup.password.text")}
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              className="w-full rounded-lg border border-2 p-3 focus:border-blue-500 focus:outline-none"
              placeholder={`${t("signup.password.place_holder")}`}
            />
            {errors.password && (
              <p className="mt-1 text-sm text-red-500">{errors.password}</p>
            )}
          </div>

          {/* Password Confirm Field */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-600">
              {t("signup.cf-password.text")}
            </label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
              className="w-full rounded-lg border border-2 p-3 focus:border-blue-500 focus:outline-none"
              placeholder={`${t("signup.cf-password.place_holder")}`}
            />
            {errors.confirmPassword && (
              <p className="mt-1 text-sm text-red-500">
                {errors.confirmPassword}
              </p>
            )}
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="cursor-pointer w-full rounded-lg bg-blue-600 px-4 py-2 text-white transition hover:bg-blue-700"
          >
            {t("signup.text")}
          </button>
        </form>

        {/* Register & Forgot Password Links */}
        <div className="mt-4 text-center text-sm text-gray-600">
          <p>
            {t("signup.have_account")}
            <a href="/auth/login" className="text-blue-500 hover:underline">
              {t("login.text")}
            </a>
          </p>
          <div className="mt-4 flex items-center justify-center gap-4">
            <button
              onClick={() => changeLanguage("en")}
              className={`cursor-pointer flex items-center gap-2 pointer rounded-md p-2 ${
                language == "en" ? "bg-blue-400" : ""
              }`}
            >
              <img src={enFlag} alt="English" className="h-6 w-6" />
              <span
                className={`text-gray-600 font-semibold ${
                  language == "en" ? "text-white" : "text-black"
                } `}
              >
                {t("login.en")}
              </span>
            </button>

            <button
              onClick={() => changeLanguage("vn")}
              className={`cursor-pointer flex items-center gap-2 pointer rounded-md p-2 ${
                language == "vn" ? "bg-blue-400" : ""
              }`}
            >
              <img src={vnFlag} alt="Vietnamese" className="h-6 w-6" />
              <span
                className={`text-gray-600 font-semibold ${
                  language == "vn" ? "text-white" : "text-black"
                } `}
              >
                {t("login.vn")}
              </span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Signup;
