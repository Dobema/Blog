import { navigate } from "../utils/navigation";

export function HomePage({ status, featuredPosts }) {
  return (
    <div className="page-stack">
      <section className="hero-panel">
        <div className="hero-copy">
          <p className="eyebrow">Persoenlicher Blog</p>
          <h1>Ein Ort fuer Gedanken, Artikel und neue Serien.</h1>
          <p className="lead">
            Diese Startseite ist bereits so vorbereitet, dass hier spaeter echte
            Blogbeitraege aus dem Backend erscheinen koennen. Unten siehst du schon
            den vorgesehenen Beitragsbereich.
          </p>

          <div className="hero-actions">
            <button className="primary-button" onClick={() => navigate("/register")}>
              Projekt starten
            </button>
            <button className="secondary-button" onClick={() => navigate("/profile")}>
              Profil ansehen
            </button>
          </div>
        </div>

        <aside className="status-card">
          <span className="status-label">API-Status</span>
          <strong>{status}</strong>
          <p>
            Der Frontend-Bereich ist schon mit dem vorhandenen Health-Endpunkt verbunden
            und kann spaeter leicht auf echte Blogdaten umgestellt werden.
          </p>
        </aside>
      </section>

      <section className="highlights-grid">
        <article>
          <span>01</span>
          <h2>Beitraege im Fokus</h2>
          <p>Die wichtigsten Inhalte stehen auf der Startseite bewusst weit unten als Zielpunkt.</p>
        </article>
        <article>
          <span>02</span>
          <h2>Auth bereits mitgedacht</h2>
          <p>Login und Registrierung sind als eigene Seiten vorbereitet und koennen spaeter direkt an echte Endpunkte gehen.</p>
        </article>
        <article>
          <span>03</span>
          <h2>Profil mit Richtung</h2>
          <p>Das Profil zeigt schon Statistiken und eigene Inhalte, damit die App nicht nur aus Formularen besteht.</p>
        </article>
      </section>

      <section className="section-header">
        <div>
          <p className="eyebrow">Neu im Journal</p>
          <h2>Veroeffentlichte Beitraege</h2>
        </div>
        <p className="section-copy">
          Hier sollen spaeter alle publizierten Artikel aus deiner Datenbank erscheinen.
          Im Moment sind Beispielbeitraege eingebaut, damit Layout und Struktur schon stehen.
        </p>
      </section>

      <section className="post-list">
        {featuredPosts.map((post) => (
          <article key={post.id} className="post-card">
            <div className="post-meta-row">
              <span className="category-pill">{post.category}</span>
              <span>{post.readTime}</span>
            </div>
            <h3>{post.title}</h3>
            <p>{post.excerpt}</p>
            <footer className="post-footer">
              <span>{post.author}</span>
              <span>{post.publishedAt}</span>
            </footer>
          </article>
        ))}
      </section>
    </div>
  );
}
