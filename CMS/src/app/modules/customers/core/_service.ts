import axios, { AxiosResponse } from "axios";
import { UsersQueryResponse } from "./_model";

const API_URL = import.meta.env.VITE_APP_API_URL;
const USER_URL = `${API_URL}/user`;

// const getUsers = (query: string): Promise<UsersQueryResponse> => {
//   return axios
//     .get(`${USER_URL}?${query}`)
//     .then((d: AxiosResponse<UsersQueryResponse>) => d.data);
// };
const getUsers = (query: string): Promise<UsersQueryResponse> => {
  return axios
    .get(`https://dummyjson.com/c/cc4a-e825-4f8a-8285`)
    .then((d: AxiosResponse<UsersQueryResponse>) => d.data);
};

export { getUsers };
