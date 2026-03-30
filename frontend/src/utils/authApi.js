async function readResponsePayload(response) {
  const contentType = response.headers.get("content-type") || "";
  const rawText = await response.text();

  if (!rawText) {
    return {};
  }

  if (contentType.includes("application/json")) {
    try {
      return JSON.parse(rawText);
    } catch {
      return { message: rawText };
    }
  }

  return { message: rawText };
}

async function handleJsonResponse(response) {
  const payload = await readResponsePayload(response);

  if (!response.ok) {
    const message =
      payload.message ||
      payload.detail ||
      payload.error ||
      payload.title ||
      `Die Anfrage ist mit Status ${response.status} fehlgeschlagen.`;
    const error = new Error(message);
    error.status = response.status;
    throw error;
  }

  return payload;
}

async function performRequest(url, options = {}) {
  try {
    const response = await fetch(url, options);
    return await handleJsonResponse(response);
  } catch (error) {
    if (error instanceof TypeError) {
      throw new Error("Das Backend ist gerade nicht erreichbar. Bitte pruefe, ob `./gradlew bootRun` laeuft.");
    }

    throw error;
  }
}

export async function registerUser(formData) {
  // Registrierung legt einen neuen Benutzer im Backend an.
  return performRequest("/api/auth/register", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
  });
}

export async function loginUser(formData) {
  // Login prueft, ob E-Mail und Passwort zum gespeicherten Konto passen.
  return performRequest("/api/auth/login", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
  });
}

export async function loadProfile(username) {
  // Das Profil wird aktuell direkt ueber den Benutzernamen geladen.
  return performRequest(`/api/profiles/${username}`, {
    credentials: "include"
  });
}

export async function loadCurrentUser() {
  return performRequest("/api/auth/me", {
    credentials: "include"
  });
}

export async function logoutUser() {
  return performRequest("/api/auth/logout", {
    method: "POST",
    credentials: "include"
  });
}

export async function loadOwnProfile() {
  return performRequest("/api/profiles/me", {
    credentials: "include"
  });
}

export async function loadPosts() {
  return performRequest("/api/posts", {
    credentials: "include"
  });
}

export async function loadPost(slug) {
  return performRequest(`/api/posts/${slug}`, {
    credentials: "include"
  });
}

export async function createPost(formData) {
  return performRequest("/api/posts", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
  });
}

export async function createComment(slug, formData) {
  return performRequest(`/api/posts/${slug}/comments`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
  });
}
