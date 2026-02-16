-- Sample Data for Testing
-- This file contains sample data to populate the database for testing purposes

-- Note: Run this after the schema is created
-- Password for all users: password123
-- Hashed with BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

-- Insert Admin User
INSERT INTO users (name, email, password, role, created_at, updated_at) VALUES 
('Admin User', 'admin@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', NOW(), NOW());

-- Insert Sample Users
INSERT INTO users (name, email, password, role, created_at, updated_at) VALUES 
('John Doe', 'john@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', NOW(), NOW()),
('Jane Smith', 'jane@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', NOW(), NOW()),
('Bob Johnson', 'bob@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', NOW(), NOW()),
('Alice Williams', 'alice@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER', NOW(), NOW());

-- Insert Sample Trucks
INSERT INTO trucks (truck_number, type, capacity, availability_status, created_at, updated_at) VALUES 
('TRK-001', 'Flatbed', 10.00, 'AVAILABLE', NOW(), NOW()),
('TRK-002', 'Box Truck', 15.00, 'AVAILABLE', NOW(), NOW()),
('TRK-003', 'Refrigerated', 12.00, 'AVAILABLE', NOW(), NOW()),
('TRK-004', 'Tanker', 20.00, 'AVAILABLE', NOW(), NOW()),
('TRK-005', 'Dump Truck', 18.00, 'AVAILABLE', NOW(), NOW()),
('TRK-006', 'Flatbed', 10.00, 'MAINTENANCE', NOW(), NOW()),
('TRK-007', 'Box Truck', 15.00, 'AVAILABLE', NOW(), NOW()),
('TRK-008', 'Container Truck', 25.00, 'AVAILABLE', NOW(), NOW());

-- Insert Sample Drivers
INSERT INTO drivers (name, phone, license_number, assigned_truck_id, created_at, updated_at) VALUES 
('Mike Johnson', '+1234567890', 'DL-12345', NULL, NOW(), NOW()),
('Sarah Williams', '+1234567891', 'DL-12346', NULL, NOW(), NOW()),
('David Brown', '+1234567892', 'DL-12347', NULL, NOW(), NOW()),
('Emily Davis', '+1234567893', 'DL-12348', NULL, NOW(), NOW()),
('James Wilson', '+1234567894', 'DL-12349', NULL, NOW(), NOW()),
('Lisa Anderson', '+1234567895', 'DL-12350', NULL, NOW(), NOW());

-- Insert Sample Bookings (using user IDs 2-5 for regular users)
INSERT INTO bookings (user_id, truck_id, driver_id, pickup_location, drop_location, load_type, weight, distance, price, status, booking_date, created_at, updated_at) VALUES 
-- Pending bookings
(2, NULL, NULL, 'New York, NY', 'Boston, MA', 'Furniture', 5.50, 215.50, 591.25, 'PENDING', NOW(), NOW(), NOW()),
(3, NULL, NULL, 'Los Angeles, CA', 'San Francisco, CA', 'Electronics', 3.20, 382.00, 1006.60, 'PENDING', NOW(), NOW(), NOW()),
(4, NULL, NULL, 'Chicago, IL', 'Detroit, MI', 'General Cargo', 8.00, 283.00, 761.50, 'PENDING', NOW(), NOW(), NOW()),

-- Assigned bookings
(2, 1, 1, 'Miami, FL', 'Orlando, FL', 'Food Items', 4.00, 235.00, 639.50, 'ASSIGNED', DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), NOW()),
(3, 2, 2, 'Seattle, WA', 'Portland, OR', 'Construction Materials', 12.00, 174.00, 491.00, 'ASSIGNED', DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), NOW()),

-- In Transit bookings
(4, 3, 3, 'Houston, TX', 'Dallas, TX', 'Furniture', 6.50, 239.00, 650.00, 'IN_TRANSIT', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW(), NOW()),
(5, 4, 4, 'Phoenix, AZ', 'Las Vegas, NV', 'Electronics', 4.80, 297.00, 794.90, 'IN_TRANSIT', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW(), NOW()),

