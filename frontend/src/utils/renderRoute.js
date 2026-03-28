import { createElement } from "react";

export function renderRoute(route, pageComponents) {
  const PageComponent = pageComponents[route] ?? pageComponents["/"];
  return createElement(PageComponent);
}
