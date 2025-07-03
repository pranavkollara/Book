import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import { useEffect, useState } from "react";
import api from "../api/axios";
import Profile from "../components/Profile";
import Navbar from "../components/Navbar";

export default function Genres() {
  const { user } = useAuth();
  const navigate = useNavigate();

  console.log(user);

  const [comedy, setComedy] = useState([{}]);
  const [romance, setRomance] = useState([{}]);

  const [sliceoflife, setSliceOfLife] = useState([{}]);

  async function fetchSliceOfLife() {
    try {
      const response = await api.get("/book/getBookByGenre/Slice of Life");
      console.log("Response from API:", response.data);
      if (response.data && response.data.length > 0) {
        setSliceOfLife(response.data);
      } else {
        setSliceOfLife([]);
      }
    } catch (error) {
      console.error("Error fetching slice of life genre:", error);
    }
  }

  async function fetchComedy() {
    try {
      const response = await api.get("/book/getBookByGenre/Comedy");
      console.log("Response from API:", response.data);
      if (response.data && response.data.length > 0) {
        setComedy(response.data);
      } else {
        setComedy([]);
      }
    } catch (error) {
      console.error("Error fetching comedy genre:", error);
    }
  }

  async function fetchRomance() {
    try {
      const response = await api.get("/book/getBookByGenre/Romance");
      console.log("Response from API:", response.data);
      if (response.data && response.data.length > 0) {
        setRomance(response.data);
      } else {
        setRomance([]);
      }
    } catch (error) {
      console.error("Error fetching comedy genre:", error);
    }
  }

  useEffect(() => {
    fetchComedy();
    fetchRomance();
    fetchSliceOfLife();
  }, []);

  return (
    <div className="relative h-full">
        <Navbar></Navbar>
      <div className="bg-[url('/assests/genre_background.png')] opacity-80 bg-center h-full absolute inset-0"></div>
      <div className="flex justify-between items-center pr-4 relative z-10">
        <div className="p-4 flex items-center gap-4 w-full">
          
          <div className="font-moon-dance text-[5vw]  font-thin">Genres</div>
        </div>
        

         
        
      </div>

      <div className="flex flex-col gap-4 justify-center items-center pb-10">
        <div className="flex flex-col   text-5xl  w-[90%] relative mb-10">
          <Link
            to={"/search?genre=Slice of Life"}
            className="font-amatic-sc font-bold ml-20  z-10 link link-hover"
          >
            Slice of Life
          </Link>
          <img
            className="h-52 w-[100%] rounded-full absolute top-11  opacity-80 z-10 "
           
            src="/assests/genre_strip3.png"
            alt="Genre 1"
          />

          <div className="flex flex-wrap justify-center gap-4 mt-5 z-20">
            {sliceoflife
              .map((book, index) =>
                index == 5 ? (
                  <>
                  <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      
                      />
                      </Link>
                  </>
                ) : (
                  <>
                  <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      onClick={() => navigate("/book?bookName=" + book?.bookName)}
                      />
                      </Link>
                    <div
                      key={index + 10}
                      className="divider lg:divider-horizontal"
                    ></div>
                  </>
                )
              )
              .slice(0, 6)}
          </div>
        </div>
      </div>

      <div className="flex flex-col gap-4 justify-center items-center pb-10">
        <div className="flex flex-col   text-5xl  w-[90%] relative mb-10">
          <Link
            to={"/search?genre=Comedy"}
            className="font-amatic-sc font-bold ml-20  z-10 link link-hover"
          >
            Comedy
          </Link>
          <img
            className="h-52 w-[100%] rounded-full absolute top-12  opacity-80 z-10 "
            onClick={() => navigate("/home")}
            src="/assests/genre_strip1.png"
            alt="Genre 1"
          />

          <div className="flex flex-wrap justify-center gap-4 mt-5 z-20">
            {comedy
              .map((book, index) =>
                index == 5 ? (
                  <>
                   <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      />
                      </Link>
                  </>
                ) : (
                  <>
                   <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      />
                      </Link>
                    <div
                      key={index + 10}
                      className="divider lg:divider-horizontal"
                    ></div>
                  </>
                )
              )
              .slice(0, 6)}
          </div>
        </div>
      </div>
      <div className="flex flex-col gap-4 justify-center items-center pb-10">
        <div className="flex flex-col   text-5xl  w-[90%] relative mb-10">
          <Link
            to={"/search?genre=Romance"}
            className="font-amatic-sc font-bold ml-20  z-10 link link-hover"
          >
            Romance
          </Link>
          <img
            className="h-52 w-[100%] rounded-full absolute top-11  opacity-80 z-10 "
            onClick={() => navigate("/home")}
            src="/assests/genre_strip4.png"
            alt="Genre 1"
          />

          <div className="flex flex-wrap justify-center gap-4 mt-5 z-20">
            {romance
              .map((book, index) =>
                index == 5 ? (
                  <>
                   <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      />
                      </Link>
                  </>
                ) : (
                  <>
                   <Link to={"/book?bookName=" + book?.bookName}>
                    <img
                      key={index}
                      src={book?.imageUrl}
                      alt={book?.bookName}
                      className="w-28 h-40 object-cover"
                      />
                      </Link>
                    <div
                      key={index + 10}
                      className="divider lg:divider-horizontal"
                    ></div>
                  </>
                )
              )
              .slice(0, 6)}
          </div>
        </div>
      </div>
    </div>
  );
}
