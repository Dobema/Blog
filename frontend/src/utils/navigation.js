export function getRouteFromHash() {
  const hash = window.location.hash.replace(/^#/, "");
  return hash || "/";
}

export function navigate(to) {
  window.location.hash = to;
}
