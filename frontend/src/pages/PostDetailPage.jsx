import { useEffect, useState } from "react";

import { createComment, loadPost } from "../utils/authApi";
import { navigate } from "../utils/navigation";

export function PostDetailPage({ slug, currentUser }) {
  const [post, setPost] = useState(null);
  const [comment, setComment] = useState("");
  const [feedback, setFeedback] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    let active = true;

    setPost(null);
    setError("");
    setFeedback("");

    loadPost(slug)
      .then((data) => {
        if (active) {
          setPost({
            ...data,
            title: data?.title ?? "Unbenannter Beitrag",
            excerpt: data?.excerpt ?? "",
            content: typeof data?.content === "string" ? data.content : "",
            author: data?.author ?? "Unbekannt",
            publishedAt: data?.publishedAt ?? null,
            comments: Array.isArray(data?.comments) ? data.comments : []
          });
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
  }, [slug]);

  async function handleSubmit(event) {
    event.preventDefault();
    setFeedback("");
    setError("");
    setIsSubmitting(true);

    try {
      const createdComment = await createComment(slug, { content: comment });
      setPost((currentPost) => {
        if (!currentPost) {
          return currentPost;
        }

        return {
          ...currentPost,
          comments: [...currentPost.comments, createdComment]
        };
      });
      setComment("");
      setFeedback("Kommentar gespeichert.");
    } catch (submitError) {
      setError(submitError.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  if (error && !post) {
    return (
      <section className="auth-layout">
        <div className="auth-copy">
          <p className="eyebrow">Beitrag</p>
          <h1>Dieser Beitrag konnte nicht geladen werden.</h1>
          <p>{error}</p>
        </div>
        <div className="auth-card">
          <button className="secondary-button full-width" onClick={() => navigate("/")}>
            Zur Startseite
          </button>
        </div>
      </section>
    );
  }

  if (!post) {
    return (
      <section className="auth-layout">
        <div className="auth-copy">
          <p className="eyebrow">Beitrag</p>
          <h1>Beitrag wird geladen.</h1>
          <p>Wir holen gerade Inhalt und Kommentare aus dem Backend.</p>
        </div>
      </section>
    );
  }

  const comments = Array.isArray(post.comments) ? post.comments : [];
  const paragraphs = typeof post.content === "string" && post.content.trim()
    ? post.content.split(/\n+/)
    : ["Zu diesem Beitrag liegt aktuell noch kein Inhalt vor."];

  return (
    <div className="page-stack">
      <article className="post-detail-shell">
        <div className="post-detail-header">
          <p className="eyebrow">Artikel</p>
          <h1>{post.title}</h1>
          <p className="lead">{post.excerpt}</p>
          <div className="post-footer">
            <span>{post.author}</span>
            <span>{post.publishedAt || "Noch nicht veroeffentlicht"}</span>
          </div>
        </div>

        <div className="post-detail-content">
          {paragraphs.map((paragraph, index) => (
            <p key={`${post.id}-${index}`}>{paragraph}</p>
          ))}
        </div>
      </article>

      <section className="section-header">
        <div>
          <p className="eyebrow">Kommentare</p>
          <h2>Rueckmeldungen zu diesem Beitrag</h2>
        </div>
        <p className="section-copy">
          Kommentare werden direkt am Beitrag gespeichert und beim Laden mit ausgeliefert.
        </p>
      </section>

      <section className="comments-layout">
        <div className="comments-list">
          {comments.length ? (
            comments.map((entry) => (
              <article key={entry.id} className="comment-card">
                <div className="comment-meta">
                  <strong>{entry.author}</strong>
                  <span>{entry.createdAt}</span>
                </div>
                <p>{entry.content}</p>
              </article>
            ))
          ) : (
            <article className="comment-card empty-state-card">
              <strong>Noch keine Kommentare</strong>
              <p>Sei die erste Person, die auf diesen Beitrag reagiert.</p>
            </article>
          )}
        </div>

        <form className="auth-card comment-form" onSubmit={handleSubmit}>
          <p className="eyebrow">Kommentar schreiben</p>
          <label>
            Dein Kommentar
            <textarea
              name="content"
              placeholder={
                currentUser
                  ? "Was moechtest du zu diesem Beitrag sagen?"
                  : "Bitte logge dich ein, um einen Kommentar zu schreiben."
              }
              value={comment}
              onChange={(event) => setComment(event.target.value)}
              disabled={!currentUser || isSubmitting}
            />
          </label>
          {currentUser ? (
            <button type="submit" className="primary-button full-width" disabled={isSubmitting}>
              {isSubmitting ? "Kommentar wird gespeichert..." : "Kommentar absenden"}
            </button>
          ) : (
            <button
              type="button"
              className="secondary-button full-width"
              onClick={() => navigate("/login")}
            >
              Zum Login
            </button>
          )}
          {feedback ? <p className="form-feedback">{feedback}</p> : null}
          {error ? <p className="form-feedback">{error}</p> : null}
        </form>
      </section>
    </div>
  );
}
