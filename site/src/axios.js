import axios from 'axios';

const instance = axios.create({
    baseURL: `${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}`,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    }
});

export default instance;