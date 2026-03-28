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

## Hinweise

- Vite leitet `/api` im Entwicklungsmodus an das Backend auf Port `8080` weiter.
- Im Projekt ist jetzt ein Gradle Wrapper enthalten. Dadurch brauchst du kein global installiertes `gradle`.
- Fuer dieses Projekt ist ein lokales Java-21-JDK unter `.jdks/java-21` eingerichtet.
- Vor Backend-Befehlen kannst du im Ordner `backend/` einfach `source ./use-java21.sh` ausfuehren.
- Deine `~/.bash_profile` ist auf diesem Rechner `root`-besessen. Falls du Java 21 dauerhaft in jeder Bash-Session setzen willst, kannst du selbst einmalig diesen Befehl ausfuehren:

```bash
sudo sh -c 'printf "\n# >>> blog-java-21 >>>\nexport JAVA_HOME=\"/Users/matthias/Documents/Programme/Blog/.jdks/java-21/Contents/Home\"\nexport PATH=\"\$JAVA_HOME/bin:\$PATH\"\n# <<< blog-java-21 <<<\n" >> /Users/matthias/.bash_profile'
```

- Ich habe mich fuer Kotlin entschieden, weil es sehr gut mit Spring Boot zusammenarbeitet. Wenn du lieber Java moechtest, kann ich dir das Backend auch direkt auf Java umstellen.
