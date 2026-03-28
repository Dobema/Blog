import { useState } from "react";

import { storeUser } from "../hooks/useStoredUser";
import { loginUser } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function LoginPage() {
  const [formData, setFormData] = useState({
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
      const response = await loginUser(formData);
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
        <p className="eyebrow">Willkommen zurueck</p>
        <h1>Logge dich ein und verwalte deine Inhalte.</h1>
        <p>
          Diese Seite verwendet jetzt bereits die echten Backend-Endpunkte fuer den Login.
        </p>
      </div>

      <form className="auth-card" onSubmit={handleSubmit}>
        <label>
          E-Mail
          <input
            name="email"
            type="email"
            placeholder="dein.name@example.com"
            value={formData.email}
            onChange={handleChange}
          />
        </label>
        <label>
          Passwort
          <input
            name="password"
            type="password"
            placeholder="Dein Passwort"
            value={formData.password}
            onChange={handleChange}
          />
        </label>
        <button type="submit" className="primary-button full-width" disabled={isSubmitting}>
          {isSubmitting ? "Login laeuft..." : "Einloggen"}
        </button>
        {feedback ? <p className="form-feedback">{feedback}</p> : null}
        <p className="form-footnote">
          Noch kein Konto?{" "}
          <button type="button" className="text-button" onClick={() => navigate("/register")}>
            Hier registrieren
          </button>
        </p>
      </form>
    </section>
  );
}
