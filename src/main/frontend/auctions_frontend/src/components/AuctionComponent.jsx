import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getAuction,
  getImages,
  removeAuction,
  updateAuction,
} from "../services/AuctionService";
import { getUserById } from "../services/UserService";
import { getLoggedInUser, isAdminUser } from "../services/AuthService";
import { convertToDateFormat, convertToHours } from "../helper/DateProcessing";
import ImageComponent from "./ImageComponent";
import axios from "axios";
import { fetchImages } from "../services/ImageService";

const AuctionComponent = () => {
  const { id } = useParams();
  const [auction, setAuction] = useState({});
  const [ownerName, setOwnerName] = useState("");
  const [images, setImages] = useState([]);
  const isAdmin = isAdminUser();
  const navigator = useNavigate();

  // useEffect(() => {
  //   async function fetchImages() {
  //     try {
  //       // Fetch image data from the first endpoint
  //       const imageDataResponse = await axios.get(
  //         `http://localhost:8080/api/v2/images/auction/${id}/data`
  //       );
  //       const imageData = imageDataResponse.data;

  //       // Fetch images from the second endpoint for each image ID
  //       const imagePromises = imageData.map(async (imageInfo) => {
  //         const imageId = imageInfo.id;
  //         const imageUrl = `http://localhost:8080/api/v2/images/${imageId}/image`;
  //         const imageResponse = await axios.get(imageUrl, {
  //           responseType: "arraybuffer",
  //         });
  //         const imageBlob = new Blob([imageResponse.data], {
  //           type: "image/jpeg",
  //         });
  //         return URL.createObjectURL(imageBlob);
  //       });

  //       // Wait for all image requests to complete
  //       const imageUrls = await Promise.all(imagePromises);

  //       // Set the image URLs in state
  //       setImages(imageUrls);
  //     } catch (error) {
  //       console.error("Error fetching images:", error);
  //     }
  //   }

  //   fetchImages();
  // }, [id]);

  // useEffect(() => {
  //   const imagesFetch = fetchImages(id);
  //   setImages(imagesFetch);
  // }, [id]);

  useEffect(() => {
    async function fetchAuctionImages() {
      try {
        const imagesFetch = await fetchImages(id);
        setImages(imagesFetch);
      } catch (error) {
        console.error("Error fetching images:", error);
      }
    }

    fetchAuctionImages();
  }, [id]);

  useEffect(() => {
    getAuction(id)
      .then((response) => {
        setAuction(response.data);
        console.log(auction);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [id]);

  useEffect(() => {
    if (auction.userId) {
      getUserById(auction.userId)
        .then((response) => {
          let result = response.data;
          setOwnerName(result.name);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [auction.userId]);

  console.log(auction);

  function updateAuction() {
    navigator("/update-auction/" + id);
  }

  function deleteAuction(id) {
    if (window.confirm("Are you sure you want to delete this auction?")) {
      removeAuction(id)
        .then((response) => {
          console.log(response);
          navigator("/");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  return (
    <div className="container">
      <div className="row vh-100">
        <div className="col-sm-3 border">
          {/* Auction Info Card */}
          <div className="card mt-2">
            <div className="card-header text-center">
              <h3>Auction Overview</h3>
            </div>
            <div className="card-body">
              <span>
                Owner: <a href={`/user/${auction.userUsername}`}>{ownerName}</a>
              </span>
              <br />
              <span>
                Ends on {convertToDateFormat(auction.endDate)} at{" "}
                {convertToHours(auction.endDate)}
              </span>
            </div>
          </div>

          {/* Bids Card */}
          <div className="card mt-2">
            <div className="card-header text-center">
              <h3>Bids Information</h3>
            </div>
            <div className="card-body"></div>
            <div className="card-footer">Footer</div>
          </div>

          {/* Actions Overview  */}
          {auction.userUsername == getLoggedInUser() && (
            <div className="card mt-2">
              <div className="card-header text-center">
                <h3>Actions</h3>
              </div>
              <div className="card-body text-center">
                <button
                  className="btn btn-primary btn-lg"
                  onClick={() => updateAuction()}
                >
                  Update this auction
                </button>
                {isAdmin && (
                  <button
                    className="btn btn-danger btn-lg mt-2"
                    onClick={() => deleteAuction()}
                  >
                    Delete this auction
                  </button>
                )}
              </div>
            </div>
          )}
        </div>

        {/* Auctions Information */}
        <div className="col-sm-9 border">
          <div className="card">
            <div className="card-header text-center">
              <h3>Auction Information</h3>
            </div>
            <div className="card-body">
              <div className="border text-center p-1">
                <h3>{auction.name}</h3>
              </div>
              <div className="border p-4">
                <pre>{auction.description}</pre>
                {images.map((imageUrl, index) => (
                  <img
                    key={index}
                    src={imageUrl}
                    width={400}
                    height={400}
                  ></img>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuctionComponent;
