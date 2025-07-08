import { useEffect, useState } from "react";
import {
  Link,
  useLocation,
  useParams,
  useSearchParams,
} from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import SearchMainBook from "../components/SearchMainBook";
import SimilarBook from "../components/SimilarBook";

export default function Search() {

  const [loading, setLoading] = useState(true);

  const location = useLocation();
  const [param] = useSearchParams();

  const genre = param.get("genre");

  const name = param.get("name");

  const [results, setResults] = useState([{}]);

  async function fetchBooksByGenre() {
    try {
      if (genre) {
        const respone = await api.get("/book/getBookByGenre/" + genre);
        console.log("Response from API:", respone.data);
        if (respone.data && respone.data.length > 0) {
          setResults((prev) => [...prev, ...respone.data]);
        }
      }
    } catch (e) {
      console.error("Error parsing genre from URL:", e);
    }
  }

  async function fetchBooksByName() {
    try {
      if (name) {
        const respone = await api.get("/book/getBooksByName/" + name);
        console.log("Response from API:", respone.data);
        if (respone.data && respone.data.length > 0) {
          setResults((prev) => [...prev, ...respone.data]);
        }
      }
    } catch (e) {
      console.error("Error parsing genre from URL:", e);
    }
  }

  useEffect(() => {
    setResults([{}]); // Reset results on new search

    fetchBooksByName();
    fetchBooksByGenre();
  }, [genre, name, location.search]);

  return (
    <div className="relative min-h-screen">
      <div className="bg-[url('/assests/genre_background.png')] opacity-80 bg-center h-full absolute inset-0"></div>
      <Navbar></Navbar>
      <div className="flex flex-col pr-4 relative z-10">
        <h1 className="text-7xl font-seasons text-[#053827] font-bold p-10 pb-0">
          Search Results
        </h1>

        <div>
          {results.length > 1 ? (
            results
              .map(
                (book, index) =>
                  book.bookName && (
                    <Link
                      to={"/book?bookName=" + book?.bookName}
                      key={index}
                      className="p-4 pl-10 flex gap-2 font-amatic-sc"
                    >
                      
                        <SearchMainBook book={book}></SearchMainBook>
                    
                    </Link>
                  )
              )
              .slice(0, 2)
          ) : (
            <div className="p-4 text-center text-7xl font-seasons">
              No books found.
            </div>
          )}
        </div>

        {results.length > 2 && (
          <div>
            <h1 className="font-seasons text-2xl pl-10">Similar Books</h1>
          </div>
        )}
        <div className="carousel">
          {results.length > 2 &&
            results
              .map(
                (book, index) =>
                  book.bookName && (
                    <Link
                      to={"/book?bookName=" + book?.bookName}
                      key={index}
                      className="p-4 flex flex-col gap-2 font-amatic-sc"
                    >
                      <SimilarBook book={book}></SimilarBook>
                    </Link>
                  )
              )
              .splice(2)}
        </div>
      </div>
    </div>
  );
}
