import { useEffect, useState } from "react";

import ProjectDetailsCard from "../../components/Marks/ProjectDetailsCard";
import MarksTableCard from "../../components/Marks/MarksTableCard";
import FeedbackCard from "../../components/Marks/FeedbackCard";
import ResultCard from "../../components/Marks/ResultCard";

import "./Marks.css";

function Marks() {

  const [projectDetails, setProjectDetails] = useState({
    projectTitle: "",
    guideName: "",
    submissionDate: "",
    status: "",
  });

  const [marks, setMarks] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    const fetchData = async () => {

      try {

        const student = JSON.parse(
          localStorage.getItem("student")
        );

        if (!student) {
          console.log("Student not found in localStorage");
          setLoading(false);
          return;
        }

        const studentId = student.id || student.Id;

        console.log("Student ID:", studentId);

        // --------------------------------
        // 1. FETCH PROJECT / PROPOSAL
        // --------------------------------

        const proposalResponse = await fetch(
          `http://localhost:8081/proposal/student/${studentId}`
        );

        const proposalData = await proposalResponse.json();

        console.log("Proposal Data:", proposalData);

        if (proposalResponse.ok) {

          setProjectDetails({
            projectTitle: proposalData.projectTitle || "",
            guideName: proposalData.guideName || "",
            submissionDate: proposalData.submissionDate || "",
            status: "Submitted",
          });

        } else {

          console.error(
            "Failed to fetch proposal:",
            proposalData
          );

        }

        // --------------------------------
        // 2. FETCH STUDENT MARKS
        // --------------------------------

        const marksResponse = await fetch(
          `http://localhost:8081/api/student/marks/${studentId}`
        );

        const marksData = await marksResponse.json();

        console.log("Marks Data:", marksData);

        if (marksResponse.ok) {

          setMarks(marksData);

        } else {

          console.error(
            "Failed to fetch marks:",
            marksData
          );

          setMarks(null);
        }

      } catch (error) {

        console.error(
          "Error fetching marks/project details:",
          error
        );

      } finally {

        setLoading(false);

      }
    };

    fetchData();

  }, []);


  if (loading) {
    return (
      <div className="marks-container">
        <h1 className="page-title">
          My Marks
        </h1>

        <p>
          Loading marks...
        </p>
      </div>
    );
  }


  return (

    <div className="marks-container">

      <h1 className="page-title">
        My Marks
      </h1>

      <p className="page-subtitle">
        View your project evaluation, faculty feedback, and final result.
      </p>


      {/* PROJECT DETAILS */}

      <ProjectDetailsCard
        projectDetails={projectDetails}
      />


      {/* MARKS */}

      {marks ? (

        <>

          <MarksTableCard
            marks={marks}
          />

          <FeedbackCard
            marks={marks}
          />

          <ResultCard
            marks={marks}
          />

        </>

      ) : (

        <div className="no-marks">
          <p>
            Marks have not been published yet.
          </p>
        </div>

      )}

    </div>

  );
}

export default Marks;





// import { useEffect, useState } from "react";

// import ProjectDetailsCard from "../../components/Marks/ProjectDetailsCard";
// import MarksTableCard from "../../components/Marks/MarksTableCard";
// import FeedbackCard from "../../components/Marks/FeedbackCard";
// import ResultCard from "../../components/Marks/ResultCard";

// import "./Marks.css";

// function Marks() {

//   const [projectDetails, setProjectDetails] = useState({
//     projectTitle: "",
//     guideName: "",
//     submissionDate: "",
//     status: "",
//   });

//   useEffect(() => {

//     const fetchProjectDetails = async () => {

//       try {

//         const student = JSON.parse(
//           localStorage.getItem("student")
//         );

//         if (!student) {
//           console.log("Student not found in localStorage");
//           return;
//         }

//         const studentId = student.id || student.Id;

//         console.log("Student ID:", studentId);

//         const response = await fetch(
//           `http://localhost:8081/api/student/${studentId}`
//         );

//         const data = await response.json();

//         console.log("Proposal Data:", data);

//         if (!response.ok) {
//           console.error("Failed to fetch proposal:", data);
//           return;
//         }

//         setProjectDetails({
//           projectTitle: data.projectTitle || "",
//           guideName: data.guideName || "",
//           submissionDate: data.submissionDate || "",
//           status: "Submitted",
//         });

//       } catch (error) {

//         console.error(
//           "Error fetching project details:",
//           error
//         );

//       }

//     };

//     fetchProjectDetails();

//   }, []);

//   return (
//     <div className="marks-container">

//       <h1 className="page-title">
//         My Marks
//       </h1>

//       <p className="page-subtitle">
//         View your project evaluation, faculty feedback, and final result.
//       </p>

//       <ProjectDetailsCard
//         projectDetails={projectDetails}
//       />

//       <MarksTableCard />

//       <FeedbackCard />

//       <ResultCard />

//     </div>
//   );
// }

// export default Marks;