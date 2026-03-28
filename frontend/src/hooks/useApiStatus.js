import { useEffect, useState } from "react";

export function useApiStatus() {
  const [status, setStatus] = useState("Backend wird geprueft...");

  useEffect(() => {
    let active = true;

    fetch("/api/health")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Request fehlgeschlagen");
        }
        return response.json();
      })
      .then((data) => {
        if (active) {
          setStatus(`${data.status}: ${data.message}`);
        }
      })
      .catch(() => {
        if (active) {
          setStatus("Backend noch nicht erreichbar. Starte den Server auf Port 8080.");
        }
      });

    return () => {
      active = false;
    };
  }, []);

  return status;
}
