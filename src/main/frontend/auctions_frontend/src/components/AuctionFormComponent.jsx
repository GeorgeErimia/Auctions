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
    <div className="container">
      <br />
      <br />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">Auction Name</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Auction Name"
                  name="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Auction Description</label>
                {/* <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Description"
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                /> */}
                <textarea
                  className="form-control"
                  rows={5}
                  placeholder="Enter Description"
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                ></textarea>
              </div>
              {isAdminUser() && (
                <div className="form-group mb-2">
                  <label className="form-label">Auction User Id</label>
                  <input
                    type="number"
                    className="form-control"
                    placeholder="Enter User Id"
                    name="userId"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                  />
                </div>
              )}

              <button
                className="btn btn-success"
                onClick={(e) => saveOrUpdateAuction(e)}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuctionFormComponent;
