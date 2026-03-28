import { useState } from "react";

import { createPost } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function CreatePostPage({ currentUser }) {
  const [formData, setFormData] = useState({
    title: "",
    excerpt: "",
    content: "",
    publish: true
  });
  const [feedback, setFeedback] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  function handleChange(event) {
    const { name, type, checked, value } = event.target;
    setFormData((currentData) => ({
      ...currentData,
      [name]: type === "checkbox" ? checked : value
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setFeedback("");
    setIsSubmitting(true);

    try {
      const response = await createPost(formData);
      setFeedback(`Beitrag gespeichert: ${response.title}`);
      navigate("/");
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  if (!currentUser) {
    return (
      <section className="auth-layout">
        <div className="auth-copy">
          <p className="eyebrow">Schreiben</p>
          <h1>Bitte zuerst einloggen.</h1>
          <p>Neue Beitraege koennen nur mit einer aktiven Session gespeichert werden.</p>
        </div>
        <div className="auth-card">
          <button className="primary-button full-width" onClick={() => navigate("/login")}>
            Zum Login
          </button>
        </div>
      </section>
    );
  }

  return (
    <section className="auth-layout create-post-layout">
      <div className="auth-copy">
        <p className="eyebrow">Neuer Beitrag</p>
        <h1>Schreibe deinen naechsten Artikel.</h1>
        <p>
          Dieses Formular speichert neue Posts direkt in der Datenbank. Wenn du
          "Sofort veroeffentlichen" aktiviert laesst, erscheint der Beitrag danach direkt
          auf der Startseite.
        </p>
      </div>

      <form className="auth-card" onSubmit={handleSubmit}>
        <label>
          Titel
          <input
            name="title"
            type="text"
            placeholder="Mein naechster Blogartikel"
            value={formData.title}
            onChange={handleChange}
          />
        </label>
        <label>
          Auszug
          <input
            name="excerpt"
            type="text"
            placeholder="Kurze Zusammenfassung des Beitrags"
            value={formData.excerpt}
            onChange={handleChange}
          />
        </label>
        <label>
          Inhalt
          <textarea
            name="content"
            className="content-textarea"
            placeholder="Hier schreibst du den eigentlichen Beitrag..."
            value={formData.content}
            onChange={handleChange}
          />
        </label>
        <label className="checkbox-row">
          <input
            name="publish"
            type="checkbox"
            checked={formData.publish}
            onChange={handleChange}
          />
          Sofort veroeffentlichen
        </label>
        <button type="submit" className="primary-button full-width" disabled={isSubmitting}>
          {isSubmitting ? "Beitrag wird gespeichert..." : "Beitrag speichern"}
        </button>
        {feedback ? <p className="form-feedback">{feedback}</p> : null}
      </form>
    </section>
  );
}
