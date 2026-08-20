import { useState } from "react";
import { Outlet } from "react-router-dom";

import FacultySidebar from "../../components/FacultySidebar/FacultySidebar";
import FacultyTopbar from "../../components/FacultyTopbar/FacultyTopbar";

import "./FacultyPortal.css";

function FacultyPortal() {
const [sidebarOpen, setSidebarOpen] = useState(true);

  return (
    <>
      <FacultySidebar sidebarOpen={sidebarOpen} />

      <div className={`main ${sidebarOpen ? "" : "expanded"}`}>
        
        <FacultyTopbar
          sidebarOpen={sidebarOpen}
          setSidebarOpen={setSidebarOpen}
        />

        <div className="content">
          <Outlet />
        </div>

      </div>
    </>
  );
}

export default FacultyPortal;
