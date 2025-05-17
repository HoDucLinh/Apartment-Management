import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/apartment_management/api/';

export const endpoints = {
    'login': '/login',
    'current-user': '/secure/profile',
    'change-password': (userId) => `users/${userId}/change_password`,
    'updateAvatar': (userId) => `/users/${userId}/update_avatar`
}

export const authApis = (token = null) => {
    if (!token) {
        token = cookie.load('token'); // fallback nếu không truyền từ props
    }

    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
};

export default axios.create({
    baseURL: BASE_URL
});