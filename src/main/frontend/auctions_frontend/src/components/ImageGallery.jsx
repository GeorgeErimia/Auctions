import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "../image-gallery.css";

const ImageGallery = (props) => {
  const images = props.images;

  const [imageIndex, setImageIndex] = useState(0);

  const [activeImage, setActiveImage] = useState(images[0]);

  function nextImage() {
    if (imageIndex < images.length - 1) {
      setImageIndex(imageIndex + 1);
    }
    // console.log("index: " + imageIndex);
    // console.log("length: " + images.length);

    // setActiveImage(images[imageIndex]);
  }

  function prevImage() {
    if (imageIndex > 0) {
      setImageIndex(imageIndex - 1);
    }
    // console.log("index: " + imageIndex);
    // console.log("length: " + images.length);

    // setActiveImage(images[imageIndex]);
  }

  useEffect(() => {
    setActiveImage(images[imageIndex]);
  }, [images, imageIndex]);

  return (
    <div className="container-gallery">
      {/* {images.map((image, index) => {
        return (
          <div className="slide" key={image}>
            <div className="numbertext">{images.length}</div>
          </div>
        );
      })} */}

      <div className="slide">
        <img src={activeImage} alt="" className="image-active" />
        <a className="image-prev" onClick={() => prevImage()}>
          ❮
        </a>
        <a className="image-next" onClick={() => nextImage()}>
          ❯
        </a>
      </div>

      {/* <a className="prev" onClick={plusSlides(-1)}></a>
      <a className="next" onClick={plusSlides(1)}></a> */}

      <div className="slider-row">
        {images.map((image, index) => {
          return (
            <div className="column" key={image}>
              <img
                src={image}
                width="60px"
                height="48px"
                onClick={() => {
                  setActiveImage(image);
                  imageIndex = index;
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ImageGallery;
