import React from 'react'
import { useAuth } from './AuthContext';
import { Navigate } from 'react-router-dom';

export default function ProtectedRoutes({children}) {
  const { isLoggedIn } = useAuth();

  return isLoggedIn ? children : <Navigate to="/login" replace />;
}