-- Delivered bookings
(2, 5, 5, 'Philadelphia, PA', 'Washington, DC', 'General Cargo', 7.00, 140.00, 453.50, 'DELIVERED', DATE_SUB(NOW(), INTERVAL 5 DAY), NOW(), NOW()),
(3, 7, 6, 'San Diego, CA', 'Los Angeles, CA', 'Food Items', 3.50, 120.00, 351.75, 'DELIVERED', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW(), NOW()),
(4, 1, 1, 'Denver, CO', 'Salt Lake City, UT', 'Construction Materials', 10.00, 525.00, 1367.50, 'DELIVERED', DATE_SUB(NOW(), INTERVAL 10 DAY), NOW(), NOW()),
(5, 2, 2, 'Atlanta, GA', 'Charlotte, NC', 'Furniture', 5.00, 244.00, 662.50, 'DELIVERED', DATE_SUB(NOW(), INTERVAL 12 DAY), NOW(), NOW()),
(2, 3, 3, 'Minneapolis, MN', 'Milwaukee, WI', 'Electronics', 4.20, 337.00, 894.60, 'DELIVERED', DATE_SUB(NOW(), INTERVAL 15 DAY), NOW(), NOW());

-- Insert Sample Payments
INSERT INTO payments (booking_id, amount, payment_status, payment_date, created_at) VALUES 
-- Pending payments for pending bookings
(1, 591.25, 'PENDING', NULL, NOW()),
(2, 1006.60, 'PENDING', NULL, NOW()),
(3, 761.50, 'PENDING', NULL, NOW()),

-- Pending payments for assigned bookings
(4, 639.50, 'PENDING', NULL, NOW()),
(5, 491.00, 'PENDING', NULL, NOW()),

-- Pending payments for in-transit bookings
(6, 650.00, 'PENDING', NULL, NOW()),
(7, 794.90, 'PENDING', NULL, NOW()),

-- Completed payments for delivered bookings
(8, 453.50, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
(9, 351.75, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
(10, 1367.50, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 10 DAY), NOW()),
(11, 662.50, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 12 DAY), NOW()),
(12, 894.60, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 15 DAY), NOW());

-- Update truck availability for assigned and in-transit bookings
UPDATE trucks SET availability_status = 'ASSIGNED' WHERE id IN (1, 2, 3, 4);

-- Assign some drivers to trucks
UPDATE drivers SET assigned_truck_id = 1 WHERE id = 1;
UPDATE drivers SET assigned_truck_id = 2 WHERE id = 2;
UPDATE drivers SET assigned_truck_id = 3 WHERE id = 3;
UPDATE drivers SET assigned_truck_id = 4 WHERE id = 4;

-- Verify data
SELECT 'Users created:' as Info, COUNT(*) as Count FROM users;
SELECT 'Trucks created:' as Info, COUNT(*) as Count FROM trucks;
SELECT 'Drivers created:' as Info, COUNT(*) as Count FROM drivers;
SELECT 'Bookings created:' as Info, COUNT(*) as Count FROM bookings;
SELECT 'Payments created:' as Info, COUNT(*) as Count FROM payments;

-- Show summary statistics
SELECT 
    'Summary Statistics' as Report,
    (SELECT COUNT(*) FROM users WHERE role = 'USER') as Total_Users,
    (SELECT COUNT(*) FROM users WHERE role = 'ADMIN') as Total_Admins,
    (SELECT COUNT(*) FROM trucks) as Total_Trucks,
    (SELECT COUNT(*) FROM drivers) as Total_Drivers,
    (SELECT COUNT(*) FROM bookings) as Total_Bookings,
    (SELECT SUM(price) FROM bookings WHERE status = 'DELIVERED') as Total_Revenue;
