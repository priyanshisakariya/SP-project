import { useState } from "react";
import { Outlet } from "react-router-dom";

import FacultySidebar from "../../components/FacultySidebar/FacultySidebar";
import FacultyTopbar from "../../components/FacultyTopbar/FacultyTopbar";

import "./FacultyPortal.css";

function FacultyPortal() {

  const [sidebarOpen, setSidebarOpen] = useState(true);

  return (
    <div className="portal">

      {/* FACULTY SIDEBAR */}
      <FacultySidebar
        sidebarOpen={sidebarOpen}
      />


      {/* FACULTY MAIN AREA */}
      <div className="main">

        {/* TOPBAR */}
        <FacultyTopbar
          sidebarOpen={sidebarOpen}
          setSidebarOpen={setSidebarOpen}
        />
         {/* <FacultyTopbar
  onMenuClick={toggleSidebar} 
/> */}


        {/* PAGE CONTENT */}
        <main className="content">
          <Outlet />
        </main>

      </div>

    </div>
  );
}

export default FacultyPortal;
