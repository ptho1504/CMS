import { createContext, useContext, useState, ReactNode } from "react";

interface BreadcrumbContextType {
  breadcrumb: string[];
  setBreadcrumb: (titles: string[]) => void;
}

// Create Context
const BreadcrumbContext = createContext<BreadcrumbContextType | undefined>(
  undefined
);

// Provider Component
export const BreadcrumbProvider = ({ children }: { children: ReactNode }) => {
  const [breadcrumb, setBreadcrumb] = useState<string[]>([]);

  return (
    <BreadcrumbContext.Provider value={{ breadcrumb, setBreadcrumb }}>
      {children}
    </BreadcrumbContext.Provider>
  );
};

// Custom Hook for using Breadcrumb Context
export const useBreadcrumb = () => {
  const context = useContext(BreadcrumbContext);
  if (!context) {
    throw new Error("useBreadcrumb must be used within a BreadcrumbProvider");
  }
  return context;
};
