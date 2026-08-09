import "./FacultyTopbar.css";
import logo from "../../assets/logo1.jpeg";

import {
  FaBell,
  FaBars,
} from "react-icons/fa";

function FacultyTopbar({ sidebarOpen, setSidebarOpen }) {

  return (
    <header className="topbar">

      {/* LEFT SECTION */}
      <div className="top-left">

        {/* BURGER MENU */}
        <button
          className="menu-btn"
          onClick={() => setSidebarOpen(!sidebarOpen)}
          aria-label="Toggle Sidebar"
        >
          <FaBars />
        </button>

        <h2 className="portal-title">
          Faculty Portal
        </h2>

      </div>


      {/* RIGHT SECTION */}
      <div className="user-section">

        {/* Notification */}
        <div className="notification">
          <FaBell />
        </div>


        {/* Profile */}
        <div className="profile">

          <img
            src={logo}
            alt="Profile"
            className="profile-img"
          />

          <div className="profile-info">

            <h4>
              Priyanshi Sakariya
            </h4>

            <p>
              Faculty
            </p>

          </div>

        </div>

      </div>

    </header>
  );
}

export default FacultyTopbar;
