
import { useEffect, useState } from 'react';
import api from '../api/axios';
import { useAuth } from '../auth/AuthContext'
import { Link } from 'react-router-dom';

export default function Profile() {

    const { user ,token} = useAuth();



    const [author, setAuthor] = useState(null);

    async function fetchAuthor() {
      try{
        if (!user || !token) {
          
          return;
        }
        const response = await api.get("/auth/userRole/"+user.username);
        console.log("Response from API:", response.data);
        if(response.data[0].authority !== "ROLE_AUTHOR"){return}
       
        //  response = await api.get("/user/getAuthor)")
        console.log("Author data:", response.data);
        setAuthor(response.data);
      }catch (error) {
        console.error("Error fetching author data:", error);
      }
    }

    useEffect(() => {
        if (token) {
            fetchAuthor();
        }
    },[])

    const { logout } = useAuth();


    if (!user || !token ) {
        return <>
        <div className="flex gap-4">
                    <a href="/login" className="btn bg-[#00357a] font-dm-sans shadow-none text-white border-none">LOGIN</a>
                    <a href="/signup" className="btn bg-[#331d0f] font-dm-sans shadow-none text-white border-none">REGISTER</a>
                </div>
        </>
    }

  return (
    <div className='z-50 opacity-90'>
       <>
              <div className="flex gap-2">
   
    <div className="dropdown z-20">
      <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
        <div className="w-10 rounded-full">
          <img
            alt="Tailwind CSS Navbar component"
            src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp" />
        </div>
      </div>
      <ul
        tabIndex={0}
        className="menu menu-sm dropdown-content bg-base-100 rounded-box  mt-3 w-52 p-2 shadow right-2 top-12 z-[999]">
          <li className="menu-title">Welcome {user.username}</li>

        <li>
          <Link to={"/wishlist"}>Wishlist</Link>
          </li>
                  <li><Link to={"/settings"}>Settings</Link></li>
        <li><a onClick={logout}>Logout</a></li>
      </ul>
    </div>
  </div>
              </>
    </div>
  )
}
