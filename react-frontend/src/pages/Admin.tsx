import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext"
import { useEffect, useState } from "react";


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

    const[bookId, setBookId] = useState("");
    const[bookName, setBookName] = useState("");
    const[bookDesc, setBookDesc] = useState("");
    const[authorId, setAuthorId] = useState("");
    const[authorName, setAuthorName] = useState("");
    const[genre, setGenre] = useState([]);

    const[count, setCount] = useState(0);
    const[buySites, setBuySites] = useState([]);
    const[imageUrl, setImageUrl] = useState("");
    const[price, setPrice] = useState(0.0);
    const[reviewIds, setReviewIds] = useState([]);

    async function addBook() {
        try{
            const bookData = {
                bookId,
                bookName,
                bookDesc,
                authorId: parseInt(authorId),
                authorName,
                genre: genre.map(g => g.trim()),
                count,
                buySites: buySites.map(site => site.trim()),
                imageUrl,
                price,
                reviewIds: reviewIds.map(id => id.trim())
            };

            console.log("Book Data to be sent:", bookData);

            const response = await api.post("/book/addBook", bookData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            console.log("Response from API:", response.data);
         
        }catch (error) {
            console.error("Error adding book:", error);
        }
    }
    

    // "bookId": "SOL-001",
    // "bookName": "Norwegian Wood",
    // "bookDesc": "A young man reflects on love and loss during his student years in Tokyo.",
    // "authorId": 19001,
    // "authorName": "Haruki Murakami",
    // "genre": ["Slice of Life"],
    // "count": 0,
    // "buySites": ["Amazon"],
    // "imageUrl": "https://covers.openlibrary.org/b/isbn/9780375704024-L.jpg",
    // "price": 14.50,
    // "reviewIds": []

    useEffect(() => {
        verifyAdmin();
    }, [token]);

  return (
    <div className="flex w-screen">
      <ul className="menu bg-base-200 rounded-box w-56 h-screen">
    <li><a href={"#addBook"}>Add Book</a></li>
    <li><a href={"#updateBook"}>Update Book</a></li>
    <li><button>Add Author</button></li>
    <li><button onClick={logout}>Logout</button></li>
    </ul>
    <div className="flex flex-col carousel carousel-vertical h-screen w-screen">
        <div id="addBook" className="w-full carousel-item  h-full flex flex-col">
            <h1 className="text-4xl font-seasons p-4 text-center">Add Book</h1>
            <div className="flex flex-row w-full justify-evenly p-4">

            <div className="w-1/2">
            <div className="flex flex-col gap-1 p-2 w-">
                <h1 className="text-xl font-seasons">Book ID</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setBookId(e.target.value)} />
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Book Name</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setBookName(e.target.value)} />
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Book Description</h1>
                <input className="input font-seasons" onChange={(e) => setBookDesc(e.target.value)}></input>
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Author ID</h1>
                <input type="text" className="input font-seasons" onChange={(e) => setAuthorId(e.target.value)} />
            </div>    
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Author Name</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setAuthorName(e.target.value)} />
            </div>
            </div>
            <div className="w-1/2">
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Genre</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setGenre(e.target.value.split(","))} />
            </div>

            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Buy Sites</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setBuySites(e.target.value.split(","))} />
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Image URL</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setImageUrl(e.target.value)} />
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Price</h1>
                <input type="number" className="input font-seasons " onChange={(e) => setPrice(parseFloat(e.target.value))} />
            </div>
            <div className="flex flex-col gap-1 p-2">
                <h1 className="text-xl font-seasons">Review IDs</h1>
                <input type="text" className="input font-seasons " onChange={(e) => setReviewIds(e.target.value.split(","))} />
            </div>

            </div>
            </div>
            <button className="btn text-center  max-w-50 m-4" onClick={addBook}>Add Book</button>

        </div>
        <div id="updateBook" className="w-full carousel-item h-screen">
            <h1 className="text-4xl font-seasons p-4 text-center">Update Book</h1>
        </div>
    </div>
    </div>

  )
}
