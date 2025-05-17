import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/apartment_management/api/';

export const endpoints = {
    'login': '/login',
    'payment': '/payment',
    'get-invoices': (userId) => `/api/invoices/${userId}`,


}

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});