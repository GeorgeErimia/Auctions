import React from "react";

const FooterComponent = () => {
  return (
    <footer
      className="footer footer-component"
      style={{
        position: "absolute",
        bottom: "0",
        width: "100%",
        height: "60px",
        backgroundColor: "black",
        color: "white",
        textAlign: "center",
      }}
    >
      <p className="text-center">Auctions App By George Erimia</p>
    </footer>
  );
};

export default FooterComponent;
