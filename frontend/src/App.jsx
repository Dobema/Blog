import { useEffect } from "react";

import { Navigation } from "./components/Navigation";
import { posts, profilePosts } from "./data/blogData";
import { useApiStatus } from "./hooks/useApiStatus";
import { useHashRoute } from "./hooks/useHashRoute";
import { clearStoredUser, storeUser, useStoredUser } from "./hooks/useStoredUser";
import { CreatePostPage } from "./pages/CreatePostPage";
import { HomePage } from "./pages/HomePage";
import { LoginPage } from "./pages/LoginPage";
import { PostDetailPage } from "./pages/PostDetailPage";
import { ProfilePage } from "./pages/ProfilePage";
import { RegisterPage } from "./pages/RegisterPage";
import { loadCurrentUser } from "./utils/authApi";
import { renderRoute } from "./utils/renderRoute";

export default function App() {
  // Die App setzt nur die grossen Bausteine zusammen und uebergibt Daten an die passende Seite.
  const route = useHashRoute();
  const status = useApiStatus();
  const currentUser = useStoredUser();

  useEffect(() => {
    // Beim Start gleichen wir den lokalen Browserzustand einmal mit der echten Session im Backend ab.
    loadCurrentUser()
      .then((user) => {
        storeUser(user);
      })
      .catch((error) => {
        if (error.status === 401) {
          clearStoredUser();
        }
      });
  }, []);

  const pageComponents = {
    "/": () => <HomePage status={status} fallbackPosts={posts} currentUser={currentUser} />,
    "/write": () => <CreatePostPage currentUser={currentUser} />,
    "/login": LoginPage,
    "/register": RegisterPage,
    "/profile": () => <ProfilePage profilePosts={profilePosts} />
  };
  const content = route.startsWith("/posts/")
    ? <PostDetailPage slug={route.replace("/posts/", "")} currentUser={currentUser} />
    : renderRoute(route, pageComponents);

  return (
    <main className="app-shell">
      <div className="ambient ambient-left" />
      <div className="ambient ambient-right" />
      <Navigation route={route} currentUser={currentUser} />
      {content}
    </main>
  );
}
