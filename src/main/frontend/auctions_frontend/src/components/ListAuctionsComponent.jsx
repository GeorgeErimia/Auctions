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
import { fetchDefaultImage } from "../services/ImageService";
import AuctionItemComponent from "./AuctionItemComponent";

const ListAuctionsComponent = ({ user }) => {
  const [auctions, setAuctions] = useState([]);
  const [currentUserId, setCurrentUserId] = useState("");
  const [auctionImages, setAuctionImages] = useState({});

  const navigator = useNavigate();

  const isAdmin = isAdminUser();

  const loggedInUser = getLoggedInUser();

  useEffect(() => {
    listAuctions();
    // // Set up a timer to update the auctions every second
    // const interval = setInterval(() => {
    //   listAuctions();
    // }, 1000);

    // // Clean up the timer when the component unmounts
    // return () => clearInterval(interval);
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

  useEffect(() => {
    if (auctions.length > 0) {
      const fetchImages = async () => {
        const images = await Promise.all(
          auctions.map(async (auction) => {
            try {
              const imageUrl = await fetchDefaultImage(auction.id);
              return { [auction.id]: imageUrl };
            } catch (error) {
              console.error(error);
              return { [auction.id]: null };
            }
          })
        );
        const imageMap = images.reduce((acc, img) => ({ ...acc, ...img }), {});
        setAuctionImages(imageMap);
      };
      fetchImages();
    }
  }, [auctions]);

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
        return <h1 id="auction-title">My Auctions</h1>;
      } else {
        return <h1 id="auction-title">{`${user}'s Auctions`}</h1>;
      }
    } else {
      return <h1 id="auction-title">Auctions List</h1>;
    }
  }

  return (
    <>
      {
        <div className="container">
          {/* <div className="sidebar"></div> */}
          <div className="main-section">
            {auctions.map((auction) => {
              return (
                <AuctionItemComponent
                  auction={auction}
                  image={auctionImages[auction.id]}
                  key={auction.id}
                />
              );
            })}
          </div>
        </div>
      }
    </>
  );
};

export default ListAuctionsComponent;
