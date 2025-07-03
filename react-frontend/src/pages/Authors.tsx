import React, { useEffect, useState } from 'react'
import api from '../api/axios';
import Navbar from '../components/Navbar';

export default function Authors() {

    const [authors, setAuthors] = useState([]);

    async function fetchAuthors() {
        try {
            const response = await api.get("/user/allAuthors") // Replace with your API endpoint
            if (!response) {
                throw new Error('Network response was not ok');
            }
            
            setAuthors(response.data);
        } catch (error) {
            console.error('Error fetching authors:', error);
        }
    }

    async function followAuthor(authorId) {
        try {
            const response = await api.post(`/user/followAuthor/${authorId}`); // Replace with your API endpoint
            if (!response) {
                throw new Error('Network response was not ok');
            }
            // Optionally, you can update the state or show a success message
            console.log('Followed author successfully:', response.data);
            alert('Followed author successfully!');
        } catch (error) {
            console.error('Error following author:', error);
        }
    }

    useEffect(() => {
        fetchAuthors();
    }, []);


  return (
     <div className="relative min-h-screen w-screen h-full ">
      <div className="bg-[url('/assests/bookback.jpg')] opacity-20 bg-center h-full absolute inset-0 -z-10"></div>
        <Navbar></Navbar>
      <div className="relative z-10  ">
        <div className="flex flex-row items-center justify-center text-center">
            <img src='/assests/authorpageimg.png' className='h-28'></img>
            <h1 className='text-4xl font-edu-nsw-act-hand-pre font-bold  mt-4'>Writers you adore</h1>
        </div>

        </div>
        <div className="flex flex-col flex-wrap justify-center items-center font-amatic-sc gap-4 p-10 mt-8  w-[80%]  relative m-auto">
             <div className="bg-[#ece7d6] rounded-tr-[7rem] rounded-bl-[7rem] opacity-50 bg-center h-full absolute inset-0 z-10"></div>
            {authors.map((author) => (
                <>
                <div className='flex justify-between items-center gap-4  w-[90%] z-40 opacity-100'>
                    <div>
                    <img src={"https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"} alt={author.authorName} className='h-24 w-24 rounded-full' />
                    </div>
                    <div className='w-[80%]'>
                    <h2 className='text-2xl font-bold'>{author.authorName}</h2>
                    <p className='text-lg'>{author.authorDesc}</p>
                    </div>
                    <div>
                        <button className='btn bg-[#00357a] rounded-2xl text-[#e6ddc1] font-poppins hover:text-[#00357a] hover:bg-[#e6ddc1] transition duration-300 ease-in' onClick={() => followAuthor(author.authorId)}>Follow</button>
                    </div>
                </div>
                    <img src="/assests/line.png" className='w-96'></img>
                </>
            )).slice(0, 4)}
            </div>
    </div>
  )
}
