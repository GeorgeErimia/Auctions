import React, { useState } from "react";

const ImageInputFormComponent = () => {
  const [image, setImage] = useState("");
  const images = "";
  return (
    <div>
      <label>Choose Images to Upload</label>
      <input
        type="file"
        id="images"
        name="images"
        accept="image/png, image/jpeg"
        // value={images}
        // onChange={(e) => {
        //   console.log(images);
        //   setImage(e.target.value);
        // }}
        multiple
      />
      <img src={image} alt="" />
    </div>
  );
};

export default ImageInputFormComponent;
