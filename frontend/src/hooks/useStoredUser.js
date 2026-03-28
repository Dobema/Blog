import { useEffect, useState } from "react";

const STORAGE_KEY = "blog-current-user";

export function readStoredUser() {
  const rawValue = window.localStorage.getItem(STORAGE_KEY);

  if (!rawValue) {
    return null;
  }

  try {
    return JSON.parse(rawValue);
  } catch {
    return null;
  }
}

export function storeUser(user) {
  // Nach Login oder Registrierung speichern wir den aktuellen Benutzer lokal im Browser.
  window.localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
  window.dispatchEvent(new Event("blog-user-changed"));
}

export function clearStoredUser() {
  window.localStorage.removeItem(STORAGE_KEY);
  window.dispatchEvent(new Event("blog-user-changed"));
}

export function useStoredUser() {
  // Dieser Hook haelt React synchron zu localStorage, auch wenn sich der Benutzer spaeter aendert.
  const [user, setUser] = useState(() => readStoredUser());

  useEffect(() => {
    const syncUser = () => {
      setUser(readStoredUser());
    };

    window.addEventListener("storage", syncUser);
    window.addEventListener("blog-user-changed", syncUser);

    return () => {
      window.removeEventListener("storage", syncUser);
      window.removeEventListener("blog-user-changed", syncUser);
    };
  }, []);

  return user;
}
