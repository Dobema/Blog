// Diese Demo-Daten bleiben als Fallback erhalten, solange noch nicht jede Ansicht live aus der API liest.
export const posts = [
  {
    id: 1,
    title: "Wie aus einer Idee ein Blog mit eigener Stimme wird",
    excerpt:
      "Ein Blick auf Themenfindung, Redaktionsrhythmus und das Gefuehl, wenn ein Projekt ploetzlich wirklich nach dir klingt.",
    category: "Strategie",
    author: "Matthias",
    publishedAt: "28. Maerz 2026",
    readTime: "6 Min."
  },
  {
    id: 2,
    title: "Mein Setup fuer Schreiben, Entwerfen und Verfeinern",
    excerpt:
      "Von ersten Notizen bis zur veroeffentlichten Fassung: diese Werkzeuge und Routinen halten den Prozess angenehm leicht.",
    category: "Workflow",
    author: "Matthias",
    publishedAt: "26. Maerz 2026",
    readTime: "4 Min."
  },
  {
    id: 3,
    title: "Warum persoenliche Projekte oft die besten Lernraeume sind",
    excerpt:
      "Gerade kleine, eigene Plattformen zwingen uns dazu, Entscheidungen ueber Technik, Design und Inhalte wirklich zu treffen.",
    category: "Entwicklung",
    author: "Matthias",
    publishedAt: "21. Maerz 2026",
    readTime: "8 Min."
  }
];

export const profilePosts = [
  {
    id: 1,
    title: "Roadmap fuer mein Blog im Fruehjahr",
    status: "Veroeffentlicht",
    updatedAt: "heute"
  },
  {
    id: 2,
    title: "Entwurf: Ueber digitale Routinen und Fokus",
    status: "Entwurf",
    updatedAt: "gestern"
  },
  {
    id: 3,
    title: "Notizen zu einer Serie ueber React und Kotlin",
    status: "Idee",
    updatedAt: "vor 3 Tagen"
  }
];
