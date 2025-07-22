import React, { useEffect, useState } from 'react'
import Navbar from '../components/Navbar';
import { useAuth } from '../auth/AuthContext';
import api from '../api/axios';

export default function CreatePost() {

    const { token } = useAuth();

    const date = new Date();
    console.log("Current Date:", date.toLocaleDateString());

    const [author, setAuthor] = useState(null);
    const [postContent, setPostContent] = useState("");

    async function fetchAuthor() {
        try {
            if (!token) {
                return;
            }
            const response = await api("/auth/loadUser", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            
            // Assuming the author data is in the response
            console.log("Author data:", response.data);
            setAuthor(response.data);
        } catch (error) {
            console.error("Error fetching author data:", error);
        }
    }

    async function createPost() {
        try {
            if (!author || !postContent) {
                console.error("Author or post content is missing");
                return;
            }
            const response = await api.post("/post/createPost", {
                authorId: author.id,
                content: postContent,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log("Post created successfully:", response.data);
            setPostContent(""); // Clear the input after successful post creation
        } catch (error) {
            console.error("Error creating post:", error);
        }
    }

    useEffect(() => {
        if (token) {
            fetchAuthor();
        }
    }, [token]);

  return (
    <div className="bg-[url('/assests/login_background.png')] bg-cover bg-center h-[calc(100vh+90px)]">
        <Navbar></Navbar>
        <div  className='flex flex-col gap-10 items-center justify-center w-2/3 ml-auto mr-auto'>
            <div className='flex gap-4 items-center pt-8'>
                <img src="/assests/authorsalley.png" className='h-15'></img>
                <h1 className='text-5xl font-mea-culpa mt-4'>Author's Alley</h1>


            </div>
                <div className='bg-white p-8 rounded-tr-4xl w-1/2 relative flex flex-col items-center'>
                <img src='/assests/authoralleyborder.png' className='absolute h-80 top-0 left-0 -translate-y-4.5 -translate-x-4.5'></img>
                    <div className='flex gap-2 justify-between w-full'>
                        <div className='flex gap-4 items-center'>
                            <img src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp" className='h-10 rounded-full'/>
                        <div className='flex flex-col justify-center'>
                            <h1 className='font-amatic-sc font-bold text-3xl'>{author?.username}</h1>
                            <h1 className='font-amatic-sc text-xl'>{date.toLocaleDateString()}</h1>
                        </div>
                            </div>
                        <img src='/assests/authoralleybook.png' className='h-10 m-5'></img>
                    </div>
                    <input onChange={(e) => setPostContent(e.target.value)} className='w-[90%] h-80 mt-4 bg-[#103b32] rounded-4xl opacity-30 font-bold font-amatic-sc text-white align-text-top p-4 text-2xl placeholder:text-white placeholder:font-amatic-sc placeholder:font-bold' type='text' placeholder='Write your post here...'>
                    </input>
                    <div>
                        <button className='bg-[#103b32]  text-white font-amatic-sc font-bold text-2xl rounded-xl px-8 py-2 mt-4 hover:bg-[#0c2a24]' onClick={createPost}>Create Post</button>
                    </div>
                <img src='/assests/authoralleyborder.png' className='absolute h-80 bottom-0 right-0 translate-y-4.5 translate-x-4.5 rotate-180'></img>

                </div>


        </div>

    </div>
      
 
  )
}
