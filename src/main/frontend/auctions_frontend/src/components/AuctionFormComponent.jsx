import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  createAuction,
  getAuction,
  updateAuction,
} from "../services/AuctionService";
import { getLoggedInUserObj } from "../services/UserService";
import { isAdminUser } from "../services/AuthService";

const AuctionFormComponent = () => {
  const [name, setName] = useState("");
  const [userId, setUserId] = useState();
  const [description, setDescription] = useState("");
  const navigator = useNavigate();
  const { id } = useParams();

  function saveOrUpdateAuction(e) {
    e.preventDefault();

    const auction = { name, userId, description };
    console.log(auction);

    if (id) {
      updateAuction(id, auction)
        .then((response) => {
          console.log(response);
          navigator("/auctions/" + id);
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      createAuction(auction)
        .then((response) => {
          console.log(response);
          navigator("/auctions");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  function pageTitle() {
    if (id) {
      return <h2 className="text-center">Update Auction</h2>;
    } else {
      return <h2 className="text-center">Add Auction</h2>;
    }
  }

  useEffect(() => {
    if (id) {
      getAuction(id)
        .then((response) => {
          const auction = response.data;
          console.log(auction);
          setName(auction.name);
          setUserId(auction.userId);
          setDescription(auction.description);
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      getLoggedInUserObj()
        .then((response) => {
          console.log(response.data);
          setUserId(response.data.id);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [id]);

  return (
    <div className="container small">
      <div className="row-title">
        <h1>Add Auction</h1>
      </div>
      <form action="">
        <div className="form-data">
          <div className="form-data" id="form-auction-name">
            <label>Item Name</label>
            <input
              type="text"
              name="name"
              placeholder="Enter Item Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className="form-data" id="form-auction-description">
            <label>Item Description</label>
            <textarea
              name="description"
              placeholder="Enter Description"
              rows={10}
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></textarea>
          </div>
          {isAdminUser() && (
            <div className="form-data" id="form-auction-owner-id">
              <label>Owner ID</label>
              <input
                type="number"
                placeholder="Enter Owner ID"
                name="userId"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
              />
            </div>
          )}
        </div>
        <div className="form-images"></div>
        <div className="form-actions">
          <button
            className="btn-submit"
            onClick={(e) => saveOrUpdateAuction(e)}
          >
            Submit
          </button>
          <button className="btn-cancel" onClick={() => History.go(-1)}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default AuctionFormComponent;
