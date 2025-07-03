import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8050",

});

api.interceptors.request.use((config) => {
    const url = config.url || "";
    const isAuthPath = /\/auth(\/|$)/.test(url); // Matches /auth and /auth/login etc.

    if (!isAuthPath) {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
    }

    return config;
});


export default api;
