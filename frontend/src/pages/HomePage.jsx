import { useEffect, useState } from "react";

import { loadPosts } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function HomePage({ status, fallbackPosts, currentUser }) {
  const [posts, setPosts] = useState(fallbackPosts);

  useEffect(() => {
    let active = true;

    // Die Startseite zieht ihre Beitraege bevorzugt direkt aus dem Backend,
    // damit neue Inhalte nach dem Schreiben sofort sichtbar werden.
    loadPosts()
      .then((data) => {
        if (active && Array.isArray(data) && data.length > 0) {
          setPosts(data);
        }
      })
      .catch(() => {
        if (active) {
          setPosts(fallbackPosts);
        }
      });

    return () => {
      active = false;
    };
  }, [fallbackPosts]);

  return (
    <div className="page-stack">
      <section className="hero-panel">
        <div className="hero-copy">
          <p className="eyebrow">Persoenlicher Blog</p>
          <h1>Ein Ort fuer Gedanken, Artikel und neue Serien.</h1>
          <p className="lead">
            Diese Startseite liest veroeffentlichte Beitraege jetzt bereits aus dem Backend.
            Neue Artikel aus dem Schreibformular tauchen dadurch direkt hier auf.
          </p>

          <div className="hero-actions">
            <button
              className="primary-button"
              onClick={() => navigate(currentUser ? "/write" : "/register")}
            >
              {currentUser ? "Neuen Beitrag schreiben" : "Projekt starten"}
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
            Die Startseite verbindet jetzt Health-Check und echte Beitragsdaten aus der Datenbank.
          </p>
        </aside>
      </section>

      <section className="highlights-grid">
        <article>
          <span>01</span>
          <h2>Echte Session</h2>
          <p>Login und Logout arbeiten jetzt ueber eine echte Session im Backend.</p>
        </article>
        <article>
          <span>02</span>
          <h2>Beitraege schreiben</h2>
          <p>Neue Artikel werden ueber ein Formular angelegt und in der Datenbank gespeichert.</p>
        </article>
        <article>
          <span>03</span>
          <h2>Direkt sichtbar</h2>
          <p>Veroeffentlichte Posts erscheinen nach dem Speichern direkt wieder auf der Startseite.</p>
        </article>
      </section>

      <section className="section-header">
        <div>
          <p className="eyebrow">Neu im Journal</p>
          <h2>Veroeffentlichte Beitraege</h2>
        </div>
        <p className="section-copy">
          Hier werden jetzt echte, veroeffentlichte Blogartikel aus der Datenbank geladen.
        </p>
      </section>

      <section className="post-list">
        {posts.map((post) => (
          <article key={post.id} className="post-card">
            <div className="post-meta-row">
              <span className="category-pill">{post.category}</span>
              <span>{post.readTime || post.status || "Beitrag"}</span>
            </div>
            <h3>{post.title}</h3>
            <p>{post.excerpt}</p>
            <footer className="post-footer">
              <span>{post.author}</span>
              <span>{post.publishedAt || "Noch nicht veroeffentlicht"}</span>
            </footer>
            {post.slug ? (
              <button
                className="text-button"
                onClick={() => navigate(`/posts/${post.slug}`)}
              >
                Beitrag lesen und kommentieren
              </button>
            ) : null}
          </article>
        ))}
      </section>
    </div>
  );
}
