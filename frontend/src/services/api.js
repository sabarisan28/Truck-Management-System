import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Handle response errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authService = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
};

export const bookingService = {
  create: (data) => api.post('/bookings', data),
  getMyBookings: () => api.get('/bookings/my-bookings'),
  getById: (id) => api.get(`/bookings/${id}`),
};

export const adminService = {
  getDashboard: () => api.get('/admin/dashboard'),
  
  // Trucks
  getTrucks: () => api.get('/admin/trucks'),
  getTruck: (id) => api.get(`/admin/trucks/${id}`),
  createTruck: (data) => api.post('/admin/trucks', data),
  updateTruck: (id, data) => api.put(`/admin/trucks/${id}`, data),
  deleteTruck: (id) => api.delete(`/admin/trucks/${id}`),
  
  // Drivers
  getDrivers: () => api.get('/admin/drivers'),
  getDriver: (id) => api.get(`/admin/drivers/${id}`),
  createDriver: (data) => api.post('/admin/drivers', data),
  updateDriver: (id, data) => api.put(`/admin/drivers/${id}`, data),
  deleteDriver: (id) => api.delete(`/admin/drivers/${id}`),
  
  // Bookings
  getAllBookings: () => api.get('/admin/bookings'),
  assignDriver: (id, driverId, truckId) => 
    api.put(`/admin/bookings/${id}/assign?driverId=${driverId}&truckId=${truckId}`),
  updateStatus: (id, status) => 
    api.put(`/admin/bookings/${id}/status?status=${status}`),
};

export default api;
