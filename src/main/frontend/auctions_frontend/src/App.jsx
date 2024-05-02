import "./App.css";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import LoginComponent from "./components/LoginComponent";
import { isUserLoggedIn } from "./services/AuthService";
import ListAuctionsComponent from "./components/ListAuctionsComponent";
import AuctionComponent from "./components/AuctionComponent";
import RegisterComponent from "./components/RegisterComponent";
import UserComponent from "./components/UserComponent";

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
        <HeaderComponent />
        <Routes>
          <Route path="/" element={<LoginComponent />}></Route>
          <Route
            path="/auctions"
            element={
              <AuthenticatedRoute>
                <ListAuctionsComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/add-auction"
            element={
              <AuthenticatedRoute>
                <AuctionComponent />
              </AuthenticatedRoute>
            }
          ></Route>

          <Route
            path="/update-auction/:id"
            element={
              <AuthenticatedRoute>
                <AuctionComponent />
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
          <Route path="/login" element={<LoginComponent />}></Route>
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
}

export default App;
