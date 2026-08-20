import { NavLink } from "react-router-dom";
import "./FacultySidebar.css";
import logo from "../../assets/logo1.jpeg";

import {
  FaHome,
  FaUsers,
  FaFileAlt,
  FaCalendarAlt,
  FaComments,
  FaAward,
  FaChartBar,
  FaBell,
  FaSignOutAlt,
} from "react-icons/fa";

function FacultySidebar({ sidebarOpen }) {
  return (
    <aside className={`sidebar ${sidebarOpen ? "open" : "close"}`}>

      {/* Logo */}
      <div className="sidebar-header">
        <img
          src={logo}
          alt="CampusProject Logo"
          className="sidebar-logo"
        />

        <h2 className="sidebar-title">
          CampusProject
        </h2>
      </div>

      {/* Menu */}
      <nav className="sidebar-menu">

        <NavLink
          to="/student-portal"
          end
          className={({ isActive }) =>
            isActive ? "active" : ""
          }
        >
          <FaHome />
          <span>Dashboard</span>
        </NavLink>

        <NavLink to="/faculty-portal/assigned-students">
          <FaUsers />
          <span>Assigned Students</span>
        </NavLink>

        <NavLink to="/faculty-portal/review">
          <FaFileAlt />
          <span>Review Proposal</span>
        </NavLink>

        <NavLink to="/faculty-portal/weekly-review">
          <FaCalendarAlt />
          <span>Weekly Review</span>
        </NavLink>

        <NavLink to="/faculty-portal/comments">
          <FaComments />
          <span>Comments</span>
        </NavLink>

        <NavLink to="/faculty-portal/mark-management">
          <FaAward />
          <span>Mark Management</span>
        </NavLink>

        <NavLink to="/faculty-portal/generate-marksheet">
          <FaChartBar />
          <span>Generate Marksheet</span>
        </NavLink>

        <NavLink to="/faculty-portal/notifications">
          <FaBell />
          <span>Notifications</span>
        </NavLink>

      </nav>

      {/* Logout */}
      <div className="logout-area">
        <NavLink to="/">
          <FaSignOutAlt />
          <span>Logout</span>
        </NavLink>
      </div>

    </aside>
  );
}

export default FacultySidebar;
