import { Navigation } from "./components/Navigation";
import { posts, profilePosts } from "./data/blogData";
import { useApiStatus } from "./hooks/useApiStatus";
import { useHashRoute } from "./hooks/useHashRoute";
import { HomePage } from "./pages/HomePage";
import { LoginPage } from "./pages/LoginPage";
import { ProfilePage } from "./pages/ProfilePage";
import { RegisterPage } from "./pages/RegisterPage";
import { renderRoute } from "./utils/renderRoute";

export default function App() {
  const route = useHashRoute();
  const status = useApiStatus();
  const pageComponents = {
    "/": () => <HomePage status={status} featuredPosts={posts} />,
    "/login": LoginPage,
    "/register": RegisterPage,
    "/profile": () => <ProfilePage profilePosts={profilePosts} />
  };
  const content = renderRoute(route, pageComponents);

  return (
    <main className="app-shell">
      <div className="ambient ambient-left" />
      <div className="ambient ambient-right" />
      <Navigation route={route} />
      {content}
    </main>
  );
}
