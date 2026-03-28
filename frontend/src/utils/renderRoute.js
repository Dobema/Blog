import { createElement } from "react";

export function renderRoute(route, pageComponents) {
  // Unbekannte Routen fallen automatisch auf die Startseite zurueck.
  const PageComponent = pageComponents[route] ?? pageComponents["/"];
  return createElement(PageComponent);
}
