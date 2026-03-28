import { useEffect, useState } from "react";

import { useStoredUser } from "../hooks/useStoredUser";
import { loadOwnProfile } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function ProfilePage({ profilePosts }) {
  const storedUser = useStoredUser();
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!storedUser?.username) {
      setProfile(null);
      return;
    }

    let active = true;

    // Das Profil wird jetzt ueber die Session geladen und nicht mehr nur ueber lokale Browserdaten.
    loadOwnProfile()
      .then((data) => {
        if (active) {
          setProfile(data);
          setError("");
        }
      })
      .catch((loadError) => {
        if (active) {
          setError(loadError.message);
        }
      });

    return () => {
      active = false;
    };
  }, [storedUser]);

  if (!storedUser) {
    return (
      <section className="auth-layout">
        <div className="auth-copy">
          <p className="eyebrow">Profil</p>
          <h1>Bitte zuerst einloggen oder registrieren.</h1>
          <p>Ohne gespeicherten Benutzer kann die Profilseite keine Daten aus der API laden.</p>
        </div>
        <div className="auth-card">
          <button className="primary-button full-width" onClick={() => navigate("/login")}>
            Zum Login
          </button>
        </div>
      </section>
    );
  }

  const postsToRender = profile?.posts ?? profilePosts;
  const displayName = profile?.username ?? storedUser.username;

  return (
    <div className="page-stack">
      <section className="profile-hero">
        <div className="avatar-shell">{displayName.slice(0, 1).toUpperCase()}</div>
        <div className="profile-copy">
          <p className="eyebrow">Dein Profil</p>
          <h1>{displayName}</h1>
          <p>
            {profile?.bio ||
              "Hier kannst du spaeter Profildaten, deinen Beschreibungstext und deine zuletzt veroeffentlichten Artikel anzeigen."}
          </p>
        </div>
      </section>

      <section className="stats-grid">
        <article>
          <strong>{profile?.totalPosts ?? postsToRender.length}</strong>
          <span>Beitraege gesamt</span>
        </article>
        <article>
          <strong>{profile?.draftPosts ?? 0}</strong>
          <span>Entwuerfe offen</span>
        </article>
        <article>
          <strong>{profile?.publishedPosts ?? 0}</strong>
          <span>Veroeffentlicht</span>
        </article>
      </section>

      <section className="section-header">
        <div>
          <p className="eyebrow">Eigene Inhalte</p>
          <h2>Deine letzten Beitraege</h2>
        </div>
        <p className="section-copy">
          Diese Liste kommt jetzt bevorzugt aus dem Backend und faellt nur im Notfall auf Demo-Daten zurueck.
        </p>
      </section>

      {error ? <p className="form-feedback">{error}</p> : null}

      <section className="profile-posts">
        {postsToRender.map((post) => (
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
