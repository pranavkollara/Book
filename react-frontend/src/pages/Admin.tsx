import { useNavigate } from "react-router-dom";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext"
import { useEffect } from "react";


export default function Admin() {

    const {token,logout} = useAuth();
    console.log("Token in Admin:", token);  

    const navigate = useNavigate();

    async function verifyAdmin() {
        try{
            const response = await api.get("/auth/verifyAdmin",{
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
            console.log("Response from API:", response.data);
            if (!response) {
                throw new Error('Network response was not ok');
            }
        }catch (error) {
            console.error("Error verifying admin:", error);
            logout();
            navigate("/login");
        }
    }

    useEffect(() => {
        verifyAdmin();
    }, [token]);

  return (
    <div className="flex w-screen">
      <ul className="menu bg-base-200 rounded-box w-56 h-screen">
    <li><button>Add Book</button></li>
    <li><button>Add Author</button></li>
    <li><button onClick={logout}>Logout</button></li>
    </ul>

<div className="w-full justify-center items-center">
    <h1 className="text-4xl font-seasons p-4 text-center">Add Book</h1>
</div>
    </div>

  )
}
