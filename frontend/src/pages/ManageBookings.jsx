import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import { adminService } from '../services/api';

const ManageBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const [trucks, setTrucks] = useState([]);
  const [showAssignModal, setShowAssignModal] = useState(false);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [assignData, setAssignData] = useState({ driverId: '', truckId: '' });

  useEffect(() => {
    fetchBookings();
    fetchDrivers();
    fetchTrucks();
  }, []);

  const fetchBookings = async () => {
    try {
      const response = await adminService.getAllBookings();
      setBookings(response.data);
    } catch (error) {
      console.error('Failed to fetch bookings:', error);
    }
  };

  const fetchDrivers = async () => {
    try {
      const response = await adminService.getDrivers();
      setDrivers(response.data);
    } catch (error) {
      console.error('Failed to fetch drivers:', error);
    }
  };

  const fetchTrucks = async () => {
    try {
      const response = await adminService.getTrucks();
      setTrucks(response.data.filter(t => t.availabilityStatus === 'AVAILABLE'));
    } catch (error) {
      console.error('Failed to fetch trucks:', error);
    }
  };

  const handleAssign = async (e) => {
    e.preventDefault();
    try {
      await adminService.assignDriver(selectedBooking.id, assignData.driverId, assignData.truckId);
      fetchBookings();
      fetchTrucks();
      setShowAssignModal(false);
      alert('Driver and truck assigned successfully!');
    } catch (error) {
      alert('Failed to assign driver and truck');
    }
  };

  const handleStatusChange = async (bookingId, newStatus) => {
    try {
      await adminService.updateStatus(bookingId, newStatus);
      fetchBookings();
      fetchTrucks();
      alert('Status updated successfully!');
    } catch (error) {
      alert('Failed to update status');
    }
  };

  const openAssignModal = (booking) => {
    setSelectedBooking(booking);
    setAssignData({ driverId: '', truckId: '' });
    setShowAssignModal(true);
  };

  const getStatusColor = (status) => {
    const colors = {
      PENDING: 'bg-yellow-100 text-yellow-800',
      ASSIGNED: 'bg-blue-100 text-blue-800',
      IN_TRANSIT: 'bg-purple-100 text-purple-800',
      DELIVERED: 'bg-green-100 text-green-800',
      CANCELLED: 'bg-red-100 text-red-800'
    };
    return colors[status] || 'bg-gray-100 text-gray-800';
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar />
      
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-6">Manage Bookings</h1>

        <div className="bg-white rounded-lg shadow-md overflow-x-auto">
          <table className="min-w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">User</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Route</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Load</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Price</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {bookings.map((booking) => (
                <tr key={booking.id}>
                  <td className="px-6 py-4">#{booking.id}</td>
                  <td className="px-6 py-4">{booking.userName}</td>
                  <td className="px-6 py-4">
                    <div className="text-sm">
                      <div>From: {booking.pickupLocation}</div>
                      <div>To: {booking.dropLocation}</div>
                    </div>
                  </td>
                  <td className="px-6 py-4">
                    <div className="text-sm">
                      <div>{booking.loadType}</div>
                      <div>{booking.weight} tons</div>
                    </div>
                  </td>
                  <td className="px-6 py-4 text-green-600 font-semibold">${booking.price}</td>
                  <td className="px-6 py-4">
                    <span className={`px-2 py-1 rounded text-sm ${getStatusColor(booking.status)}`}>
                      {booking.status}
                    </span>
                  </td>
                  <td className="px-6 py-4">
                    <div className="flex flex-col gap-2">
                      {booking.status === 'PENDING' && (
                        <button
                          onClick={() => openAssignModal(booking)}
                          className="text-blue-600 hover:text-blue-800 text-sm"
                        >
                          Assign Driver
                        </button>
                      )}
                      {booking.status === 'ASSIGNED' && (
                        <button
                          onClick={() => handleStatusChange(booking.id, 'IN_TRANSIT')}
                          className="text-purple-600 hover:text-purple-800 text-sm"
                        >
                          Mark In Transit
                        </button>
                      )}
                      {booking.status === 'IN_TRANSIT' && (
                        <button
                          onClick={() => handleStatusChange(booking.id, 'DELIVERED')}
                          className="text-green-600 hover:text-green-800 text-sm"
                        >
                          Mark Delivered
                        </button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showAssignModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-lg w-full max-w-md">
            <h2 className="text-2xl font-bold mb-4">Assign Driver & Truck</h2>
            <form onSubmit={handleAssign}>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Select Driver</label>
                <select
                  className="w-full px-4 py-2 border rounded-lg"
                  value={assignData.driverId}
                  onChange={(e) => setAssignData({ ...assignData, driverId: e.target.value })}
                  required
                >
                  <option value="">Choose a driver</option>
                  {drivers.map((driver) => (
                    <option key={driver.id} value={driver.id}>
                      {driver.name} - {driver.licenseNumber}
                    </option>
                  ))}
                </select>
              </div>
              <div className="mb-6">
                <label className="block text-gray-700 mb-2">Select Truck</label>
                <select
                  className="w-full px-4 py-2 border rounded-lg"
                  value={assignData.truckId}
                  onChange={(e) => setAssignData({ ...assignData, truckId: e.target.value })}
                  required
                >
                  <option value="">Choose a truck</option>
                  {trucks.map((truck) => (
                    <option key={truck.id} value={truck.id}>
                      {truck.truckNumber} - {truck.type} ({truck.capacity} tons)
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex gap-4">
                <button type="submit" className="flex-1 bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700">
                  Assign
                </button>
                <button
                  type="button"
                  onClick={() => setShowAssignModal(false)}
                  className="flex-1 bg-gray-300 text-gray-700 py-2 rounded-lg hover:bg-gray-400"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ManageBookings;
