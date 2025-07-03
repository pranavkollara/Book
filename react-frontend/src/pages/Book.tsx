import React, { use, useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Profile from "../components/Profile";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext";
import Navbar from "../components/Navbar";

export default function Book() {
  const { token, user } = useAuth();

  const [book, setBook] = useState({});

  const [tab, setTab] = useState(0);

  const [moreByAuthor, setMoreByAuthor] = useState([]);
  const [similarBooks, setSimilarBooks] = useState([]);

  const [userReview, setUserReview] = useState("");
  const [userRating, setUserRating] = useState(0);

  const [reviews, setReviews] = useState([]);

  const location = useLocation();

  const params = new URLSearchParams(location.search);
  const bookName = params.get("bookName");

  async function fetchBook() {
    try {
      const response = await api.get(`/book/getBookByName/${bookName}`);
      if (response.status === 200) {
        setBook(response.data);
      } else {
        console.error("Failed to fetch book data");
      }
    } catch (error) {
      console.error("Error fetching book:", error);
    }
  }

  async function fetchMoreByAuthor() {
    try {
      const response = await api.get(
        `/book/getBookByAuthorName/${book.authorName}`
      );
      if (response.status === 200) {
        setMoreByAuthor(response.data);
      } else {
        console.error("Failed to fetch more books by author");
      }
    } catch (error) {
      console.error("Error fetching more books by author:", error);
    }
  }

  async function fetchSimilarBooks() {
    try {
      const response = await api.get(`/book/getBookByGenre/${book.genre[0]}`);
      if (response.status === 200) {
        setSimilarBooks(response.data);
      } else {
        console.error("Failed to fetch similar books");
      }
    } catch (error) {
      console.error("Error fetching similar books:", error);
    }
  }

  async function submitReview() {
    if (!user) {
      alert("Please login to submit a review");
      return;
    }

    try {
      const response = await api.post("/post/addReviewToBookId", {
        reviewId: Math.floor(Math.random() * 1000000),
        bookId: book.bookId,
        userId: user.userId,
        username: user.username,
        reviewDesc: userReview,
        imageUrl: user.imageUrl,
        rating: userRating | 0,
      });

      if (response.status === 200) {
        console.log("Review submitted successfully");
        setReview("");
      } else {
        console.error("Failed to submit review");
      }
    } catch (error) {
      console.error("Error submitting review:", error);
    }
  }

  async function fetchReviews() {
    try {
      const response = await api.get(`/post/getReviewsByBookId/${book.bookId}`);
      if (response.status === 200) {
        console.log("Reviews fetched successfully:", response.data);
        setReviews(response.data);
      } else {
        console.error("Failed to fetch reviews");
      }
    } catch (error) {
      console.error("Error fetching reviews:", error);
    }
  }

  async function addToWishlist() {
    try{
      const response = await api.post("/wishlist/addBookToWishlist/"+book.bookId);
      if (response.status === 200) {
        console.log("Book added to wishlist successfully");
      } else {
        console.error("Failed to add book to wishlist");
      }
    }catch(error) {
      console.error("Error adding book to wishlist:", error);
    }
  }

  useEffect(() => {
    fetchBook();
  }, [bookName]);

  console.log("Book data:", book);

  useEffect(() => {
    if (!book.bookId) return; // Ensure bookId is available before fetching
    fetchReviews();
    fetchMoreByAuthor();
    fetchSimilarBooks();
  }, [book.bookId]);

  return (
    <div className="relative min-h-screen h-full">
      <div className="bg-[url('/assests/bookback.jpg')] opacity-20 bg-center h-full absolute inset-0"></div>
      <div className="relative z-10">
        <Navbar></Navbar>

        <div className="flex flex-row">
          
          


         

          <img src={book.imageUrl} className="h-[30rem] mt-18 ml-22 m-8 border-transparent border-4"></img>
      
          
          
         
          
            
          <div className="mt-12 ml-20 flex  flex-col gap-10">
            <div className="flex flex-row gap-10 flex-wrap items-center">
              <div>

              <div className="text-5xl   font-spectral font-bold">
                {book.bookName}
              </div>
              <div className="text-3xl    font-spectral font-italic">
                ~ {book.authorName}
              </div>
              </div>
              <div>
                <button className="btn bg-[#17320b] text-white btn-lg" onClick={() => addToWishlist()} disabled={!user}>Add to wishlist</button>
              </div>
            </div>
            <div className="text-xl font-edu-nsw-act-hand-pre  ">
              <div className="flex flex-col gap-4">
                <div className="flex  gap-25 font-medium text-[#401e1e]">
                  <button className="link-hover" onClick={() => setTab(0)}>
                    About the book
                  </button>
                  <button className="link-hover" onClick={() => setTab(1)}>
                    Get the book
                  </button>
                  <button className="link-hover" onClick={() => setTab(2)}>
                    More by author
                  </button>
                  <button className="link-hover" onClick={() => setTab(3)}>
                    Similar books
                  </button>
                </div>
                <img
                  className="opacity-80 blur-[0.5px] mr-24"
                  src="/assests/line.png"
                ></img>
              </div>
              <div className="mt-5 text-2xl w-[60rem]">
                {tab === 0 && <>{book.bookDesc}</>}
                {tab === 1 && <>{}</>}
                {tab === 2 && (
                  <>
                    <div className="flex gap-8">
                      {
                        moreByAuthor
                          .map((book, index) => (
                            <div key={index} className="mb-4 gap-2 w-28">
                              <Link to={`/book?bookName=${book.bookName}`}>
                                <img
                                  src={book.imageUrl}
                                  className="h-40 w-28 object-cover rounded-lg"
                                  alt={book.bookName}
                                />

                                <div className="text-lg font-semibold text-wrap w-28">
                                  {book.bookName}
                                </div>

                                <div className="text-lg text-gray-600">
                                  {book.authorName}
                                </div>
                              </Link>
                            </div>
                          ))
                          .slice(0, 5) // Display only the first 5 similar books
                      }
                    </div>
                  </>
                )}
                {tab === 3 && (
                  <div className="flex gap-2 justify-around">
                    {
                      similarBooks
                        .map((book, index) => (
                          <div key={index} className="mb-4 gap-2 w-28">
                            <Link to={`/book?bookName=${book.bookName}`}>
                              <img
                                src={book.imageUrl}
                                className="h-40 w-28 object-cover rounded-lg"
                                alt={book.bookName}
                              />

                              <div className="text-lg font-semibold text-wrap w-28">
                                {book.bookName}
                              </div>

                              <div className="text-lg text-gray-600">
                                {book.authorName}
                              </div>
                            </Link>
                          </div>
                        ))
                        .slice(0, 5) // Display only the first 5 similar books
                    }
                  </div>
                )}
              </div>
            </div>
            {book && (
              <h1 className="font-edu-nsw-act-hand-pre text-2xl font-medium">
                Genre : {book.genre}
              </h1>
            )}

            <div className="felx flex-row gap-8">
              <h1 className="font-edu-nsw-act-hand-pre text-2xl font-medium">
                Ratings and Reviews
              </h1>
              <div className="rating mt-2">
                <input
                  type="radio"
                  name="rating-2"
                  className="mask mask-star-2 bg-orange-400"
                  aria-label="1 star"
                  onClick={() => setUserRating(1)}
                />
                <input
                  type="radio"
                  name="rating-2"
                  className="mask mask-star-2 bg-orange-400"
                  aria-label="2 star"
                  onClick={() => setUserRating(2)}
                  defaultChecked
                />
                <input
                  type="radio"
                  name="rating-2"
                  className="mask mask-star-2 bg-orange-400"
                  aria-label="3 star"
                  onClick={() => setUserRating(3)}
                />
                <input
                  type="radio"
                  name="rating-2"
                  className="mask mask-star-2 bg-orange-400"
                  aria-label="4 star"
                  onClick={() => setUserRating(4)}
                />
                <input
                  type="radio"
                  name="rating-2"
                  className="mask mask-star-2 bg-orange-400"
                  aria-label="5 star"
                  onClick={() => setUserRating(5)}
                />
              </div>
              <div className="mt-4">
                <h1 className="font-edu-nsw-act-hand-pre text-lg font-medium">
                  Add a review
                </h1>
                <textarea
                  className="textarea bg-transparent w-[80%] font-edu-nsw-act-hand-pre mt-2 mb-2"
                  onChange={(e) => setUserReview(e.target.value)}
                  placeholder="Share your thoughts on the book"
                  disabled={!user}
                ></textarea>
              </div>
              <button
                className="btn btn-primary font-chewy text-lg font-medium"
                onClick={() => submitReview()}
                disabled={!user}
              >
                Submit
              </button>
            </div>

            <div></div>
          </div>
        </div>
        <div>
          {reviews.length > 0 && (
            <>
              {reviews.map((review, index) => (
                <div
                  key={index}
                  className="flex flex-col gap-4 pl-20 pr-20 pt-2 pb-2  border-gray-300"
                >
                  <div className="flex items-center gap-4">
                    <img
                      src={
                        review.imageUrl ||
                        "https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"
                      }
                      alt={review.username}
                      className="w-15 h-15 rounded-full"
                    />
                    <div className="flex flex-col w-full relative gap-3">
                      <div className="flex flex-row gap-4 w-full">
                        <h2 className="font-edu-nsw-act-hand-pre font-medium text-2xl">
                          {review.username}
                        </h2>
                        <div className="rating">
                          <input
                            type="radio"
                            name={`rating-${index + 5}`}
                            className="mask mask-star-2 bg-orange-400"
                            aria-label="1 star"
                            defaultChecked={review.rating === 1}
                          />
                          <input
                            type="radio"
                            name={`rating-${index + 5}`}
                            className="mask mask-star-2 bg-orange-400"
                            aria-label="2 star"
                            defaultChecked={review.rating === 2}
                          />
                          <input
                            type="radio"
                            name={`rating-${index + 5}`}
                            className="mask mask-star-2 bg-orange-400"
                            aria-label="3 star"
                            defaultChecked={review.rating === 3}
                          />
                          <input
                            type="radio"
                            name={`rating-${index + 5}`}
                            className="mask mask-star-2 bg-orange-400"
                            aria-label="4 star"
                            defaultChecked={review.rating === 4}
                          />
                          <input
                            type="radio"
                            name={`rating-${index + 5}`}
                            className="mask mask-star-2 bg-orange-400"
                            aria-label="5 star"
                            defaultChecked={review.rating === 5}
                          />
                        </div>
                      </div>
                      <img
                        src="/assests/reviewline.png"
                        className="w-[40rem] -z-10"
                      ></img>
                      <p className="font-amatic-sc text-2xl">{review.review}</p>
                    </div>
                  </div>
                </div>
              ))}
            </>
          )}
        </div>
      </div>
    </div>
  );
}
