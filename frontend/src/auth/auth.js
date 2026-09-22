import api, { setAuthToken } from '../lib/axios';

export const TOKEN_KEY = 'token';

export function getStoredToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function restoreAuthToken() {
  const token = getStoredToken();

  if (token) {
    setAuthToken(token);
  }

  return Boolean(token);
}

export async function login(username, password) {
  const response = await api.post('/api/auth/login', { username, password });
  const token = response.data?.token;

  if (!token) {
    throw new Error('Login response did not include a token');
  }

  localStorage.setItem(TOKEN_KEY, token);
  setAuthToken(token);

  return response.data;
}

export async function getCurrentUser() {
  const token = getStoredToken();

  if (!token) {
    return null;
  }

  setAuthToken(token);
  const response = await api.get('/api/auth/me');

  return response.data;
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY);
  setAuthToken(null);
}
