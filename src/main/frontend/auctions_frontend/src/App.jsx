import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import LoginComponent from "./components/LoginComponent";
import { isUserLoggedIn } from "./services/AuthService";
import ListAuctionsComponent from "./components/ListAuctionsComponent";
import AuctionFormComponent from "./components/AuctionFormComponent";
import RegisterComponent from "./components/RegisterComponent";
import UserComponent from "./components/UserComponent";
import HomeComponent from "./components/HomeComponent";
import AuctionComponent from "./components/AuctionComponent";
import LoginComponentV2 from "./components/LoginComponentV2";
import HeaderComponentV2 from "./components/HeaderComponentV2";
import ImageInputFormComponent from "./components/ImageInputFormComponent";

function App() {
  function AuthenticatedRoute({ children }) {
    const isAuth = isUserLoggedIn();

    if (isAuth) {
      return children;
    }

    return <Navigate to="/" />;
  }

  return (
    <>
      <BrowserRouter>
        {/* <HeaderComponent /> */}
        {isUserLoggedIn() && <HeaderComponentV2 />}
        <Routes>
          <Route path="/" element={<HomeComponent />}></Route>
          <Route
            path="/auctions"
            element={
              <AuthenticatedRoute>
                <ListAuctionsComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/auctions/:id"
            element={
              <AuthenticatedRoute>
                <AuctionComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/add-auction"
            element={
              <AuthenticatedRoute>
                <AuctionFormComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/update-auction/:id"
            element={
              <AuthenticatedRoute>
                <AuctionFormComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/user/:username"
            element={
              <AuthenticatedRoute>
                <UserComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route path="/register" element={<RegisterComponent />}></Route>
          <Route path="/login" element={<LoginComponentV2 />}></Route>

          <Route
            path="/add-images"
            element={<ImageInputFormComponent></ImageInputFormComponent>}
          ></Route>
        </Routes>
        {/* <FooterComponent /> */}
      </BrowserRouter>
    </>
  );
}

export default App;
