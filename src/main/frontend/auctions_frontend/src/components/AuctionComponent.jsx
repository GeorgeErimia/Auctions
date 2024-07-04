import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getAuction,
  getImages,
  removeAuction,
  updateAuction,
} from "../services/AuctionService";
import { getUserById } from "../services/UserService";
import {
  getLoggedInUser,
  isAdminUser,
  isUserLoggedIn,
} from "../services/AuthService";
import { convertToDateFormat, convertToHours } from "../helper/DateProcessing";
import ImageComponent from "./ImageComponent";
import axios from "axios";
import { fetchImages } from "../services/ImageService";
import ImageGallery from "./ImageGallery";
// import ReactImageGallery from "react-image-gallery";

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
          navigator("/auctions");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  return (
    <div className="container small">
      <div className="row auction-header">
        <h1>{auction.name}</h1>
      </div>
      <div className="row">
        <div className="col auction-col-1">
          <div className="row auction-row-1">
            <ImageGallery images={images} />
          </div>
          <div className="row">
            <pre>{auction.description}</pre>
          </div>
        </div>
        <div className="col auction-col-2">
          <div className="col details-col">
            <div className="detail">
              {" "}
              <h4>Closes on</h4>
              <h2>
                {convertToDateFormat(auction.endDate)} at{" "}
                {convertToHours(auction.endDate)}
              </h2>
            </div>

            <div className="detail">
              {" "}
              <span>
                Owner: <a href={`/user/${auction.userUsername}`}>{ownerName}</a>
              </span>
            </div>

            <div className="detail">
              <span id="highest-bid">Highest bid : 100$</span>
            </div>
          </div>
          <div className="col details-col actions-col">
            {isUserLoggedIn() && getLoggedInUser() !== auction.userUsername && (
              <button className="btn-place-bid btn-action">
                Place your Bid!
              </button>
            )}
            {(auction.userUsername === getLoggedInUser() || isAdmin) && (
              <>
                <button
                  className="btn-edit btn-action"
                  onClick={() => navigator(`/update-auction/${auction.id}`)}
                >
                  Edit this Auction
                </button>
              </>
            )}
            {isAdmin && (
              <>
                <button
                  className="btn-delete btn-action"
                  onClick={() => deleteAuction(auction.id)}
                >
                  Delete this Auction
                </button>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
export default AuctionComponent;
