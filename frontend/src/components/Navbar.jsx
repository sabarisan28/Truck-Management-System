import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { user, logout, isAdmin } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <div className="flex items-center space-x-4">
            <Link to={isAdmin() ? '/admin/dashboard' : '/dashboard'} className="text-xl font-bold">
              🚚 Truck Management
            </Link>
            
            {isAdmin() ? (
              <div className="flex space-x-4">
                <Link to="/admin/dashboard" className="hover:text-blue-200">Dashboard</Link>
                <Link to="/admin/trucks" className="hover:text-blue-200">Trucks</Link>
                <Link to="/admin/drivers" className="hover:text-blue-200">Drivers</Link>
                <Link to="/admin/bookings" className="hover:text-blue-200">Bookings</Link>
              </div>
            ) : (
              <div className="flex space-x-4">
                <Link to="/dashboard" className="hover:text-blue-200">Dashboard</Link>
                <Link to="/book-truck" className="hover:text-blue-200">Book Truck</Link>
                <Link to="/my-bookings" className="hover:text-blue-200">My Bookings</Link>
              </div>
            )}
          </div>
          
          <div className="flex items-center space-x-4">
            <span className="text-sm">Welcome, {user?.name}</span>
            <button
              onClick={handleLogout}
              className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
