import {
  createContext,
  Dispatch,
  ReactNode,
  SetStateAction,
  useContext,
  useEffect,
  useState,
} from "react";
import { AuthModel, UserModel } from "./_model";
import * as AuthHelper from "./_helper";
import * as authService from "./_service";

type AuthContextProps = {
  auth: AuthModel | undefined;
  saveAuth: (auth: AuthModel | undefined) => void;
  currentUser: UserModel | undefined;
  setCurrentUser: Dispatch<SetStateAction<UserModel | undefined>>;
  logout: () => void;
};

const initAuthContextPropsState = {
  auth: AuthHelper.getAuth(),
  saveAuth: () => {},
  currentUser: undefined,
  setCurrentUser: () => {},
  logout: () => {},
};

const AuthContext = createContext<AuthContextProps>(initAuthContextPropsState);
const useAuth = () => {
  return useContext(AuthContext);
};
const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [auth, setAuth] = useState<AuthModel | undefined>(AuthHelper.getAuth());
  const [currentUser, setCurrentUser] = useState<UserModel | undefined>();
  const saveAuth = (auth: AuthModel | undefined) => {
    setAuth(auth);
    if (auth) {
      AuthHelper.setAuth(auth);
    } else {
      AuthHelper.removeAuth();
    }
  };

  const logout = () => {
    saveAuth(undefined);
    setCurrentUser(undefined);
  };

  return (
    <AuthContext.Provider
      value={{ auth, saveAuth, currentUser, setCurrentUser, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

const AuthInit = ({ children }: { children: ReactNode }) => {
  const { auth, currentUser, logout, setCurrentUser } = useAuth();

  // We should request user by authToken (IN OUR EXAMPLE IT'S API_TOKEN) before rendering the application
  useEffect(() => {
    const requestUser = async (apiToken: string) => {
      try {
        if (!currentUser) {
          const { data } = await authService.getUserByToken(apiToken);
          if (data) {
            setCurrentUser(data);
          }
        }
      } catch (error) {
        console.error(error);
        if (currentUser) {
          logout();
        }
      }
    };

    if (auth && auth.api_token) {
      requestUser(auth.api_token);
    } else {
      logout();
    }
  }, []);
  return <>{children}</>;
};

export { AuthProvider, AuthInit, useAuth };
