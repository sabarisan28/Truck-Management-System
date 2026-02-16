import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';

// Pages
import Login from './pages/Login';
import Register from './pages/Register';
import UserDashboard from './pages/UserDashboard';
import BookTruck from './pages/BookTruck';
import MyBookings from './pages/MyBookings';
import Payment from './pages/Payment';
import AdminDashboard from './pages/AdminDashboard';
import ManageTrucks from './pages/ManageTrucks';
import ManageDrivers from './pages/ManageDrivers';
import ManageBookings from './pages/ManageBookings';

const PrivateRoute = ({ children, adminOnly = false }) => {
  const { user, loading, isAdmin } = useAuth();

  if (loading) {
    return <div className="flex items-center justify-center h-screen">Loading...</div>;
  }

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (adminOnly && !isAdmin()) {
    return <Navigate to="/dashboard" />;
  }

  return children;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
          {/* User Routes */}
          <Route path="/dashboard" element={
            <PrivateRoute>
              <UserDashboard />
            </PrivateRoute>
          } />
          <Route path="/book-truck" element={
            <PrivateRoute>
              <BookTruck />
            </PrivateRoute>
          } />
          <Route path="/my-bookings" element={
            <PrivateRoute>
              <MyBookings />
            </PrivateRoute>
          } />
          <Route path="/payment/:bookingId" element={
            <PrivateRoute>
              <Payment />
            </PrivateRoute>
          } />
          
          {/* Admin Routes */}
          <Route path="/admin/dashboard" element={
            <PrivateRoute adminOnly>
              <AdminDashboard />
            </PrivateRoute>
          } />
          <Route path="/admin/trucks" element={
            <PrivateRoute adminOnly>
              <ManageTrucks />
            </PrivateRoute>
          } />
          <Route path="/admin/drivers" element={
            <PrivateRoute adminOnly>
              <ManageDrivers />
            </PrivateRoute>
          } />
          <Route path="/admin/bookings" element={
            <PrivateRoute adminOnly>
              <ManageBookings />
            </PrivateRoute>
          } />
          
          <Route path="/" element={<Navigate to="/login" />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
