/* eslint-disable @typescript-eslint/no-explicit-any */

import { Response } from "@/app/utils/helper";

export type Gender = "Male" | "Female";

export type User = {
  id: number;
  gender: Gender;
  email: string;
  firstname: string;
  middlename: string;
  lastname: string;
  birthdate: string;
  birthplace: string;
  citizenship: string;
  EmbossedTitleCode?: string;
  LanguageCode?: string;
  MaritalStatusCode?: string;
  SalutationCode?: string;
  ShortName?: string;
  ClientNumber?: string;
  MobilePhone?: string;
  HomePhone?: string;
  BusinessPhone?: string;
  EmbossedLastName?: string;
  EmbossedFirstName?: string;
};

export type UsersQueryResponse = Response<Array<User>>;

export type TableProps = {
  bordered: boolean;
  loading: boolean;
  size: "large" | "middle" | "small";
  expandable?: any;
  title?: any;
  showHeader: any;
  footer: any;
  rowSelection: any;
  scroll: {
    (options?: ScrollToOptions): void;
    (x: number, y: number): void;
  };
  tableLayout: any;
};
