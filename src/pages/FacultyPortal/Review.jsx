import { useEffect, useState } from "react";
import axios from "axios";

import "./Review.css";

function Review() {

  // =====================================================
  // STATES
  // =====================================================

  const [proposals, setProposals] = useState([]);

  const [selectedProposal, setSelectedProposal] = useState(null);

  const [search, setSearch] = useState("");

  const [filter, setFilter] = useState("All");

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");


  // =====================================================
  // BACKEND API URL
  // =====================================================

  const API_URL = "http://localhost:8081/review-proposal";


  // =====================================================
  // GET ALL PROPOSALS
  // =====================================================

  useEffect(() => {

    fetchProposals();

  }, []);


  const fetchProposals = async () => {

    try {

      setLoading(true);
      setError("");

      const response = await axios.get(API_URL);

      console.log("Review proposals:", response.data);

      setProposals(response.data);

    } catch (error) {

      console.error(
        "Error fetching review proposals:",
        error
      );

      setError(
        "Unable to load proposals. Please make sure the backend is running."
      );

    } finally {

      setLoading(false);

    }
  };


  // =====================================================
  // VIEW SINGLE PROPOSAL
  // =====================================================

  const handleViewProposal = async (reviewProposalId) => {

    try {

      const response = await axios.get(
        `${API_URL}/${reviewProposalId}`
      );

      console.log(
        "Selected proposal:",
        response.data
      );

      setSelectedProposal(response.data);

    } catch (error) {

      console.error(
        "Error fetching proposal:",
        error
      );

      alert("Unable to load proposal.");

    }
  };


  // =====================================================
  // UPDATE PROPOSAL STATUS
  // =====================================================

  const handleStatusChange = async (
    reviewProposalId,
    status
  ) => {

    try {

      const response = await axios.put(
        `${API_URL}/${reviewProposalId}/status`,
        {
          status: status,
        }
      );

      console.log(
        "Updated proposal:",
        response.data
      );


      // -----------------------------------------------
      // Update proposal in the cards
      // -----------------------------------------------

      setProposals((previousProposals) =>
        previousProposals.map((proposal) =>
          proposal.reviewProposalId ===
          response.data.reviewProposalId
            ? response.data
            : proposal
        )
      );


      // -----------------------------------------------
      // Update currently opened proposal
      // -----------------------------------------------

      setSelectedProposal(response.data);


      alert(
        `Proposal ${status
          .toLowerCase()
          .replace("_", " ")} successfully.`
      );

    } catch (error) {

      console.error(
        "Error updating proposal status:",
        error
      );

      alert(
        "Unable to update proposal status."
      );
    }
  };


  // =====================================================
  // SEARCH + FILTER
  // =====================================================

  const filteredProposals = proposals.filter(
    (proposal) => {

      const searchText =
        search.toLowerCase().trim();


      const matchSearch =
        proposal.projectTitle
          ?.toLowerCase()
          .includes(searchText) ||

        String(proposal.studentId)
          .includes(searchText);


      const matchFilter =
        filter === "All" ||
        proposal.status === filter;


      return matchSearch && matchFilter;
    }
  );


  // =====================================================
  // LOADING
  // =====================================================

  if (loading) {

    return (

      <div className="review-page">

        <div className="review-header">

          <h1>
            Project Proposal Review
          </h1>

          <p>
            Loading student proposals...
          </p>

        </div>

      </div>

    );
  }


  // =====================================================
  // MAIN UI
  // =====================================================

  return (

    <div className="review-page">


      {/* =================================================
          HEADER
      ================================================== */}

      <div className="review-header">

        <h1>
          Project Proposal Review
        </h1>

        <p>
          Review, approve or reject student project proposals.
        </p>

      </div>


      {/* =================================================
          ERROR MESSAGE
      ================================================== */}

      {error && (

        <div className="error-message">

          <p>{error}</p>

          <button onClick={fetchProposals}>
            Retry
          </button>

        </div>

      )}


      {/* =================================================
          SEARCH + FILTER
      ================================================== */}

      <div className="review-toolbar">

        <input
          type="text"
          placeholder="Search Student / Project..."
          value={search}
          onChange={(e) =>
            setSearch(e.target.value)
          }
        />


        <select
          value={filter}
          onChange={(e) =>
            setFilter(e.target.value)
          }
        >

          <option value="All">
            All
          </option>

          <option value="PENDING">
            Pending
          </option>

          <option value="APPROVED">
            Approved
          </option>

          <option value="REJECTED">
            Rejected
          </option>

          <option value="CHANGES_REQUESTED">
            Request Changes
          </option>

        </select>

      </div>


      {/* =================================================
          PROPOSAL GRID
      ================================================== */}

      <div className="proposal-grid">

        {filteredProposals.length === 0 ? (

          <p>
            No proposals found.
          </p>

        ) : (

          filteredProposals.map((proposal) => (

            <div
              className="proposal-card"
              key={proposal.reviewProposalId}
            >


              {/* =========================================
                  CARD TOP
              ========================================== */}

              <div className="proposal-top">

                <h3>
                  {proposal.projectTitle}
                </h3>


                <span
                  className={`status ${proposal.status
                    ?.toLowerCase()
                    .replace("_", "-")}`}
                >

                  {proposal.status === "CHANGES_REQUESTED"
                    ? "Request Changes"
                    : proposal.status}

                </span>

              </div>


              {/* =========================================
                  STUDENT
              ========================================== */}

              <p>

                <strong>
                  Student :
                </strong>{" "}

                {proposal.studentId}

              </p>


              {/* =========================================
                  DOMAIN
              ========================================== */}

              <p>

                <strong>
                  Domain :
                </strong>{" "}

                {proposal.projectDomain}

              </p>


              {/* =========================================
                  TECHNOLOGY
              ========================================== */}

              <p>

                <strong>
                  Technology :
                </strong>{" "}

                {proposal.technologyStack}

              </p>


              {/* =========================================
                  VIEW BUTTON
              ========================================== */}

              <button
                className="view-btn"
                onClick={() =>
                  handleViewProposal(
                    proposal.reviewProposalId
                  )
                }
              >

                View Proposal

              </button>

            </div>

          ))

        )}

      </div>


      {/* =================================================
          PROPOSAL MODAL
      ================================================== */}

      {selectedProposal && (

        <div className="modal-overlay">

          <div className="proposal-modal">


            {/* =========================================
                TITLE
            ========================================== */}

            <h2>
              {selectedProposal.projectTitle}
            </h2>


            {/* =========================================
                STUDENT
            ========================================== */}

            <p>

              <strong>
                Student :
              </strong>{" "}

              {selectedProposal.studentId}

            </p>


            {/* =========================================
                DOMAIN
            ========================================== */}

            <p>

              <strong>
                Domain :
              </strong>{" "}

              {selectedProposal.projectDomain}

            </p>


            {/* =========================================
                TECHNOLOGY
            ========================================== */}

            <p>

              <strong>
                Technology :
              </strong>{" "}

              {selectedProposal.technologyStack}

            </p>


            {/* =========================================
                OTHER TECHNOLOGY
            ========================================== */}

            {selectedProposal.otherTechnology && (

              <p>

                <strong>
                  Other Technology :
                </strong>{" "}

                {selectedProposal.otherTechnology}

              </p>

            )}


            {/* =========================================
                MEMBER 2
            ========================================== */}

            {selectedProposal.member2Name && (

              <p>

                <strong>
                  Member 2 :
                </strong>{" "}

                {selectedProposal.member2Name}

                {selectedProposal.member2Enrollment &&
                  ` (${selectedProposal.member2Enrollment})`
                }

              </p>

            )}


            {/* =========================================
                MEMBER 3
            ========================================== */}

            {selectedProposal.member3Name && (

              <p>

                <strong>
                  Member 3 :
                </strong>{" "}

                {selectedProposal.member3Name}

                {selectedProposal.member3Enrollment &&
                  ` (${selectedProposal.member3Enrollment})`
                }

              </p>

            )}


            {/* =========================================
                PROJECT DESCRIPTION
            ========================================== */}

            <p>

              <strong>
                Project Description :
              </strong>

            </p>


            <p>
              {selectedProposal.projectDescription}
            </p>


            {/* =========================================
                PROPOSAL FILE
            ========================================== */}

            <p>

              <strong>
                Proposal File :
              </strong>{" "}

              {selectedProposal.proposalFile}

            </p>


            {/* =========================================
                CURRENT STATUS
            ========================================== */}

            <p>

              <strong>
                Current Status :
              </strong>{" "}

              <span
                className={`status ${selectedProposal.status
                  ?.toLowerCase()
                  .replace("_", "-")}`}
              >

                {selectedProposal.status ===
                "CHANGES_REQUESTED"
                  ? "Request Changes"
                  : selectedProposal.status}

              </span>

            </p>


            {/* =================================================
                ACTION BUTTONS
            ================================================== */}

            <div className="modal-buttons">


              {/* =============================================
                  APPROVE
              ============================================== */}

              <button
                className="approve"
                onClick={() =>
                  handleStatusChange(
                    selectedProposal.reviewProposalId,
                    "APPROVED"
                  )
                }
                disabled={
                  selectedProposal.status ===
                  "APPROVED"
                }
              >

                Approve

              </button>


              {/* =============================================
                  REJECT
              ============================================== */}

              <button
                className="reject"
                onClick={() =>
                  handleStatusChange(
                    selectedProposal.reviewProposalId,
                    "REJECTED"
                  )
                }
                disabled={
                  selectedProposal.status ===
                  "REJECTED"
                }
              >

                Reject

              </button>


              {/* =============================================
                  REQUEST CHANGES
              ============================================== */}

              <button
                className="changes"
                onClick={() =>
                  handleStatusChange(
                    selectedProposal.reviewProposalId,
                    "CHANGES_REQUESTED"
                  )
                }
                disabled={
                  selectedProposal.status ===
                  "CHANGES_REQUESTED"
                }
              >

                Request Changes

              </button>

            </div>


            {/* =================================================
                CLOSE
            ================================================== */}

            <button
              className="close-btn"
              onClick={() =>
                setSelectedProposal(null)
              }
            >

              Close

            </button>

          </div>

        </div>

      )}

    </div>

  );
}

export default Review;