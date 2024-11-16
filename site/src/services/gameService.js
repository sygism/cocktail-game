import axios from '@/axios.js';

export async function startGame() {
    return axios.post(`${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}/start-game`);
}

export async function getGameState() {
    return axios.get(`${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}/game-state`);
}

export async function submitGuess(guess) {
    return axios.get(`${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}/guess?guess=${guess}`);
}

export async function getHighScore() {
    return axios.get(`${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}/high-score`);
}

export async function errorTest() {
    return axios.get(`${import.meta.env.VITE_TIRE_COCKTAIL_GAME_BACKEND_URL}/error-test`);
}