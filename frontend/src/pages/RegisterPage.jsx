import { useState } from "react";

import { storeUser } from "../hooks/useStoredUser";
import { registerUser } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function RegisterPage() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: ""
  });
  const [feedback, setFeedback] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  function handleChange(event) {
    const { name, value } = event.target;
    setFormData((currentData) => ({
      ...currentData,
      [name]: value
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setIsSubmitting(true);
    setFeedback("");

    try {
      // Die API legt den Benutzer in der Datenbank an und gibt ihn direkt wieder zurueck.
      const response = await registerUser(formData);
      storeUser(response.user);
      setFeedback(response.message);
      navigate("/profile");
    } catch (error) {
      setFeedback(error.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <section className="auth-layout">
      <div className="auth-copy">
        <p className="eyebrow">Neues Konto</p>
        <h1>Erstelle dein Profil fuer deinen Blog.</h1>
        <p>
          Diese Seite sendet jetzt bereits echte Registrierungsdaten an das Backend
          und speichert den angemeldeten Benutzer lokal im Browser.
        </p>
      </div>

      <form className="auth-card" onSubmit={handleSubmit}>
        <label>
          Benutzername
          <input
            name="username"
            type="text"
            placeholder="matthias"
            value={formData.username}
            onChange={handleChange}
            required
            minLength={3}
          />
        </label>
        <label>
          E-Mail
          <input
            name="email"
            type="email"
            placeholder="dein.name@example.com"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Passwort
          <input
            name="password"
            type="password"
            placeholder="Mindestens 8 Zeichen"
            value={formData.password}
            onChange={handleChange}
            required
            minLength={8}
          />
        </label>
        <button type="submit" className="primary-button full-width" disabled={isSubmitting}>
          {isSubmitting ? "Konto wird angelegt..." : "Konto anlegen"}
        </button>
        {feedback ? <p className="form-feedback">{feedback}</p> : null}
        <p className="form-footnote">
          Bereits registriert?{" "}
          <button type="button" className="text-button" onClick={() => navigate("/login")}>
            Zum Login
          </button>
        </p>
      </form>
    </section>
  );
}
