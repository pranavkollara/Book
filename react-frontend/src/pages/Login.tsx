import React, { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  const { login ,setUserData } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post(
        "/auth/login",
        {
          username,
          password,
        },
        {
          headers: {
            Authorization: `Bearer `, // Basic auth header
          },
        }
      );

      console.log(response);
      if (response.status === 200) {

        login(response.data);
        try{
          const userResponse = await api.get("/user/getUser");
          if (!userResponse) {
            throw new Error("Network response was not ok");
          }
          setUserData(userResponse.data);

          console.log("User data:", userResponse.data);
          navigate("/home"); // Redirect to dashboard after successful login
        }catch(error) {
          console.error("Error fetching user data:", error);
          alert("Failed to fetch user data. Please try again later.");
        }


      }
    } catch (error) {
      console.error("Login failed:", error);
      alert("Login failed. Please check your credentials.");
    }
  };

  return (
    <div className="bg-[url('/assests/login_background.png')] bg-cover bg-center h-screen flex items-center justify-center">
      <div className="bg-[#d5f8e3] absolute h-[80vh] w-[30vw] rounded-4xl opacity-70">
       
      </div>
      <div className="absolute top-8 right-210">
        <img
          src="/assests/loginregisterbookie.png"
          alt="Logo"
          className="h-[20vh] w-[20vw] object-contain opacity-70"
        />
      </div>
      <div className="z-10 flex flex-col items-center justify-center gap-4 bg-[#d5f8e3] bg-transparent">
        <p className="font-chewy text-2xl pb-8">Login</p>
        <label className="input rounded-full  backdrop-opacity-35 brightness-75 w-[20vw] opacity-60 border-none">
          <svg
            className="h-[1em] opacity-50"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
          >
            <g
              strokeLinejoin="round"
              strokeLinecap="round"
              strokeWidth="2.5"
              fill="none"
              stroke="currentColor"
            >
              <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
              <circle cx="12" cy="7" r="4"></circle>
            </g>
          </svg>
          <input
            type="text"
            required
            placeholder="Username"
            pattern="[A-Za-z][A-Za-z0-9\-]*"
            minLength="3"
            maxlength="30"
            title="Only letters, numbers or dash"
            onChange={(e) => setUsername(e.target.value)}
          />
        </label>

        <label className="input rounded-full  backdrop-opacity-35 brightness-75 w-[20vw] opacity-60 border-none">
          <svg
            className="h-[1em] opacity-50"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
          >
            <g
              strokeLinejoin="round"
              strokeLinecap="round"
              strokeWidth="2.5"
              fill="none"
              stroke="currentColor"
            >
              <path d="M2.586 17.414A2 2 0 0 0 2 18.828V21a1 1 0 0 0 1 1h3a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h1a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h.172a2 2 0 0 0 1.414-.586l.814-.814a6.5 6.5 0 1 0-4-4z"></path>
              <circle cx="16.5" cy="7.5" r=".5" fill="currentColor"></circle>
            </g>
          </svg>
          <input
            type="password"
            required
            placeholder="Password"
            minlength="8"
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
            title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>

        <a className="link link-hover font-poppins text-gray-400 italic opacity-70">Forgot Password?</a>
        
        <div>

        <button
          className="btn bg-[#0097b2] rounded-full w-[20vw] font-poppins opacity-70 text-white"
          onClick={handleSubmit}
          >
          Login
        </button>
        <div className="divider">OR</div>
        <button
          className="btn bg-[#0097b2] rounded-full w-[20vw] font-poppins opacity-70 text-white"
          onClick={() => {
            navigate.to("/signup"); }
          }
        >
          Sign Up
        </button>
            </div>


      </div>
    </div>
  );
}
