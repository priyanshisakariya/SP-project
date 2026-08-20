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

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (
      formData.name === "admin" &&
      formData.password === "admin123"
    ) {
      navigate("/admin-dashboard");
    } else {
      alert("Invalid Username or Password");
    }
  };

  const handleForgotPassword = () => {
    alert("Redirect to Forgot Password page");
  };

  return (
    <div
      className="admin-login-container"
      style={{ backgroundImage: `url(${bg})` }}
    >
      <div className="admin-login-card">
        <h2>Admin Login</h2>
        <p className="subtitle">
          Sign in to access the admin dashboard
        </p>

        <form onSubmit={handleSubmit}>
          <label>Name</label>
          <input
            type="text"
            name="name"
            placeholder="Enter your name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <label>Password</label>
          <input
            type="password"
            name="password"
            placeholder="Enter your password"
            value={formData.password}
            onChange={handleChange}
            required
          />

          <div className="forgot-password">
            <span onClick={handleForgotPassword}>
              Forgot Password?
            </span>
          </div>

          <button type="submit">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default AdminLogin;