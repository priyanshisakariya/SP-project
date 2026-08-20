import "./AdminLogin.css";
import bg from "../../assets/background.jpg";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const AdminLogin = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // ==============================
  // HANDLE INPUT CHANGE
  // ==============================
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    setError("");
  };

  // ==============================
  // LOGIN
  // ==============================
  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");

    // Basic validation
    if (!formData.name.trim()) {
      setError("Please enter admin name.");
      return;
    }

    if (!formData.password.trim()) {
      setError("Please enter password.");
      return;
    }

    setLoading(true);

    try {
      console.log("Sending login request...");
      console.log("Name:", formData.name);

      const response = await fetch(
        "http://localhost:8080/api/admin/login",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },

          body: JSON.stringify({
            name: formData.name.trim(),
            password: formData.password,
          }),
        }
      );

      console.log("Login status:", response.status);

      // ==============================
      // READ RESPONSE
      // ==============================
      const contentType = response.headers.get("content-type");

      let data;

      if (contentType && contentType.includes("application/json")) {
        data = await response.json();
      } else {
        data = await response.text();
      }

      console.log("Login response:", data);

      // ==============================
      // SUCCESS
      // ==============================
      if (response.ok) {
        /*
          Backend currently returns:

          {
            "message": "Login successful",
            "name": "admin"
          }
        */

        // Save admin information
        const adminData = {
          name: data.name || formData.name,
        };

        localStorage.setItem(
          "admin",
          JSON.stringify(adminData)
        );

        // If backend later returns token
        if (data.token) {
          localStorage.setItem(
            "adminToken",
            data.token
          );
        }

        console.log("Admin login successful.");

        // Go to admin dashboard
        navigate("/admin-dashboard");

        return;
      }

      // ==============================
      // INVALID LOGIN
      // ==============================
      if (response.status === 401) {
        setError(
          typeof data === "string"
            ? data
            : data.message || "Invalid username or password"
        );

        return;
      }

      // ==============================
      // OTHER BACKEND ERRORS
      // ==============================
      setError(
        typeof data === "string"
          ? data
          : data.message || "Login failed. Please try again."
      );

    } catch (error) {
      console.error("LOGIN ERROR:", error);

      /*
        This catch is only for real connection/network errors,
        such as:
        - Backend stopped
        - Wrong port
        - CORS/network failure
      */

      setError(
        "Unable to connect to server. Please make sure the backend is running on port 8080."
      );
    } finally {
      setLoading(false);
    }
  };

  // ==============================
  // FORGOT PASSWORD
  // ==============================
  const handleForgotPassword = () => {
    navigate("/forgot-password");
  };

  // ==============================
  // UI
  // ==============================
  return (
    <div
      className="login-container"
      style={{
        backgroundImage: `url(${bg})`,
      }}
    >
      <div className="login-card">

        <h2>Admin Login</h2>

        <p className="subtitle">
          Sign in to access the admin dashboard
        </p>

        {/* ERROR MESSAGE */}
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>

          {/* NAME */}
          <label htmlFor="name">
            Name
          </label>

          <input
            id="name"
            type="text"
            name="name"
            placeholder="Enter your name"
            value={formData.name}
            onChange={handleChange}
            autoComplete="username"
            disabled={loading}
            required
          />

          {/* PASSWORD */}
          <label htmlFor="password">
            Password
          </label>

          <input
            id="password"
            type="password"
            name="password"
            placeholder="Enter your password"
            value={formData.password}
            onChange={handleChange}
            autoComplete="current-password"
            disabled={loading}
            required
          />

          {/* FORGOT PASSWORD */}
          <div className="forgot-password">
            <span onClick={handleForgotPassword}>
              Forgot Password?
            </span>
          </div>

          {/* LOGIN BUTTON */}
          <button
            type="submit"
            disabled={loading}
          >
            {loading ? "Logging in..." : "Login"}
          </button>

        </form>
      </div>
    </div>
  );
};

export default AdminLogin;