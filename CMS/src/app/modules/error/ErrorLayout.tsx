import React from "react";
import { Outlet } from "react-router-dom";
import bgImage from "@/assets/image/bg-dark.jpg";
const ErrorLayout = () => {
  return (
    <div
      className="flex min-h-screen flex-col items-center justify-center bg-gray-100 p-10"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <div className="w-full max-w-2xl rounded-lg bg-white p-5 shadow-lg">
        <div className="text-center">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default ErrorLayout;
