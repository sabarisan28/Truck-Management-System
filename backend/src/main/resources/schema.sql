-- Database Schema for Truck Management System

CREATE DATABASE IF NOT EXISTS truck_management_db;
USE truck_management_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Trucks Table
CREATE TABLE IF NOT EXISTS trucks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    truck_number VARCHAR(50) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL,
    capacity DECIMAL(10,2) NOT NULL,
    availability_status ENUM('AVAILABLE', 'ASSIGNED', 'MAINTENANCE') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Drivers Table
CREATE TABLE IF NOT EXISTS drivers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    assigned_truck_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_truck_id) REFERENCES trucks(id) ON DELETE SET NULL
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    truck_id BIGINT,
    driver_id BIGINT,
    pickup_location VARCHAR(255) NOT NULL,
    drop_location VARCHAR(255) NOT NULL,
    load_type VARCHAR(100) NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    distance DECIMAL(10,2),
    price DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'ASSIGNED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (truck_id) REFERENCES trucks(id) ON DELETE SET NULL,
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE SET NULL
);

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- Insert Default Admin User (password: admin123)
INSERT INTO users (name, email, password, role) VALUES 
('Admin User', 'admin@trucksystem.com', '$2a$10$xqxQ8Z9X8Z9X8Z9X8Z9X8eKvN5Z9X8Z9X8Z9X8Z9X8Z9X8Z9X8Z9X', 'ADMIN');

-- Insert Sample Trucks
INSERT INTO trucks (truck_number, type, capacity, availability_status) VALUES 
('TRK-001', 'Flatbed', 10.00, 'AVAILABLE'),
('TRK-002', 'Box Truck', 15.00, 'AVAILABLE'),
('TRK-003', 'Refrigerated', 12.00, 'AVAILABLE'),
('TRK-004', 'Tanker', 20.00, 'AVAILABLE');

-- Insert Sample Drivers
INSERT INTO drivers (name, phone, license_number, assigned_truck_id) VALUES 
('John Doe', '+1234567890', 'DL-12345', NULL),
('Jane Smith', '+1234567891', 'DL-12346', NULL),
('Mike Johnson', '+1234567892', 'DL-12347', NULL);
