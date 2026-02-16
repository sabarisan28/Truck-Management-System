# Test Credentials and Sample Data

## Default Test Accounts

All test accounts use the password: `password123`

### Admin Account
- **Email**: admin@test.com
- **Password**: password123
- **Role**: ADMIN
- **Access**: Full system access

### User Accounts

#### User 1
- **Email**: john@test.com
- **Password**: password123
- **Role**: USER
- **Name**: John Doe

#### User 2
- **Email**: jane@test.com
- **Password**: password123
- **Role**: USER
- **Name**: Jane Smith

#### User 3
- **Email**: bob@test.com
- **Password**: password123
- **Role**: USER
- **Name**: Bob Johnson

#### User 4
- **Email**: alice@test.com
- **Password**: password123
- **Role**: USER
- **Name**: Alice Williams

## Sample Data Overview

### Trucks (8 total)
- TRK-001: Flatbed (10 tons) - Available
- TRK-002: Box Truck (15 tons) - Available
- TRK-003: Refrigerated (12 tons) - Available
- TRK-004: Tanker (20 tons) - Available
- TRK-005: Dump Truck (18 tons) - Available
- TRK-006: Flatbed (10 tons) - Maintenance
- TRK-007: Box Truck (15 tons) - Available
- TRK-008: Container Truck (25 tons) - Available

### Drivers (6 total)
- Mike Johnson (DL-12345)
- Sarah Williams (DL-12346)
- David Brown (DL-12347)
- Emily Davis (DL-12348)
- James Wilson (DL-12349)
- Lisa Anderson (DL-12350)

### Bookings (12 total)

#### Status Distribution
- **Pending**: 3 bookings
- **Assigned**: 2 bookings
- **In Transit**: 2 bookings
- **Delivered**: 5 bookings

#### Sample Booking Details

**Pending Bookings:**
1. New York → Boston (Furniture, 5.5 tons, $591.25)
2. Los Angeles → San Francisco (Electronics, 3.2 tons, $1,006.60)
3. Chicago → Detroit (General Cargo, 8 tons, $761.50)

**Assigned Bookings:**
4. Miami → Orlando (Food Items, 4 tons, $639.50)
5. Seattle → Portland (Construction Materials, 12 tons, $491.00)

**In Transit:**
6. Houston → Dallas (Furniture, 6.5 tons, $650.00)
7. Phoenix → Las Vegas (Electronics, 4.8 tons, $794.90)

**Delivered:**
8. Philadelphia → Washington DC (General Cargo, 7 tons, $453.50)
9. San Diego → Los Angeles (Food Items, 3.5 tons, $351.75)
10. Denver → Salt Lake City (Construction Materials, 10 tons, $1,367.50)
11. Atlanta → Charlotte (Furniture, 5 tons, $662.50)
12. Minneapolis → Milwaukee (Electronics, 4.2 tons, $894.60)

### Payments
- **Pending**: 7 payments (for pending, assigned, and in-transit bookings)
- **Completed**: 5 payments (for delivered bookings)
- **Total Revenue**: $3,697.85 (from completed bookings)

## Loading Sample Data

### Method 1: Automatic (Recommended)

If you want Spring Boot to automatically load data on startup:

1. Rename `data.sql` to `import.sql` in `src/main/resources/`
2. Add to `application.properties`:
```properties
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

### Method 2: Manual SQL Script

Run the SQL script manually:

```bash
mysql -u root -p truck_management_db < backend/src/main/resources/data.sql
```

### Method 3: MySQL Workbench

1. Open MySQL Workbench
2. Connect to your database
3. Open `data.sql` file
4. Execute the script

### Method 4: Command Line

```bash
# Navigate to backend/src/main/resources/
cd backend/src/main/resources/

# Run the script
mysql -u root -p truck_management_db < data.sql
```

## Testing Scenarios

### Scenario 1: User Registration and Booking

1. Register a new user account
2. Login with new credentials
3. Create a new booking
4. View booking in "My Bookings"
5. Check email for confirmation (if configured)

### Scenario 2: Admin Workflow

1. Login as admin (admin@test.com)
2. View dashboard statistics
3. Add a new truck
4. Add a new driver
5. Assign driver to a pending booking
6. Update booking status to "In Transit"
7. Mark booking as "Delivered"

### Scenario 3: Complete Booking Lifecycle

1. User creates booking (Status: PENDING)
2. Admin assigns driver and truck (Status: ASSIGNED)
3. Admin marks as in transit (Status: IN_TRANSIT)
4. Admin marks as delivered (Status: DELIVERED)
5. Payment status updates to COMPLETED

### Scenario 4: Truck Management

1. Login as admin
2. View all trucks
3. Edit truck details
4. Change availability status
5. Delete a truck (ensure no active bookings)

### Scenario 5: Driver Management

1. Login as admin
2. View all drivers
3. Add new driver
4. Assign truck to driver
5. View driver assignments

## API Testing with Sample Data

### Test User Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@test.com","password":"password123"}'
```

### Test Admin Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@test.com","password":"password123"}'
```

### Get User Bookings
```bash
curl -X GET http://localhost:8080/api/bookings/my-bookings \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Get Dashboard Stats (Admin)
```bash
curl -X GET http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

## Verifying Sample Data

### Check Users
```sql
SELECT id, name, email, role FROM users;
```

### Check Trucks
```sql
SELECT id, truck_number, type, capacity, availability_status FROM trucks;
```

### Check Bookings by Status
```sql
SELECT status, COUNT(*) as count FROM bookings GROUP BY status;
```

### Check Revenue
```sql
SELECT SUM(price) as total_revenue FROM bookings WHERE status = 'DELIVERED';
```

### Check Pending Bookings
```sql
SELECT b.id, u.name as user_name, b.pickup_location, b.drop_location, b.price 
FROM bookings b 
JOIN users u ON b.user_id = u.id 
WHERE b.status = 'PENDING';
```

## Resetting Sample Data

To reset and reload sample data:

```bash
# Drop and recreate database
mysql -u root -p -e "DROP DATABASE truck_management_db; CREATE DATABASE truck_management_db;"

# Run schema
mysql -u root -p truck_management_db < backend/src/main/resources/schema.sql

# Load sample data
mysql -u root -p truck_management_db < backend/src/main/resources/data.sql
```

## Production Considerations

**IMPORTANT**: 
- Never use these test credentials in production
- Change all default passwords
- Remove or disable sample data loading
- Use strong, unique passwords
- Enable proper authentication mechanisms
- Implement rate limiting
- Add CAPTCHA for registration

## Creating Your Own Test Data

### Generate BCrypt Password Hash

Use online BCrypt generator or Java code:

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "yourpassword";
        String hashedPassword = encoder.encode(password);
        System.out.println(hashedPassword);
    }
}
```

### Add Custom User
```sql
INSERT INTO users (name, email, password, role, created_at, updated_at) VALUES 
('Your Name', 'your@email.com', 'BCRYPT_HASH_HERE', 'USER', NOW(), NOW());
```

### Add Custom Booking
```sql
INSERT INTO bookings (user_id, pickup_location, drop_location, load_type, weight, distance, price, status, booking_date, created_at, updated_at) VALUES 
(USER_ID, 'Pickup', 'Drop', 'Type', WEIGHT, DISTANCE, PRICE, 'PENDING', NOW(), NOW(), NOW());
```

## Support

If you encounter issues with sample data:
1. Check MySQL logs for errors
2. Verify foreign key constraints
3. Ensure schema is created first
4. Check user permissions
5. Review TROUBLESHOOTING.md

Happy Testing! 🚀
