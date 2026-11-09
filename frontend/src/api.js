import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const getClothes = () => api.get('/clothes');
export const createClothe = (formData) => api.post('/clothes', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
});
export const getStats = (type) => api.get(`/clothes/stats?type=${type}`);
export const deleteClothe = (id) => api.delete(`/clothes/${id}`);
export const updateClothe = (id, data) => api.put(`/clothes/${id}`, null, { params: data });