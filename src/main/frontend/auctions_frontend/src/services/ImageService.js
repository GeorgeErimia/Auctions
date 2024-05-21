// import axios from "axios";

// const IMAGE_REST_API_URL = "http://localhost:8080/api/v2/images";

// export async function fetchImages(auctionId) {
//   axios
//     .get(IMAGE_REST_API_URL + `/auction/${auctionId}/data`)
//     .then((response) => {
//       const imageData = response.data;

//       const imagePromises = imageData.map(async (imageInfo) =>
//         getImage(imageInfo.id)
//       );

//       return Promise.all(imagePromises);
//     })
//     .catch((error) => {
//       console.error(error);
//     });
// }

// export async function getImage(imageId) {
//   axios
//     .get(IMAGE_REST_API_URL + `/${imageId}/image`, {
//       responseType: "arraybuffer",
//     })
//     .then((response) => {
//       const imageBlob = new Blob([response.data], { type: "image/jpeg" });
//       return URL.createObjectURL(imageBlob);
//     })
//     .catch((error) => {
//       console.error(error);
//     });
// }

import axios from "axios";

const IMAGE_REST_API_URL = "http://localhost:8080/api/v2/images";

export async function fetchImages(auctionId) {
  try {
    const response = await axios.get(
      IMAGE_REST_API_URL + `/auction/${auctionId}/data`
    );
    const imageData = response.data;

    const imagePromises = imageData.map(async (imageInfo) =>
      getImage(imageInfo.id)
    );

    return Promise.all(imagePromises);
  } catch (error) {
    console.error(error);
    throw error; // Rethrow the error to handle it in the component
  }
}

export async function fetchDefaultImage(auctionId) {
  try {
    const response = await axios.get(
      IMAGE_REST_API_URL + `/auction/${auctionId}/default/data`
    );
    const imageInfo = response.data;

    return getImage(imageInfo.id);
  } catch (error) {
    console.error(error);
    throw error; // Rethrow the error to handle it in the component
  }
}

export async function getImage(imageId) {
  try {
    const response = await axios.get(IMAGE_REST_API_URL + `/${imageId}/image`, {
      responseType: "arraybuffer",
    });
    const imageBlob = new Blob([response.data], { type: "image/jpeg" });
    return URL.createObjectURL(imageBlob);
  } catch (error) {
    console.error(error);
    throw error; // Rethrow the error to handle it in the component
  }
}
