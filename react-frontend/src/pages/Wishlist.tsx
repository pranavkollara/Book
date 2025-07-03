import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext";

export default function Wishlist() {
  const { user } = useAuth();

  const [books, setBooks] = useState([]);

  async function fetchWishlist() {
    try {
      const response = await api.get(
        "/wishlist/getBooksFromWishlist/" + user.userId
      );

      const bookResponse = response.data.map((id) =>
        api.get("/book/getBookById/" + id)
      );
      const bookData = await Promise.all(bookResponse);
      console.log("Book data:", bookData);
      setBooks(bookData.map((res) => res.data));
    } catch (error) {
      console.error("Error fetching wishlist:", error);
    }
  }

  async function removeFromWishlist(bookId) {
    try{
      const response = await api.delete(
        "/wishlist/deleteBookFromWishlist/" + bookId
      );
      console.log("Removed from wishlist:", response.data);
      fetchWishlist(); // Refresh the wishlist after removal
    }
    catch (error) {
      console.error("Error removing book from wishlist:", error);
    }
  }

  useEffect(() => {
    fetchWishlist();
  }, [user]);

  return (
    <div>
      <div className="bg-[url('/assests/bookback.jpg')] opacity-20 bg-center h-full absolute inset-0 -z-10"></div>
      <Navbar />
      <div className="p-10">
        <h1 className="text-7xl font-seasons text-[#053827] font-bold mt-4">
          MY WISHLIST
        </h1>
        <div className="border-6 outline-6 outline-[#05382770] outline-offset-4  border-[#05382733] p-10 mt-8 border-opacity-50 ">
          {books.map((book, index) => (
            <div
              className="flex flex-row items-center justify-between gap-10 mb-10"
              key={index}
            >
              <div className="flex flex-row items-center gap-10">
                <img src={book.imageUrl} className="h-[10rem]"></img>
                <div>
                  <div
                    key={index}
                    className="text-4xl font-amatic-sc font-bold mb-4"
                  >
                    {book.bookName}
                  </div>
                  <div
                    key={index}
                    className="text-2xl font-amatic-sc font-bold mb-4"
                  >
                    {book.authorName}
                  </div>
                </div>
              </div>
              <img src="/assests/wishlistbook.png" className="h-[8rem]" onClick={() => removeFromWishlist(book.bookId)}></img>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
