import { navigate } from "../utils/navigation";

export function RegisterPage() {
  return (
    <section className="auth-layout">
      <div className="auth-copy">
        <p className="eyebrow">Neues Konto</p>
        <h1>Erstelle dein Profil fuer deinen Blog.</h1>
        <p>
          Die Registrierung ist als eigener Einstieg vorbereitet und kann spaeter um
          Validierung, Passwortregeln und E-Mail-Bestaetigung erweitert werden.
        </p>
      </div>

      <form className="auth-card">
        <label>
          Benutzername
          <input type="text" placeholder="matthias" />
        </label>
        <label>
          E-Mail
          <input type="email" placeholder="dein.name@example.com" />
        </label>
        <label>
          Passwort
          <input type="password" placeholder="Mindestens 8 Zeichen" />
        </label>
        <button type="button" className="primary-button full-width">
          Konto anlegen
        </button>
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
