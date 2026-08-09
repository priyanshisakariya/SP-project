import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home/Home";
import About from "./pages/About/About";
import Services from "./pages/Services/Services";

// ================= STUDENT =================
import StudentRegister from "./pages/Student/StudentRegister";
import StudentLogin from "./pages/Student/StudentLogin";

import StudentPortal from "./pages/Portal/StudentPortal";
import Dashboard from "./pages/Portal/Dashboard";
import Profile from "./pages/Portal/Profile";
import SubmitProposal from "./pages/Portal/SubmitProposal";
import WeeklyProgress from "./pages/Portal/WeeklyProgress";
import FinalSubmission from "./pages/Portal/FinalSubmission";
import Marks from "./pages/Portal/Marks";
import Notifications from "./pages/Portal/Notifications";

// ================= ADMIN =================
import AdminLogin from "./pages/Admin/AdminLogin";
import AdminLayout from "./layouts/AdminLayout";
import AdminHome from "./pages/Admin/AdminHome";
import AssignedStudents from "./pages/Admin/AssignedStudents";
import AssignedFaculty from "./pages/Admin/AssignedFaculty";
import MentorAssignment from "./pages/Admin/MentorAssignment";
import FacultyStudentAllocation from "./pages/Admin/FacultyStudentAllocation";

// ================= FACULTY =================
import FacultyLogin from "./pages/Faculty/FacultyLogin";
import FacultyRegister from "./pages/Faculty/FacultyRegister";

import FacultyPortal from "./pages/FacultyPortal/FacultyPortal";
import FacultyDashboard from "./pages/FacultyPortal/FacultyDashboard";
import AssignedStudentToFaculty from "./pages/FacultyPortal/AssignedStudentToFaculty";
import FacultyCommentSection from "./pages/FacultyPortal/FacultyCommentSection";
import FacultyNotifications from "./pages/FacultyPortal/FacultyNotifications";
import GenerateMarksheet from "./pages/FacultyPortal/GenerateMarksheet";
import MarkManagement from "./pages/FacultyPortal/MarkManagement";
import Review from "./pages/FacultyPortal/Review";
import WeeklyReview from "./pages/FacultyPortal/WeeklyReview";

function App() {
  return (
    <BrowserRouter>

      <Routes>

        {/* ================= PUBLIC ================= */}

        <Route path="/" element={<Home />} />

        <Route path="/about" element={<About />} />

        <Route path="/services" element={<Services />} />


        {/* ================= STUDENT AUTH ================= */}

        <Route
          path="/student-register"
          element={<StudentRegister />}
        />

        <Route
          path="/student-login"
          element={<StudentLogin />}
        />

        <Route
          path="/register"
          element={<StudentRegister />}
        />


        {/* ================= STUDENT PORTAL ================= */}

        <Route
          path="/student-portal"
          element={<StudentPortal />}
        >

          <Route
            index
            element={<Dashboard />}
          />

          <Route
            path="profile"
            element={<Profile />}
          />

          <Route
            path="proposal"
            element={<SubmitProposal />}
          />

          <Route
            path="weekly-progress"
            element={<WeeklyProgress />}
          />

          <Route
            path="final-submission"
            element={<FinalSubmission />}
          />

          <Route
            path="marks"
            element={<Marks />}
          />

          <Route
            path="notification"
            element={<Notifications />}
          />

        </Route>


        {/* ================= ADMIN LOGIN ================= */}

        <Route
          path="/admin-login"
          element={<AdminLogin />}
        />


        {/* ================= ADMIN PORTAL ================= */}

        <Route
          path="/admin-dashboard"
          element={<AdminLayout />}
        >

          <Route
            index
            element={<AdminHome />}
          />

          <Route
            path="assigned-students"
            element={<AssignedStudents />}
          />

          <Route
            path="assigned-faculty"
            element={<AssignedFaculty />}
          />

          <Route
            path="mentor-assignment"
            element={<MentorAssignment />}
          />

          <Route
            path="notifications"
            element={<FacultyStudentAllocation />}
          />

        </Route>


        {/* ================= FACULTY AUTH ================= */}

        <Route
          path="/faculty-login"
          element={<FacultyLogin />}
        />

        <Route
          path="/faculty-register"
          element={<FacultyRegister />}
        />


        {/* ================= FACULTY PORTAL ================= */}

        <Route
          path="/faculty-portal"
          element={<FacultyPortal />}
        >

          {/* Default Faculty Dashboard */}
          <Route
            path="faculty-dashboard"
            element={<FacultyDashboard />}
          />

          {/* Assigned Students */}
          <Route
            path="assigned-students"
            element={<AssignedStudentToFaculty />}
          />

          {/* Comments */}
          <Route
            path="comments"
            element={<FacultyCommentSection />}
          />

          {/* Notifications */}
          <Route
            path="notifications"
            element={<FacultyNotifications />}
          />

          {/* Generate Marksheet */}
          <Route
            path="generate-marksheet"
            element={<GenerateMarksheet />}
          />

          {/* Mark Management */}
          <Route
            path="mark-management"
            element={<MarkManagement />}
          />

          {/* Review */}
          <Route
            path="review"
            element={<Review />}
          />

          {/* Weekly Review */}
          <Route
            path="weekly-review"
            element={<WeeklyReview />}
          />

        </Route>

      </Routes>

    </BrowserRouter>
  );
}

export default App;