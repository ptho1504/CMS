import axios from "axios";
import { AuthModel, UserModel } from "./_model";

const API_URL = import.meta.env.API_URL;

export const GET_USER_BY_ACCESS_TOKEN_URL = `${API_URL}/verify_token`;
export const LOGIN_URL = `${API_URL}/login`;
export const SIGNUP_URL = `${API_URL}/signup`;

export function login(email: string, password: string) {
  return axios.post<AuthModel>(LOGIN_URL, {
    email,
    password,
  });
}

export function register(
  email: string,
  password: string,
  password_confirmation: string
) {
  return axios.post(SIGNUP_URL, {
    email,
    password,
    password_confirmation,
  });
}

export function getUserByToken(token: string) {
  return axios.post<UserModel>(GET_USER_BY_ACCESS_TOKEN_URL, {
    api_token: token,
  });
}
