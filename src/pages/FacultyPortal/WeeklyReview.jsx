import { useEffect, useState } from "react";
import axios from "axios";
import "./WeeklyReview.css";

function WeeklyReview() {

  const WEEKLY_PROGRESS_API =
    "http://localhost:8081/api/weekly-progress";

  const WEEKLY_REVIEW_API =
    "http://localhost:8081/api/weekly-review";


  const [weeklyData, setWeeklyData] = useState([]);

  const [search, setSearch] = useState("");

  const [selectedWeek, setSelectedWeek] = useState(null);

  const [selectedWeekFilter, setSelectedWeekFilter] =
    useState("");

  const [feedback, setFeedback] = useState("");

  const [loading, setLoading] = useState(true);

  const [saving, setSaving] = useState(false);

  const [error, setError] = useState("");


  // =========================================================
  // FETCH WEEKLY PROGRESS
  // =========================================================

  useEffect(() => {
    fetchWeeklyProgress();
  }, []);


  const fetchWeeklyProgress = async () => {

    try {

      setLoading(true);
      setError("");

      const response = await axios.get(
        WEEKLY_PROGRESS_API
      );

      setWeeklyData(response.data);

    } catch (error) {

      console.error(
        "Error fetching weekly progress:",
        error
      );

      setError(
        "Unable to load weekly progress."
      );

      setWeeklyData([]);

    } finally {

      setLoading(false);

    }
  };


  // =========================================================
  // SEARCH + WEEK FILTER
  // =========================================================

  const filteredData = weeklyData.filter((item) => {

    const studentName =
      item.studentName ||
      item.student?.studentName ||
      item.student?.name ||
      "";

    const matchesSearch =
      studentName
        .toLowerCase()
        .includes(search.toLowerCase());


    const matchesWeek =
      selectedWeekFilter === "" ||
      item.week === selectedWeekFilter;


    return matchesSearch && matchesWeek;
  });


  // =========================================================
  // OPEN REVIEW MODAL
  // =========================================================

  const openReview = async (item) => {

    try {

      setError("");

      setSelectedWeek(item);

      // If feedback already exists, display it
      setFeedback(
        item.facultyFeedback || ""
      );


      // Try to get existing review for this progress
      try {

        const response = await axios.get(
          `${WEEKLY_REVIEW_API}/progress/${item.progressId}`
        );

        if (response.data) {

          setFeedback(
            response.data.facultyFeedback || ""
          );

        }

      } catch (reviewError) {

        // No review yet is completely fine
        console.log(
          "No existing faculty review found."
        );

      }

    } catch (error) {

      console.error(
        "Error opening weekly review:",
        error
      );

      setError(
        "Unable to open weekly review."
      );

    }

  };


  // =========================================================
  // SUBMIT REVIEW
  // =========================================================

  const submitReview = async (status) => {

    if (!selectedWeek) {
      return;
    }


    if (!feedback.trim()) {

      setError(
        "Please enter faculty feedback before submitting."
      );

      return;
    }


    try {

      setSaving(true);
      setError("");


      // =====================================================
      // STEP 1
      // UPDATE WEEKLY PROGRESS STATUS
      // =====================================================

      await axios.put(
        `${WEEKLY_PROGRESS_API}/${selectedWeek.progressId}/status`,
        {
          status: status
        }
      );


      // =====================================================
      // STEP 2
      // SAVE / UPDATE FACULTY REVIEW
      // =====================================================

      await axios.put(
        `${WEEKLY_REVIEW_API}/progress/${selectedWeek.progressId}`,
        {
          progressId: selectedWeek.progressId,

          facultyFeedback:
            feedback.trim()
        }
      );


      // =====================================================
      // CLOSE MODAL
      // =====================================================

      setSelectedWeek(null);

      setFeedback("");


      // =====================================================
      // REFRESH CARDS
      // =====================================================

      await fetchWeeklyProgress();

    } catch (error) {

      console.error(
        "Error submitting weekly review:",
        error
      );


      const backendMessage =
        error.response?.data?.message;


      if (backendMessage) {

        setError(backendMessage);

      } else {

        setError(
          "Unable to submit weekly review. Please try again."
        );

      }

    } finally {

      setSaving(false);

    }
  };


  // =========================================================
  // CLOSE MODAL
  // =========================================================

  const closeModal = () => {

    if (saving) {
      return;
    }

    setSelectedWeek(null);

    setFeedback("");

    setError("");
  };


  // =========================================================
  // UI
  // =========================================================

  return (

    <div className="weekly-page">


      {/* =====================================================
          HEADER
      ===================================================== */}

      <div className="weekly-header">

        <h1>
          Weekly Review
        </h1>

        <p>
          Review weekly progress submitted by students.
        </p>

      </div>


      {/* =====================================================
          TOOLBAR
      ===================================================== */}

      <div className="weekly-toolbar">

        <input
          type="text"
          placeholder="Search Student..."
          value={search}
          onChange={(e) =>
            setSearch(e.target.value)
          }
        />


        <select
          value={selectedWeekFilter}
          onChange={(e) =>
            setSelectedWeekFilter(e.target.value)
          }
        >

          <option value="">
            All Weeks
          </option>

          <option value="Week 1">
            Week 1
          </option>

          <option value="Week 2">
            Week 2
          </option>

          <option value="Week 3">
            Week 3
          </option>

          <option value="Week 4">
            Week 4
          </option>

          <option value="Week 5">
            Week 5
          </option>

          <option value="Week 6">
            Week 6
          </option>

          <option value="Week 7">
            Week 7
          </option>

          <option value="Week 8">
            Week 8
          </option>

        </select>

      </div>


      {/* =====================================================
          ERROR MESSAGE
      ===================================================== */}

      {error && (

        <div className="error-message">

          {error}

        </div>

      )}


      {/* =====================================================
          LOADING
      ===================================================== */}

      {loading && (

        <div className="no-data">

          <h3>
            Loading...
          </h3>

          <p>
            Fetching weekly progress.
          </p>

        </div>

      )}


      {/* =====================================================
          NO DATA
      ===================================================== */}

      {!loading &&
        filteredData.length === 0 && (

          <div className="no-data">

            <h3>
              No Weekly Progress Found
            </h3>

            <p>
              No weekly progress has been
              submitted by students yet.
            </p>

          </div>

        )}


      {/* =====================================================
          WEEKLY CARDS
      ===================================================== */}

      {!loading && (

        <div className="weekly-grid">

          {filteredData.map((item) => {

            const studentName =
              item.studentName ||
              item.student?.studentName ||
              item.student?.name ||
              "Unknown Student";


            const week =
              item.week ||
              "Week Not Available";


            const progress =
              item.percentage ??
              item.progress ??
              0;


            const status =
              item.status ||
              "Submitted";


            return (

              <div
                className="weekly-card"
                key={
                  item.progressId ||
                  item.id
                }
              >


                {/* CARD HEADER */}

                <div className="weekly-top">

                  <h3>
                    {studentName}
                  </h3>


                  <span
                    className={
                      status === "Approved"
                        ? "submitted"
                        : status ===
                          "Needs Improvement"
                        ? "pending"
                        : "pending"
                    }
                  >

                    {status}

                  </span>

                </div>


                {/* WEEK */}

                <p>

                  <strong>
                    {week}
                  </strong>

                </p>


                {/* PROGRESS */}

                <div className="progress-bar">

                  <div
                    className="progress-fill"
                    style={{
                      width: `${progress}%`
                    }}
                  >

                    {progress}%

                  </div>

                </div>


                {/* VIEW REVIEW */}

                <button
                  className="view-btn"
                  onClick={() =>
                    openReview(item)
                  }
                >

                  View Review

                </button>

              </div>

            );

          })}

        </div>

      )}


      {/* =====================================================
          REVIEW MODAL
      ===================================================== */}

      {selectedWeek && (

        <div className="modal-overlay">

          <div className="weekly-modal">


            {/* STUDENT */}

            <h2>

              {
                typeof selectedWeek.studentName ===
                "string"

                  ? selectedWeek.studentName

                  : selectedWeek.studentId

                  ? `Student ${selectedWeek.studentId}`

                  : "Student"
              }

            </h2>


            {/* WEEK */}

            <p>

              <strong>
                {selectedWeek.week}
              </strong>

            </p>


            <hr />


            {/* =================================================
                TASKS COMPLETED
            ================================================= */}

            <h4>
              Tasks Completed
            </h4>

            <p>

              {
                selectedWeek.workCompleted ||
                selectedWeek.tasks ||
                "-"
              }

            </p>


            {/* =================================================
                CHALLENGES
            ================================================= */}

            <h4>
              Challenges Faced
            </h4>

            <p>

              {
                selectedWeek.challenges ||
                "-"
              }

            </p>


            {/* =================================================
                NEXT WEEK PLAN
            ================================================= */}

            <h4>
              Plan for Next Week
            </h4>

            <p>

              {
                selectedWeek.nextPlan ||
                "-"
              }

            </p>


            {/* =================================================
                PROJECT
            ================================================= */}

            <h4>
              Project Title
            </h4>

            <p>

              {
                selectedWeek.projectTitle ||
                "-"
              }

            </p>


            {/* =================================================
                GITHUB
            ================================================= */}

            <h4>
              GitHub Repository
            </h4>


            {selectedWeek.githubUrl ? (

              <a
                href={selectedWeek.githubUrl}
                target="_blank"
                rel="noreferrer"
              >

                {selectedWeek.githubUrl}

              </a>

            ) : (

              <p>
                -
              </p>

            )}


            {/* =================================================
                FACULTY FEEDBACK
            ================================================= */}

            <h4>
              Faculty Feedback
            </h4>


            <textarea
              placeholder="Write feedback..."
              value={feedback}
              onChange={(e) =>
                setFeedback(e.target.value)
              }
              disabled={saving}
            />


            {/* =================================================
                ACTION BUTTONS
            ================================================= */}

            <div className="modal-buttons">


              {/* APPROVE */}

              <button
                className="approve-btn"
                disabled={saving}
                onClick={() =>
                  submitReview("Approved")
                }
              >

                {saving
                  ? "Saving..."
                  : "Approve"}

              </button>


              {/* NEEDS IMPROVEMENT */}

              <button
                className="improve-btn"
                disabled={saving}
                onClick={() =>
                  submitReview(
                    "Needs Improvement"
                  )
                }
              >

                {saving
                  ? "Saving..."
                  : "Needs Improvement"}

              </button>

            </div>


            {/* CLOSE */}

            <button
              className="close-btn"
              disabled={saving}
              onClick={closeModal}
            >

              Close

            </button>


          </div>

        </div>

      )}

    </div>

  );
}

export default WeeklyReview;