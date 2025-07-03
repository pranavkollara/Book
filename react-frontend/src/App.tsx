import React from 'react'
import { Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { AuthProvider } from './auth/AuthContext'
import Login from './pages/Login'
import ProtectedRoutes from './auth/ProtectedRoutes'
import Register from './pages/Register'
import Home from './pages/Home'
import Genres from './pages/Genres'
import Search from './pages/Search'
import Book from './pages/Book'
import Authors from './pages/Authors'
import ScrollToTop from './util/ScrollToTop'
import CreatePost from './pages/CreatePost'
import Wishlist from './pages/Wishlist'
import Post from './pages/Post'
import Settings from './pages/Settings'
import Admin from './pages/Admin'


export default function App() {
  return (
    <Router>
              <ScrollToTop></ScrollToTop>
        <AuthProvider>
            <Routes>
             
                <Route path="/login" element={<Login></Login>} />
                <Route path="/signup" element={<Register></Register>} />
                <Route path="/home" element={<Home></Home>} />
                <Route path="/genres" element={<Genres></Genres>} />
                <Route path="/search" element={<Search></Search>} />
                <Route path="/book" element={<Book></Book>} />
                <Route path="/authors" element={<Authors></Authors>} />
                <Route path="/posts" element={<Post></Post>} />
                <Route path="/settings" element={<Settings></Settings>} />
                <Route path="/admin" element={<Admin></Admin>} />
                <Route path="/wishlist" element={<ProtectedRoutes>
                  
                  <Wishlist></Wishlist>
                  </ProtectedRoutes>
                  }
                   />

                <Route path="/createPost" element={<ProtectedRoutes>
                  <CreatePost></CreatePost>
                  </ProtectedRoutes>
                }
                   />
                

            </Routes>

        </AuthProvider>
    </Router>
  )
}
