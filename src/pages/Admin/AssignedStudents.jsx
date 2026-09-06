import "./AssignedStudents.css";
import { FaSearch, FaEye } from "react-icons/fa";

const students = [
  {
    id: 1,
    name: "Priyanshi",
    enrollment: "22CE001",
    project: "Smart Attendance",
    guide: "Dr. Patel",
    status: "Active",
    progress: 80,
  },
  {
    id: 2,
    name: "Rahul",
    enrollment: "22CE015",
    project: "Library System",
    guide: "Dr. Shah",
    status: "Active",
    progress: 65,
  },
  {
    id: 3,
    name: "Neha",
    enrollment: "22CE020",
    project: "Hospital Management",
    guide: "Dr. Mehta",
    status: "Proposal Pending",
    progress: 20,
  },
];

function AssignedStudents() {
  return (
    <div className="assigned-page">

      <div className="page-header">
        <h2>Assigned Students</h2>
        <button className="add-btn">+ Add Student</button>
      </div>

      <div className="summary">

        <div className="summary-card">
          <h2>25</h2>
          <p>Total Students</p>
        </div>

        {/* <div className="summary-card">
          <h2>20</h2>
          <p>Active</p>
        </div> */}

        <div className="summary-card">
          <h2>3</h2>
          <p>Completed</p>
        </div>

        <div className="summary-card">
          <h2>2</h2>
          <p>Pending</p>
        </div>

      </div>

      <div className="toolbar">

        <div className="search-box">
          <FaSearch />
          <input
            type="text"
            placeholder="Search student..."
          />
        </div>

        <select>
          <option>All Status</option>
          <option>Active</option>
          <option>Completed</option>
          <option>Proposal Pending</option>
        </select>

      </div>

      <div className="table-container">

        <table>

          <thead>
            <tr>
              <th>Name</th>
              <th>Enrollment</th>
              <th>Project</th>
              <th>Guide</th>
              <th>Status</th>
              <th>Progress</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>

            {students.map((student) => (

              <tr key={student.id}>

                <td>{student.name}</td>

                <td>{student.enrollment}</td>

                <td>{student.project}</td>

                <td>{student.guide}</td>

                <td>
                  <span
                    className={
                      student.status === "Active"
                        ? "badge active"
                        : "badge pending"
                    }
                  >
                    {student.status}
                  </span>
                </td>

                <td>

                  <div className="progress">

                    <div
                      className="progress-bar"
                      style={{
                        width: `${student.progress}%`,
                      }}
                    ></div>

                  </div>

                  <small>{student.progress}%</small>

                </td>

                <td>

                  <button className="view-btn">
                    <FaEye />
                  </button>

                </td>

              </tr>

            ))}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default AssignedStudents;