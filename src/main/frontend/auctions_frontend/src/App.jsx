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
        </Routes>
        {/* <FooterComponent /> */}
      </BrowserRouter>
    </>
  );
}

export default App;
