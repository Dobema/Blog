import { navItems } from "../data/navigation";
import { navigate } from "../utils/navigation";

export function Navigation({ route }) {
  return (
    <header className="topbar">
      <button className="brand-mark" onClick={() => navigate("/")}>
        Maple Journal
      </button>

      <nav className="nav-links" aria-label="Hauptnavigation">
        {navItems.map((item) => (
          <button
            key={item.href}
            className={route === item.href ? "nav-link active" : "nav-link"}
            onClick={() => navigate(item.href)}
          >
            {item.label}
          </button>
        ))}
      </nav>
    </header>
  );
}
