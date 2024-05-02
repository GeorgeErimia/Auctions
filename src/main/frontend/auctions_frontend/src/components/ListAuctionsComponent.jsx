import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { isAdminUser } from "../services/AuthService";
import { getAllAuctions, removeAuction } from "../services/AuctionService";
import { getLoggedInUserObj, getUserById } from "../services/UserService";
import axios from "axios";

const ListAuctionsComponent = () => {
  const [auctions, setAuctions] = useState([]);
  const [currentUserId, setCurrentUserId] = useState();

  const navigator = useNavigate();

  const isAdmin = isAdminUser();

  useEffect(() => {
    listAuctions();
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
    getAllAuctions()
      .then((response) => {
        setAuctions(response.data);
        // console.log(auctions);
      })
      .catch((error) => console.error(error));
  }

  function addNewAuction() {
    navigator("/add-auction");
  }

  function updateAuction(id) {
    console.log("Update auction with id: " + id);
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

  return (
    <div className="container">
      <h1>Auctions List</h1>
      {/* Button to add a new Auction  */}
      <button className="btn btn-primary mb-2" onClick={addNewAuction}>
        Add Auction
      </button>
      <table className="table table-bordered table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>User (Owner)</th>
            {/* <th>User Id</th> */}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {auctions.map((auction) => {
            return (
              <tr key={auction.id}>
                <td>{auction.id}</td>
                <td>{auction.name}</td>
                <td>{auction.userUsername}</td>
                {/* <td>{auction.userId}</td> */}

                <td>
                  {(isAdmin || currentUserId == auction.userId) && (
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
    </div>
  );
};

export default ListAuctionsComponent;
