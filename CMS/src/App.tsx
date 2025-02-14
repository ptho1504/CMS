import { AuthInit } from "./app/modules/auth";
import AppRoute from "./app/routing/AppRoute";



function App() {
  return (
    <AuthInit>
      <AppRoute />
    </AuthInit>
  );
}

export default App;
