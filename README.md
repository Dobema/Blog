# Blog Monorepo

Dieses Projekt ist als Fullstack-Grundgeruest angelegt:

- `frontend/` enthaelt ein React-Frontend mit Vite.
- `backend/` enthaelt ein Spring-Boot-Backend in Kotlin.

## Voraussetzungen

- Node.js 20+
- npm 10+
- Java 21
- Gradle 8.x oder eine IDE mit Gradle-Support

## Frontend starten

```bash
cd frontend
npm install
npm run dev
```

Das Frontend laeuft dann standardmaessig unter `http://localhost:5173`.

## Backend starten

```bash
cd backend
source ./use-java21.sh
./gradlew bootRun
```

Das Backend laeuft dann unter `http://localhost:8080`.

## API testen

Der Endpunkt `GET /api/health` liefert eine kleine Statusantwort zurueck.

Weitere bereits vorbereitete Endpunkte:

- `GET /api/posts` liefert veroeffentlichte Beitraege aus der Datenbank.
- `GET /api/posts/{slug}` liefert einen einzelnen Beitrag.
- `GET /api/profiles/matthias` liefert ein Beispielprofil mit eigenen Beitraegen.

## Datenbank

Das Backend ist jetzt mit einer echten Datenbankanbindung vorbereitet:

- Es nutzt Spring Data JPA fuer Entities, Repositories und Datenzugriff.
- Fuer die lokale Entwicklung ist H2 als dateibasierte Datenbank aktiviert.
- Die Daten werden lokal unter `backend/data/` gespeichert, sobald du das Backend startest.
- Die H2-Konsole ist unter `http://localhost:8080/h2-console` erreichbar.

Die aktuelle JDBC-URL lautet:

```text
jdbc:h2:file:./data/blogdb
```

Fuer den lokalen Zugriff in der H2-Konsole:

- JDBC URL: `jdbc:h2:file:./data/blogdb`
- User Name: `sa`
- Password: leer lassen

Beim ersten Start werden automatisch Beispiel-Daten fuer einen Benutzer und einige Blogbeitraege angelegt.

## Hinweise

- Vite leitet `/api` im Entwicklungsmodus an das Backend auf Port `8080` weiter.
- Im Projekt ist jetzt ein Gradle Wrapper enthalten. Dadurch brauchst du kein global installiertes `gradle`.
- Fuer dieses Projekt ist ein lokales Java-21-JDK unter `.jdks/java-21` eingerichtet.
- Vor Backend-Befehlen kannst du im Ordner `backend/` einfach `source ./use-java21.sh` ausfuehren.
- Die H2-Datenbank ist bewusst nur der Entwicklungsstart. Spaeter koennen wir dieselbe Struktur auf PostgreSQL umstellen.
- Deine `~/.bash_profile` ist auf diesem Rechner `root`-besessen. Falls du Java 21 dauerhaft in jeder Bash-Session setzen willst, kannst du selbst einmalig diesen Befehl ausfuehren:

```bash
sudo sh -c 'printf "\n# >>> blog-java-21 >>>\nexport JAVA_HOME=\"/Users/matthias/Documents/Programme/Blog/.jdks/java-21/Contents/Home\"\nexport PATH=\"\$JAVA_HOME/bin:\$PATH\"\n# <<< blog-java-21 <<<\n" >> /Users/matthias/.bash_profile'
```

- Ich habe mich fuer Kotlin entschieden, weil es sehr gut mit Spring Boot zusammenarbeitet. Wenn du lieber Java moechtest, kann ich dir das Backend auch direkt auf Java umstellen.
