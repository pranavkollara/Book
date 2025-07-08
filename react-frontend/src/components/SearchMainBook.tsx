import React, { useState } from "react";

export default function SearchMainBook({ book }) {
  const [loading, setLoading] = useState(true);


   
  return (
    
      <>
        <img
          src={book?.imageUrl}
          alt={book?.bookName}
          className="h-70 object-cover"
          onLoad={() => {
            setLoading(false);
          }}
          hidden={loading}  
        />
        {loading && (
          <div className="skeleton h-70 w-[14rem] opacity-50"></div>
        )}
        <div>
          {loading ? <div className="skeleton h-[4rem] w-80 rounded-full opacity-50"></div>  :<h2 className="text-7xl font-bold">{book?.bookName}</h2>}
          {loading ? <div className="skeleton h-[2rem] w-80 rounded-full mt-2 opacity-50"></div>  :<p className="text-gray-600 text-4xl">Author: {book?.authorName}</p>}
        </div>
      </>
  
  );
}
