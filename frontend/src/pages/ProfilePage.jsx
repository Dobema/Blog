export function ProfilePage({ profilePosts }) {
  return (
    <div className="page-stack">
      <section className="profile-hero">
        <div className="avatar-shell">M</div>
        <div className="profile-copy">
          <p className="eyebrow">Dein Profil</p>
          <h1>Matthias Beispiel</h1>
          <p>
            Hier kannst du spaeter Profildaten, deinen Beschreibungstext und deine
            zuletzt veroeffentlichten Artikel anzeigen.
          </p>
        </div>
      </section>

      <section className="stats-grid">
        <article>
          <strong>12</strong>
          <span>Beitraege gesamt</span>
        </article>
        <article>
          <strong>3</strong>
          <span>Entwuerfe offen</span>
        </article>
        <article>
          <strong>148</strong>
          <span>Kommentare spaeter moeglich</span>
        </article>
      </section>

      <section className="section-header">
        <div>
          <p className="eyebrow">Eigene Inhalte</p>
          <h2>Deine letzten Beitraege</h2>
        </div>
        <p className="section-copy">
          Diese Liste kann spaeter direkt aus dem Benutzerprofil oder einer eigenen
          Autorenansicht geladen werden.
        </p>
      </section>

      <section className="profile-posts">
        {profilePosts.map((post) => (
          <article key={post.id} className="profile-post-card">
            <div>
              <h3>{post.title}</h3>
              <p>Zuletzt aktualisiert: {post.updatedAt}</p>
            </div>
            <span className="profile-post-status">{post.status}</span>
          </article>
        ))}
      </section>
    </div>
  );
}
