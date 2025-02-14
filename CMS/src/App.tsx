import { AuthInit } from "./app/modules/auth";
import AppRoute from "./app/routing/AppRoute";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthInit>
        <AppRoute />
      </AuthInit>
    </QueryClientProvider>
  );
}

export default App;
