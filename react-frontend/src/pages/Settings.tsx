import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import { useAuth } from "../auth/AuthContext";
import { useState } from "react";


export default function Settings() {

    const {user} =  useAuth();
    console.log("User in settings:", user);

    const [updateUsername, setUpdateUsername] = useState(user.username);
    
    const [updateProfilePic, setUpdateProfilePic] = useState(user.profilePic);

  return (
  <div className="relative min-h-screen w-screen h-screen ">
  <Navbar></Navbar>
  <div className="relative z-10 flex  h-full">

  <ul className="menu bg-base-200 h-full w-56">
  <li><Link to={"#profile"}>Profile</Link></li>

</ul>

    <div className="p-10 flex flex-col font-seasons  items-center w-full gap-4">
        <h1 className=" text-4xl">Update Profile</h1>
    <div className="w-full flex flex-col gap-4 ">
        <h1 className="font-seasons text-2xl">Username</h1>
        <input type="text" className="input font-seasons" placeholder={user.username} onChange={(e) => setUpdateUsername(e.target.value)} />
        <button className="btn max-w-[20rem]" onClick={() => console.log("Update username to:", updateUsername)}>Update Username</button>
    </div>
    <div className="w-full flex flex-col gap-4">
        <h1 className="font-seasons text-2xl">Profile Picture</h1>
        <img src={user.profilePic || "https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"} className="h-24 w-24 rounded-full" />
        <input type="file" className="file-input" />
        <button className="btn max-w-[20rem]" onClick={() => console.log("Update username to:", updateUsername)}>Update Profile Picture</button>
    </div>

    </div>
  </div>


  </div>

     
  )
}
