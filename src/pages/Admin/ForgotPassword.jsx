import "./ForgotPassword.css";
import bg from "../../assets/background.jpg";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    newPassword: "",
    confirmPassword: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  // ==============================
  // HANDLE INPUT
  // ==============================
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    setError("");
    setMessage("");
  };

  // ==============================
  // RESET PASSWORD
  // ==============================
  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");
    setMessage("");

    const name = formData.name.trim();
    const newPassword = formData.newPassword;
    const confirmPassword = formData.confirmPassword;

    // ==============================
    // VALIDATION
    // ==============================

    if (!name) {
      setError("Please enter admin name.");
      return;
    }

    if (!newPassword) {
      setError("Please enter new password.");
      return;
    }

    if (newPassword.length < 6) {
      setError("Password must contain at least 6 characters.");
      return;
    }

    if (!confirmPassword) {
      setError("Please confirm your password.");
      return;
    }

    if (newPassword !== confirmPassword) {
      setError("New password and confirm password do not match.");
      return;
    }

    setLoading(true);

    try {
      console.log("Reset password request started.");

      const response = await fetch(
        "http://localhost:8080/api/admin/reset-password",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },

          body: JSON.stringify({
            name: name,
            newPassword: newPassword,
          }),
        }
      );

      console.log("Reset password status:", response.status);

      const contentType = response.headers.get("content-type");

      let data;

      if (
        contentType &&
        contentType.includes("application/json")
      ) {
        data = await response.json();
      } else {
        data = await response.text();
      }

      console.log("Reset password response:", data);

      // ==============================
      // SUCCESS
      // ==============================

      if (response.ok) {
        setMessage(
          typeof data === "string"
            ? data
            : data.message || "Password reset successfully."
        );

        // Clear form
        setFormData({
          name: "",
          newPassword: "",
          confirmPassword: "",
        });

        // Go back to login after 2 seconds
        setTimeout(() => {
          navigate("/admin-login");
        }, 2000);

        return;
      }

      // ==============================
      // ADMIN NOT FOUND
      // ==============================

      if (response.status === 404) {
        setError(
          typeof data === "string"
            ? data
            : data.message || "Admin not found."
        );

        return;
      }

      // ==============================
      // OTHER ERROR
      // ==============================

      setError(
        typeof data === "string"
          ? data
          : data.message || "Unable to reset password."
      );
    } catch (error) {
      console.error("Reset password error:", error);

      setError(
        "Unable to connect to server. Please make sure the backend is running on port 8080."
      );
    } finally {
      setLoading(false);
    }
  };

  // ==============================
  // BACK TO LOGIN
  // ==============================
  const handleBackToLogin = () => {
    navigate("/admin-login");
  };

  return (
    <div
      className="forgot-password-container"
      style={{
        backgroundImage: `url(${bg})`,
      }}
    >
      <div className="forgot-password-card">

        {/* HEADER */}

        <div className="forgot-password-header">

          <div className="lock-icon">
            🔐
          </div>

          <h2>Forgot Password?</h2>

          <p>
            Reset your admin account password
          </p>

        </div>

        {/* SUCCESS MESSAGE */}

        {message && (
          <div className="success-message">
            {message}
          </div>
        )}

        {/* ERROR MESSAGE */}

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        {/* FORM */}

        <form onSubmit={handleSubmit}>

          {/* ADMIN NAME */}

          <label htmlFor="name">
            Admin Name
          </label>

          <input
            id="name"
            type="text"
            name="name"
            placeholder="Enter your admin name"
            value={formData.name}
            onChange={handleChange}
            autoComplete="username"
            disabled={loading}
            required
          />

          {/* NEW PASSWORD */}

          <label htmlFor="newPassword">
            New Password
          </label>

          <input
            id="newPassword"
            type="password"
            name="newPassword"
            placeholder="Enter new password"
            value={formData.newPassword}
            onChange={handleChange}
            autoComplete="new-password"
            disabled={loading}
            required
          />

          {/* PASSWORD INFO */}

          <p className="password-hint">
            Password must contain at least 6 characters.
          </p>

          {/* CONFIRM PASSWORD */}

          <label htmlFor="confirmPassword">
            Confirm Password
          </label>

          <input
            id="confirmPassword"
            type="password"
            name="confirmPassword"
            placeholder="Confirm new password"
            value={formData.confirmPassword}
            onChange={handleChange}
            autoComplete="new-password"
            disabled={loading}
            required
          />

          {/* RESET BUTTON */}

          <button
            type="submit"
            className="reset-button"
            disabled={loading}
          >
            {loading
              ? "Resetting Password..."
              : "Reset Password"}
          </button>

        </form>

        {/* BACK TO LOGIN */}

        <div className="back-to-login">
          <button
            type="button"
            onClick={handleBackToLogin}
            disabled={loading}
          >
            ← Back to Login
          </button>
        </div>

      </div>
    </div>
  );
};

export default ForgotPassword;