import { navigate } from "../utils/navigation";

export function LoginPage() {
  return (
    <section className="auth-layout">
      <div className="auth-copy">
        <p className="eyebrow">Willkommen zurueck</p>
        <h1>Logge dich ein und verwalte deine Inhalte.</h1>
        <p>
          Diese Seite ist schon bereit fuer spaetere Backend-Anbindung an JWT, Session
          oder Cookie-basierte Authentifizierung.
        </p>
      </div>

      <form className="auth-card">
        <label>
          E-Mail
          <input type="email" placeholder="dein.name@example.com" />
        </label>
        <label>
          Passwort
          <input type="password" placeholder="Dein Passwort" />
        </label>
        <button type="button" className="primary-button full-width">
          Einloggen
        </button>
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
