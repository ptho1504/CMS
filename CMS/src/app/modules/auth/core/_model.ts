export interface AuthModel {
  api_token: string;
  refresh_token?: string;
}

export interface UserModel {
  email: string;
  password: string;
}
