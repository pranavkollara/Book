import React, { useState } from "react";

export default function SimilarBook({ book }) {
  const [loading, setLoading] = useState(true);
  return (
    <>
      <img
        src={book?.imageUrl}
        alt={book?.bookName}
        className="w-35 object-cover mt-2"
        hidden={loading}
        onLoad={() => {
          setLoading(false);
        }}
        onError={() => {
          setLoading(true);
          alert("Image not found");
        }}
      />
        {loading && (
            <div className="skeleton h-50 w-35 opacity-50"></div>
        )}
      <div>
        {loading? <div className="skeleton h-[1.25rem] w-35"></div> :<h2 className="text-xl font-bold w-35">{book?.bookName}</h2>}
        {loading? <div className="skeleton h-5 w-35 mt-2"></div> :<p className="text-gray-600  w-35">Author: {book?.authorName}</p>}
      </div>
    </>
  );
}
