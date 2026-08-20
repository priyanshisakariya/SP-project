import { useState } from "react";
import "./FacultyRegister.css";
import { useNavigate } from "react-router-dom";

function FacultyRegister() {
  const navigate = useNavigate();

  const [faculty, setFaculty] = useState({
    facultyName: "",
    phoneNumber: "",
    department: "",
    designation: "",
    //password:"",
  });

   const handleChange = (e) => {
    setFaculty({
      ...faculty,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    console.log("Sending faculty data:", faculty);

    try {

      const response = await fetch(
        "http://localhost:8081/api/faculty/register",
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

        console.log("Registration successful:", data);

        alert("Registration Successful!");

        navigate("/faculty-login");

      } else {

        const errorMessage = await response.text();

        console.error("Registration failed:", errorMessage);

        alert(errorMessage);
      }

    } catch (error) {

      console.error("Server Error:", error);

      alert("Unable to connect to server!");
    }
  };

        


  return (
    <div className="register-container">
      <h2>Faculty Registration</h2>

      <form onSubmit={handleSubmit}>
        <label>Name</label>
        <input
          type="text"
          name="facultyName"
          placeholder="Enter Name"
          value={faculty.facultyName}
          onChange={handleChange}
          required
        />


        <label>Phone Number</label>
        <input
          type="text"
          name="phoneNumber"
          placeholder="Enter Phone Number"
          value={faculty.phoneNumber}
          onChange={handleChange}
          required
        />

        <label>Department</label>
        <select
          name="department"
          value={faculty.department}
          onChange={handleChange}
          required
        >
          <option value="">Select Department</option>
          <option value="BCA">BCA</option>
          <option value="MCA">MCA</option>
          <option value="Computer Science">Computer Science</option>
          <option value="IT">IT</option>
        </select>

        <label>Designation</label>
        <select
          name="designation"
          value={faculty.designation}
          onChange={handleChange}
          required
        >
          <option value="">Select Designation</option>
          <option value="Professor">Professor</option>
          <option value="Assistant Professor">
            Assistant Professor
          </option>
        </select>

        {/* <label>Password</label>
        <input
          type="password"
        name="password"
        placeholder="Enter Password"
        value={faculty.password}
        onChange={handleChange}
        required
        /> */}

        <button type="submit">
          Submit
        </button>

        <p className="login-link">
          Already Have an Account?
          <span onClick={() => navigate("/faculty-login")}>
            {" "}Login
          </span>
        </p>
      </form>
    </div>
  );
}

export default FacultyRegister;