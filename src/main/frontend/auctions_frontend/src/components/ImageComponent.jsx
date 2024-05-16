import axios from "axios";
import React, { useEffect, useState } from "react";

const ImageComponent = (id) => {
  const [imageSrc, setImageSrc] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v2/images/${id}/image`)
      .then((response) => {
        const imageBlob = response.blob();
        const imageUrl = URL.createObjectURL(imageBlob);
        setImageSrc(imageUrl);
      })
      .catch((error) => console.error(error));
  }, [id]);

  return (
    <div>
      {imageSrc && <img src={imageSrc} alt={`Image with id: ${id}`}></img>}
    </div>
  );
};

export default ImageComponent;
