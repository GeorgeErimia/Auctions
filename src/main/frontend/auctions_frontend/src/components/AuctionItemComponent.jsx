import React from "react";
import { useNavigate } from "react-router-dom";

const AuctionItemComponent = (props) => {
  const auction = props.auction;
  const image = props.image;

  const navigator = useNavigate();

  return (
    <div className="auction-element">
      <div className="image-section">
        <img
          src={image || ""}
          alt=""
          width="100%"
          height="100%"
          id="auction-primary-image"
          onClick={() => {
            navigator(`/auctions/${auction.id}`);
          }}
        />
      </div>
      <div className="title-section">
        <a href={`auctions/${auction.id}`} id="auction-name-anchor">
          {auction.name}
        </a>
      </div>
      <div className="description-section">
        <pre className="auction-description">{auction.description}</pre>
        {/* Here you place bidders, etc */}
      </div>
      <div className="actions-section">
        <button
          id="btn-auction-view"
          onClick={() => navigator(`/auctions/${auction.id}`)}
        >
          View Auction
        </button>
      </div>
    </div>
  );
};

export default AuctionItemComponent;
