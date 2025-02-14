import { Breadcrumb } from "antd";
import { useBreadcrumb } from "../core/BreadcrumContext";

const BreadCrumb = () => {
  const { breadcrumb } = useBreadcrumb();

  return (
    <Breadcrumb style={{ margin: "16px 0" }}>
      {breadcrumb.map((title: string, index: number) => (
        <Breadcrumb.Item key={index}>{title}</Breadcrumb.Item>
      ))}
    </Breadcrumb>
  );
};

export default BreadCrumb;
