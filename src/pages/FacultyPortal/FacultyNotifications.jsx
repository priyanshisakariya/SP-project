import { useEffect, useState } from "react";
import "./FacultyNotifications.css";

function FacultyNotifications() {
  const [notifications, setNotifications] = useState([]);

  const [title, setTitle] = useState("");
  const [message, setMessage] = useState("");

  const [loading, setLoading] = useState(true);
  const [sending, setSending] = useState(false);

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // ==========================================
  // FETCH SENT NOTIFICATIONS
  // ==========================================

  const fetchNotifications = () => {
    setLoading(true);
    setError("");

    fetch("http://localhost:8081/api/notifications")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch notifications");
        }

        return response.json();
      })
      .then((data) => {
        setNotifications(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching notifications:", error);

        setError("Unable to load notifications.");
        setLoading(false);
      });
  };

  // Fetch notifications when page loads
  useEffect(() => {
    fetchNotifications();
  }, []);


  // ==========================================
  // SEND / CREATE NOTIFICATION
  // ==========================================

  const handleSendNotification = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");

    // Validate title
    if (!title.trim()) {
      setError("Please enter notification title.");
      return;
    }

    // Validate message
    if (!message.trim()) {
      setError("Please enter notification message.");
      return;
    }

    setSending(true);

    try {
      const response = await fetch(
        "http://localhost:8081/api/notifications",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
          },

          body: JSON.stringify({
            title: title.trim(),
            message: message.trim(),
          }),
        }
      );

      if (!response.ok) {
        throw new Error("Failed to send notification");
      }

      const newNotification = await response.json();

      // Add newly created notification
      // to the beginning of the list
      setNotifications((previousNotifications) => [
        newNotification,
        ...previousNotifications,
      ]);

      // Clear form
      setTitle("");
      setMessage("");

      setSuccess("Notification sent successfully!");

    } catch (error) {
      console.error(
        "Error sending notification:",
        error
      );

      setError(
        "Unable to send notification. Please try again."
      );
    } finally {
      setSending(false);
    }
  };


  // ==========================================
  // JSX
  // ==========================================

  return (
    <div className="notification-page">

      {/* ======================================
          HEADER
      ====================================== */}

      <div className="notification-header">

        <h1>Notifications</h1>

        <p>
          Send important announcements and
          schedule-related information to students.
        </p>

      </div>


      {/* ======================================
          CREATE / SEND NOTIFICATION
      ====================================== */}

      <div className="create-notification-card">

        <h2>Send Notification</h2>

        <p>
          Share important information such as
          viva dates, submission deadlines,
          schedule changes, and other announcements.
        </p>


        {/* Success Message */}

        {success && (
          <div className="notification-success">
            {success}
          </div>
        )}


        {/* Error Message */}

        {error && (
          <div className="notification-error">
            {error}
          </div>
        )}


        <form onSubmit={handleSendNotification}>

          {/* Title */}

          <div className="form-group">

            <label htmlFor="notification-title">
              Notification Title
            </label>

            <input
              id="notification-title"
              type="text"
              placeholder="e.g. Viva Date Changed"
              value={title}
              onChange={(e) =>
                setTitle(e.target.value)
              }
            />

          </div>


          {/* Message */}

          <div className="form-group">

            <label htmlFor="notification-message">
              Message
            </label>

            <textarea
              id="notification-message"
              placeholder="Enter the information you want to send to students..."
              value={message}
              onChange={(e) =>
                setMessage(e.target.value)
              }
              rows="4"
            />

          </div>


          {/* Send Button */}

          <button
            type="submit"
            className="send-notification-btn"
            disabled={sending}
          >
            {sending
              ? "Sending..."
              : "Send Notification"}
          </button>

        </form>

      </div>


      {/* ======================================
          SENT NOTIFICATIONS
      ====================================== */}

      <div className="sent-notifications-section">

        <div className="section-header">

          <h2>Sent Notifications</h2>

          <p>
            Notifications you have sent to students.
          </p>

        </div>


        {/* Loading */}

        {loading && (
          <p className="notification-message">
            Loading notifications...
          </p>
        )}


        {/* No Notifications */}

        {!loading &&
          notifications.length === 0 && (
            <p className="notification-message">
              No notifications sent yet.
            </p>
          )}


        {/* Notification Cards */}

        {!loading &&
          notifications.map((item) => (

            <div
              key={item.id}
              className="notification-card"
            >

              <div className="notification-top">

                <div>

                  <h3>
                    {item.title}
                  </h3>

                  <small>
                    {item.date}
                  </small>

                </div>

              </div>


              <p>
                {item.message}
              </p>

            </div>

          ))}

      </div>

    </div>
  );
}

export default FacultyNotifications;