import { navItems } from "../data/navigation";
import { clearStoredUser } from "../hooks/useStoredUser";
import { logoutUser } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function Navigation({ route, currentUser }) {
  // Diese Komponente ist absichtlich einfach gehalten und kennt nur die aktuelle Route.
  async function handleLogout() {
    try {
      await logoutUser();
    } finally {
      clearStoredUser();
      navigate("/login");
    }
  }

  const visibleItems = currentUser
    ? navItems.filter((item) => item.href !== "/login" && item.href !== "/register")
    : navItems.filter((item) => item.href !== "/profile" && item.href !== "/write");

  return (
    <header className="topbar">
      <button className="brand-mark" onClick={() => navigate("/")}>
        Maple Journal
      </button>

      <nav className="nav-links" aria-label="Hauptnavigation">
        {visibleItems.map((item) => (
          <button
            key={item.href}
            className={route === item.href ? "nav-link active" : "nav-link"}
            onClick={() => navigate(item.href)}
          >
            {item.label}
          </button>
        ))}
        {currentUser ? (
          <button className="nav-link" onClick={handleLogout}>
            Logout
          </button>
        ) : null}
      </nav>
    </header>
  );
}
