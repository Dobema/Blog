export function getRouteFromHash() {
  const hash = window.location.hash.replace(/^#/, "");
  return hash || "/";
}

// Das Aendern des Hashes reicht fuer unsere aktuelle einfache Navigation schon aus.
export function navigate(to) {
  window.location.hash = to;
}
