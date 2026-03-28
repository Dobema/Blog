import { useEffect, useState } from "react";

import { getRouteFromHash } from "../utils/navigation";

export function useHashRoute() {
  // Die Route wird aus dem URL-Hash gelesen, damit wir ohne externe Router-Bibliothek starten koennen.
  const [route, setRoute] = useState(getRouteFromHash);

  useEffect(() => {
    const handleHashChange = () => {
      setRoute(getRouteFromHash());
    };

    window.addEventListener("hashchange", handleHashChange);
    return () => window.removeEventListener("hashchange", handleHashChange);
  }, []);

  return route;
}
