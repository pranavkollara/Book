import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import api from "../api/axios";
import { Link, useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

export default function Home() {
  const [bestsellers, setBestsellers] = useState([]);
  const [authors, setAuthors] = useState([]);

  async function fetchBestsellers() {
    try {
      const response = await api.get("/book/topFiveBooks");
      setBestsellers(response.data);
    } catch (error) {
      console.error("Error fetching bestsellers:", error);
    }
  }

  async function fetchAuthors() {
    try {
      const response = await api.get("/user/allAuthors");
      setAuthors(response.data);
    } catch (error) {
      console.error("Error fetching authors:", error);
    }
  }

  const { user } = useAuth();
  async function followAuthor(authorId) {
    try {
      const response = await api.post("/user/followAuthor/"+authorId)
      console.log("Followed author:", response.data); 
      // Optionally, you can update the authors state to reflect the follow action
    } catch (error) {
      console.error("Error following author:", error);
    }
  }

  const navigate = useNavigate();

  useEffect(() => {
    fetchBestsellers();
    fetchAuthors();
    console.log("Bestsellers:", bestsellers);
    console.log("Authors:", authors);
  }, []);

  return (
    <div className="bg-[url('/assests/background_white.png')] bg-cover  bg-center min-h-[calc(100vh+70px)] max-w-screen ">
      <Navbar></Navbar>
      <div className="flex flex-row items-center justify-center relative pt-25 h-fit">
        <div className="flex flex-col items-center ">
          <div className="w-[368px] h-[544px] bg-[#17320b] opacity-20"></div>
          <img
            src="/assests/front1.png"
            className="h-[465px] -translate-y-120"
          ></img>
        </div>
        <div className="flex flex-col items-center justify-center -translate-x-15">
          <div className="w-[370px] h-[370px] bg-[#0097b2] rounded-full opacity-36"></div>
          <img
            src="/assests/front2.png"
            className="h-[270px] -translate-y-80"
          ></img>
        </div>
        <div className="font-seasons text-[10rem] text-[#00357a] flex flex-col">
          <div className="-translate-y-30 -translate-x-65 transition-transform duration-300 ">
            The
          </div>
          <div className="-translate-y-60 -translate-x-25">Readers</div>
          <div className="-translate-y-85">Circle</div>
          <div className="text-xl w-80 -translate-y-82">
            Get inspired by our handpicked outfits. From casual to chic, find
            the perfect look for every occasion. Mix, match, and create your
            signature style with ease.
          </div>
          <Link
            to={"/genres"}
            className="bg-[#17320b] rounded-lg max-w-40 text-xl text-center transform text-white p-2 -translate-y-70 translate-x-40"
          >
            START YOUR BOOK HUNT
          </Link>
        </div>
      </div>
      <div className="-translate-y-40 h-fit">
        <div className="w-full flex flex-row items-center relative justify-around">
          <div className="bg-[#0097b254] w-1/3 h-full rounded-lg ml-28 p-4 ">
            <div className="flex gap-2 justify-center">
              <img src="/assests/bestseller.png" className="h-15"></img>
              <h1 className="text-white text-center font-rye text-5xl">
                BEST SELLERS
              </h1>
            </div>
            <ul className="list bg-transparent  rounded-box">
              {bestsellers.map((book, index) => (
                <Link to={`/book?bookName=${book.bookName}`} key={book.bookId}>
                  <li className="list-row">
                    <div className="text-4xl font-thin min-w-10 text-white tabular-nums font-rye ">
                      {index + 1}
                    </div>
                    <div>
                      <img className="h-14" src={book.imageUrl} />
                    </div>
                    <div className="list-col-grow">
                      <div className="font-amatic-sc text-3xl font-bold">
                        {book.bookName}
                      </div>
                      <div className="text-xs uppercase font-semibold opacity-60">
                        {book.authorName}
                      </div>
                    </div>
                  </li>
                </Link>
              ))}
            </ul>
          </div>
          <div><img src="/assests/homearrow.png" className="-translate-x-25 -translate-y-60 h-40"></img></div>
          <div className="text-5xl font-the-girl-next-door font-bold text-wrap w-60 text-center -translate-x-60 -translate-y-10">BINGE THESE BESTSELLER READS!</div>
        </div>
      </div>
      <div className="h-fit">
        
        <div className="w-full flex flex-row items-center relative justify-around -translate-x-20 pb-20">
          <div className="text-5xl font-the-girl-next-door font-bold text-wrap w-60 text-center translate-x-[calc(8.75rem+50%)] ">FOLLOW THESE TRENDING AUTHORS</div>
          <div><img src="/assests/homearrow.png" className="h-40 rotate-y-180 translate-x-35 -translate-y-50"></img></div>
          <div className="bg-[#0097b254] w-1/2 h-full rounded-lg ml-28 p-4 ">
            <div className="flex gap-2 justify-center">
              <img src="/assests/trending.png" className="h-15 -translate-y-2"></img>
              <h1 className="text-white text-center font-rye text-5xl">
                TRENDING AUTHORS
              </h1>
            </div>
            <ul className="list bg-transparent  rounded-box">
              {authors.map((author, index) => (
            
                  <li className="list-row">
                    <div className="text-4xl font-thin min-w-10 text-white tabular-nums font-rye ">
                      {index + 1}
                    </div>
                    <div>
                      <img className="h-14 rounded-full" src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp" />
                    </div>
                    <div className="list-col-grow">
                      <div className="font-amatic-sc text-3xl font-bold">
                        {author.authorName}
                      </div>
                      <div className="text-xs uppercase font-semibold opacity-60">
                       Followers : 0
                      </div>
                    </div>
                      <button className="btn" onClick={() => followAuthor(author.authorId)}>FOLLOW</button>
                  </li>
               
              )).slice(0,4)}
            </ul>
          </div>
          
        </div>
      </div>
    </div>
  );
}
