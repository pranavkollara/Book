import { Link, useNavigate } from "react-router-dom";
import Profile from "./Profile";
import { useState } from "react";

export default function Navbar() {

  const [searchQuery, setSearchQuery] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    navigate(`/search?name=${searchQuery}`);
 
  }

  
  return (
    <div className="sticky top-0 z-50 opacity-90 max-w-screen">
      <nav className="flex items-center justify-between pl-2 pr-8 max-h-[70px] bg-[#17320b]">
        <div className="flex items-center gap-4 pl-2">

            <div>
            <img className="h-18 -translate-y-1" src="/assests/logo.png"></img>
            </div>
            <div className=" ml-2 font-dm-sans text-white text-xl gap-7 flex">
          <Link className="link link-hover" to={"/home"}>HOME</Link>
          <Link className="link link-hover" to={"/genres"}>GENRES</Link>
          <Link className="link link-hover" to={"/authors"}>AUTHORS</Link>
          <Link className="link link-hover" to={"/posts"}>POSTS</Link>
            </div>
        </div>
        <div className="flex items-center gap-4">
            <form onSubmit={handleSubmit} className="flex items-center">

            <label className="input rounded-full">
  <svg className="h-[2em] opacity-50" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
    <g
      strokeLinejoin="round"
      strokeLinecap="round"
      strokeWidth="2.5"
      fill="none"
      stroke="currentColor"
    >
      <circle cx="11" cy="11" r="8"></circle>
      <path d="m21 21-4.3-4.3"></path>
    </g>
  </svg>
  


  <input type="search" onChange={(e) =>setSearchQuery(e.target.value)}   required placeholder="" />

  
</label>
            </form>
           
             <Profile></Profile>
           
              
              
          
        </div>
        
      </nav>
    </div>
  )
}
