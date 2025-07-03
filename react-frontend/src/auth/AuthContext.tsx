import { useContext, createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";



const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(() => localStorage.getItem("token") || null);
    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState(() => localStorage.getItem("userData") || null);



    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        if (storedToken) {
            setToken(storedToken);
        }
        const storedUser = localStorage.getItem("userData");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
        setLoading(false);
    }, []);

     const navigate = useNavigate();
    

    const login = (token) => {
        localStorage.setItem("token", token);
        setToken(token);  
    }

    const setUserData = (userData) => {
        localStorage.setItem("userData",JSON.stringify(userData));
        setUser(userData);
    }
    

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("userData");
        setToken(null);
   
        navigate("/login");
    }

    const value = {
        token,
        loading,
        login,
        logout,
        user,
        setUserData,
        isLoggedIn: !!token,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}
