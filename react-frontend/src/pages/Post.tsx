import { useEffect, useState } from "react";
import api from "../api/axios";
import { useAuth } from "../auth/AuthContext"
import Navbar from "../components/Navbar"


export default function Post() {

  const {user,token} = useAuth();


  const [posts, setPosts] = useState([]);
  const [commentData, setCommentData] = useState();  
  
  async function fetchPosts() {
    try{
      const response =  user.followedAuthors.map((author) => api.get("/post/getPostByAuthorId/" + author));
      const postData = await Promise.all(response);
    
      const postsArray = postData.map((res) => res.data);
      console.log("Posts data:", postsArray);
      setPosts(postsArray.flat()); // Flatten the array of arrays into a single array
    }
    
    catch (error) {
      console.error("Error fetching posts:", error);
    }
  }

  async function likePost(postId) {
    try{
      const response = await api.post(`/post/likePost/${postId}`);
      if (!response) {
        throw new Error('Network response was not ok');
      }

    }
    catch (error) {
      console.error("Error liking post:", error);
    }
  }

  async function commentPost(postId, commentDesc) {
    try{
      const response = await api.post(`/post/makeComment/${postId}`, {
        commentDesc: commentDesc,
        userId: user.userId,
        username: user.username,
        postId: postId
      })
      if (!response) {
        throw new Error('Network response was not ok');
      }
    }catch (error) {
      console.error("Error commenting on post:", error);
    }
  }

  useEffect(() => {
    fetchPosts();
   
  }, [user]);

  return (
    <div>
      <div className="bg-[url('/assests/bookback.jpg')] opacity-20 bg-center h-full absolute inset-0 -z-10"></div>
            <Navbar/>
    <div className="h-full flex flex-row items-center justify-center pt-10 gap-10">
      <img src="/assests/postdesgin.png" className="h-[80vh]" />
      <div className="bg-[#fbe2c16c] w-1/3 h-[80vh] rounded-4xl overflow-clip">
      <h1 className="text-4xl font-semibold text-center pt-4 font-love-light">Author's thoughts</h1>
      


      <div className="carousel w-full">
        {posts.map((post, index) => (
        <>
        <div id={"slide"+index} className="carousel-item relative w-full flex flex-col items-center gap-4">
          <div className="flex flex-row items-center justify-between pl-10 gap-5 w-full">

          <div className="">
                    <img src={"https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"} className='h-24 w-24 rounded-full' />
                    </div>
            <div className='w-[80%]'>
                    <h2 className='text-2xl font-amatic-sc'>{post.authorName}</h2>
                   
          </div>
          </div>
          <div className="w-2/3 h-[15rem] text-2xl flex flex-col p-2 text-white bg-[#3d170063] rounded-2xl">
            {post.data}
          </div>

          <div className="flex flex-row items-center w-full  gap-5 pl-20">
          <button className="btn btn-circle" onClick={() => likePost(post.postId)}>
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2.5" stroke="currentColor" className="size-[1.2em]"><path strokeLinecap="round" strokeLinejoin="round" d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" /></svg>
</button>
<details className="dropdown dropdown-top dropdown-center">
  <summary className="btn m-1 btn-circle">
<svg className="size-[1.2em]"  viewBox="0 0 32 32" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>comment-3</title> <desc>Created with Sketch Beta.</desc> <defs> </defs> <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage"> <g id="Icon-Set" sketch:type="MSLayerGroup" transform="translate(-204.000000, -255.000000)" fill="#000000"> <path d="M228,267 C226.896,267 226,267.896 226,269 C226,270.104 226.896,271 228,271 C229.104,271 230,270.104 230,269 C230,267.896 229.104,267 228,267 L228,267 Z M220,281 C218.832,281 217.704,280.864 216.62,280.633 L211.912,283.463 L211.975,278.824 C208.366,276.654 206,273.066 206,269 C206,262.373 212.268,257 220,257 C227.732,257 234,262.373 234,269 C234,275.628 227.732,281 220,281 L220,281 Z M220,255 C211.164,255 204,261.269 204,269 C204,273.419 206.345,277.354 210,279.919 L210,287 L217.009,282.747 C217.979,282.907 218.977,283 220,283 C228.836,283 236,276.732 236,269 C236,261.269 228.836,255 220,255 L220,255 Z M212,267 C210.896,267 210,267.896 210,269 C210,270.104 210.896,271 212,271 C213.104,271 214,270.104 214,269 C214,267.896 213.104,267 212,267 L212,267 Z M220,267 C218.896,267 218,267.896 218,269 C218,270.104 218.896,271 220,271 C221.104,271 222,270.104 222,269 C222,267.896 221.104,267 220,267 L220,267 Z" id="comment-3" sketch:type="MSShapeGroup"> </path> </g> </g> </g></svg>

  </summary>
  <div className="bg-[#fbe2c1] dropdown-content mt-2 p-4 rounded-lg w-[20rem]">
    <h3 className="font-bold text-lg">Make A Comment</h3>
    <textarea className="textarea textarea-bordered w-full mt-4 bg-[#3d170063] text-white" onChange={(e) => setCommentData(e.target.value)} placeholder="Write your comment here..."></textarea>
          <button className="btn mt-2" onClick={() => commentPost(post.postId, commentData)}>Submit</button>
   </div>
</details>


          </div>
          
          <div className="carousel carousel-vertical w-full">
          {post.comments.map((comment, commentIndex) => (
            <div key={commentIndex} className="carousel-item h-full flex flex-col items-center ">
              <div className="w-4/5 text-lg flex flex-col p-2 justify-center items-center bg-white border-2 border-[#3d1700] rounded-full font-amatic-sc font-semibold">
             {comment.username} : {comment.commentDesc}
            </div>
            </div>
          ))}
          </div>

    <div className="absolute left-5 right-5 top-1/2 flex -translate-y-1/2 transform justify-between">
      <a href={`#slide${index-1}`} className="btn btn-circle">❮</a>
      <a href={`#slide${index+1}`} className="btn btn-circle">❯</a>
    </div>
        </div>   
        </>
      ))}
  
 
 
</div>





      
      </div>
      <img src="/assests/postdesgin.png" className="h-[80vh] rotate-180" />
  
    </div>
   </div>
  )
}
