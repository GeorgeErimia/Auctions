import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getLoggedInUser, isAdminUser } from "../services/AuthService";
import {
  getAllAuctions,
  getAuctionsByOwner,
  isEnded,
  removeAuction,
} from "../services/AuctionService";
import { getLoggedInUserObj } from "../services/UserService";
import { convertToDisplay } from "../helper/DateProcessing";
// import HeaderComponent from "./HeaderComponent";
import HeaderComponentV2 from "./HeaderComponentV2";

const ListAuctionsComponent = ({ user }) => {
  const [auctions, setAuctions] = useState([]);
  const [currentUserId, setCurrentUserId] = useState("");

  const navigator = useNavigate();

  const isAdmin = isAdminUser();

  const loggedInUser = getLoggedInUser();

  useEffect(() => {
    listAuctions();
    // Set up a timer to update the auctions every second
    const interval = setInterval(() => {
      listAuctions();
    }, 1000);

    // Clean up the timer when the component unmounts
    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    getLoggedInUserObj()
      .then((response) => {
        setCurrentUserId(response.data.id);
      })
      .catch((error) => {
        console.error(error);
      });
  });

  function listAuctions() {
    if (user == null) {
      getAllAuctions()
        .then((response) => {
          setAuctions(response.data);
        })
        .catch((error) => console.error(error));
    } else {
      getAuctionsByOwner(user)
        .then((response) => {
          setAuctions(response.data);
        })
        .catch((error) => console.error(error));
    }
  }

  function addNewAuction() {
    navigator("/add-auction");
  }

  function updateAuction(id) {
    navigator("/update-auction/" + id);
  }

  function deleteAuction(id) {
    if (window.confirm("Are you sure you want to delete this auction?")) {
      removeAuction(id)
        .then((response) => {
          console.log(response);
          listAuctions();
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  function auctionsListHeader() {
    if (user != null) {
      if (user === loggedInUser) {
        return <h1>My Auctions</h1>;
      } else {
        return <h1>{`${user}'s Auctions`}</h1>;
      }
    } else {
      return <h1>Auctions List</h1>;
    }
  }

  return (
    <>
      {/* <HeaderComponent /> */}
      {/* <HeaderComponentV2 /> */}
      {/* <div className="container">
        {auctionsListHeader()}
        {(user === loggedInUser || user == null) && (
          <button className="btn btn-primary mb-2" onClick={addNewAuction}>
            Add Auction
          </button>
        )}

        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>User (Owner)</th>
              <th>Ends In</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {auctions.map((auction) => {
              return (
                <tr key={auction.id}>
                  <td>{auction.id}</td>
                  <td>
                    <a href={`/auctions/${auction.id}`}>{auction.name}</a>
                  </td>
                  <td>
                    <a href={`/user/${auction.userUsername}`}>
                      {auction.userUsername}
                    </a>
                  </td>
                  <td>{convertToDisplay(auction.endDate)}</td>
                  <td>
                    {(isAdmin || currentUserId == auction.userId) &&
                      !isEnded(auction) && (
                        <button
                          className="btn btn-info me-2"
                          onClick={() => updateAuction(auction.id)}
                        >
                          Update
                        </button>
                      )}

                    {isAdmin && (
                      <button
                        className="btn btn-danger me-2"
                        onClick={() => deleteAuction(auction.id)}
                      >
                        Delete
                      </button>
                    )}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div> */}
    </>
  );
};

export default ListAuctionsComponent;
