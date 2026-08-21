import { useState } from "react";
import "./FacultyLogin.css";
import { useNavigate } from "react-router-dom";

function FacultyLogin() {

  const navigate = useNavigate();

  const [faculty, setFaculty] = useState({
    phoneNumber: "",
    password: "",
  });

  const handleChange = (e) => {
    setFaculty({
      ...faculty,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    console.log("Login data:", faculty);

    try {

      const response = await fetch(
        "http://localhost:8081/api/faculty/login",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
          },

          body: JSON.stringify(faculty),
        }
      );

      if (response.ok) {

        const data = await response.json();

        console.log("Login successful:", data);

        alert("Login Successful!");

        // Store faculty information for later use
        localStorage.setItem(
          "faculty",
          JSON.stringify(data)
        );

        navigate("/faculty-portal");

      } else {

        const error = await response.text();

        console.error("Login failed:", error);

        alert(error);
      }

    } catch (error) {

      console.error("Server Error:", error);

      alert("Unable to connect to server!");
    }
  };

  return (

    <div className="login-container">

      <h2>Faculty Login</h2>

      <form onSubmit={handleSubmit}>

        <label>Phone Number</label>

        <input
          type="text"
          name="phoneNumber"
          placeholder="Enter Phone Number"
          value={faculty.phoneNumber}
          onChange={handleChange}
          maxLength="10"
          required
        />

        <label>Password</label>

        <input
          type="password"
          name="password"
          placeholder="Enter Password"
          value={faculty.password}
          onChange={handleChange}
          required
        />

        <button type="submit">
          Login
        </button>

        <p className="register-link">

          Don't Have an Account?

          <span
            onClick={() => navigate("/faculty-register")}
          >
            {" "}Register
          </span>

        </p>

      </form>

    </div>
  );
}

export default FacultyLogin;