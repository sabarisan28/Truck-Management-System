# API Documentation - Truck Management System

Base URL: `http://localhost:8080/api`

## Authentication

All endpoints except `/auth/*` require JWT token in header:
```
Authorization: Bearer <token>
```

---

## Authentication Endpoints

### 1. Register User
**POST** `/auth/register`

Request Body:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

Response (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

### 2. Login
**POST** `/auth/login`

Request Body:
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

Response (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER"
}
```

---

## User Booking Endpoints

### 3. Create Booking
**POST** `/bookings`

Headers:
```
Authorization: Bearer <token>
```

Request Body:
```json
{
  "pickupLocation": "New York, NY",
  "dropLocation": "Boston, MA",
  "loadType": "Furniture",
  "weight": 5.5
}
```

Response (200 OK):
```json
{
  "id": 1,
  "userId": 1,
  "userName": "John Doe",
  "truckId": null,
  "truckNumber": null,
  "driverId": null,
  "driverName": null,
  "pickupLocation": "New York, NY",
  "dropLocation": "Boston, MA",
  "loadType": "Furniture",
  "weight": 5.5,
  "distance": 215.5,
  "price": 591.25,
  "status": "PENDING",
  "bookingDate": "2024-01-15T10:30:00"
}
```

Price Calculation:
- Base Rate: $50
- Distance Rate: $2.50 per km
- Weight Rate: $0.50 per ton
- Total = Base + (Distance × 2.50) + (Weight × 0.50)

### 4. Get My Bookings
**GET** `/bookings/my-bookings`

Headers:
```
Authorization: Bearer <token>
```

Response (200 OK):
```json
[
  {
    "id": 1,
    "userId": 1,
    "userName": "John Doe",
    "truckId": 1,
    "truckNumber": "TRK-001",
    "driverId": 1,
    "driverName": "Mike Johnson",
    "pickupLocation": "New York, NY",
    "dropLocation": "Boston, MA",
    "loadType": "Furniture",
    "weight": 5.5,
    "distance": 215.5,
    "price": 591.25,
    "status": "ASSIGNED",
    "bookingDate": "2024-01-15T10:30:00"
  }
]
```

### 5. Get Booking by ID
**GET** `/bookings/{id}`

Headers:
```
Authorization: Bearer <token>
```

Response (200 OK): Same as Create Booking response

---

## Admin Dashboard Endpoint

### 6. Get Dashboard Statistics
**GET** `/admin/dashboard`

Headers:
```
Authorization: Bearer <token>
```

Role Required: `ADMIN`

Response (200 OK):
```json
{
  "totalUsers": 150,
  "totalBookings": 320,
  "totalRevenue": 45678.50,
  "pendingBookings": 12,
  "completedBookings": 280
}
```

---

## Admin Truck Management

### 7. Get All Trucks
**GET** `/admin/trucks`

Response (200 OK):
```json
[
  {
    "id": 1,
    "truckNumber": "TRK-001",
    "type": "Flatbed",
    "capacity": 10.00,
    "availabilityStatus": "AVAILABLE"
  }
]
```

### 8. Get Truck by ID
**GET** `/admin/trucks/{id}`

Response (200 OK): Same as single truck object

### 9. Create Truck
**POST** `/admin/trucks`

Request Body:
```json
{
  "truckNumber": "TRK-005",
  "type": "Box Truck",
  "capacity": 15.00
}
```

Response (200 OK): Created truck object

### 10. Update Truck
**PUT** `/admin/trucks/{id}`

Request Body:
```json
{
  "truckNumber": "TRK-005",
  "type": "Box Truck",
  "capacity": 15.00,
  "availabilityStatus": "MAINTENANCE"
}
```

Response (200 OK): Updated truck object

### 11. Delete Truck
**DELETE** `/admin/trucks/{id}`

Response (204 No Content)

---

## Admin Driver Management

### 12. Get All Drivers
**GET** `/admin/drivers`

Response (200 OK):
```json
[
  {
    "id": 1,
    "name": "Mike Johnson",
    "phone": "+1234567890",
    "licenseNumber": "DL-12345",
    "assignedTruckId": 1,
    "assignedTruckNumber": "TRK-001"
  }
]
```

### 13. Get Driver by ID
**GET** `/admin/drivers/{id}`

Response (200 OK): Same as single driver object

### 14. Create Driver
**POST** `/admin/drivers`

Request Body:
```json
{
  "name": "Sarah Williams",
  "phone": "+1234567893",
  "licenseNumber": "DL-12348",
  "assignedTruckId": 2
}
```

Response (200 OK): Created driver object

### 15. Update Driver
**PUT** `/admin/drivers/{id}`

Request Body: Same as Create Driver

Response (200 OK): Updated driver object

### 16. Delete Driver
**DELETE** `/admin/drivers/{id}`

Response (204 No Content)

---

## Admin Booking Management

### 17. Get All Bookings
**GET** `/admin/bookings`

Response (200 OK): Array of booking objects

### 18. Assign Driver to Booking
**PUT** `/admin/bookings/{id}/assign`

Query Parameters:
- `driverId`: Driver ID (required)
- `truckId`: Truck ID (required)

Example:
```
PUT /admin/bookings/1/assign?driverId=1&truckId=1
```

Response (200 OK): Updated booking object with assigned driver and truck

### 19. Update Booking Status
**PUT** `/admin/bookings/{id}/status`

Query Parameters:
- `status`: New status (required)

Valid statuses:
- `PENDING`
- `ASSIGNED`
- `IN_TRANSIT`
- `DELIVERED`
- `CANCELLED`

Example:
```
PUT /admin/bookings/1/status?status=IN_TRANSIT
```

Response (200 OK): Updated booking object

---

## Error Responses

### 400 Bad Request
```json
{
  "fieldName": "Error message"
}
```

### 401 Unauthorized
```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Resource not found",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 409 Conflict
```json
{
  "status": 409,
  "message": "Email already registered",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "An error occurred: ...",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## Status Codes

- `200 OK`: Request successful
- `201 Created`: Resource created
- `204 No Content`: Successful deletion
- `400 Bad Request`: Validation error
- `401 Unauthorized`: Authentication failed
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource already exists
- `500 Internal Server Error`: Server error

---

## Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

### Create Booking (with token)
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"pickupLocation":"New York","dropLocation":"Boston","loadType":"Furniture","weight":5.5}'
```

---

## Swagger UI

Interactive API documentation available at:
http://localhost:8080/swagger-ui.html

Features:
- Try out endpoints directly
- View request/response schemas
- Test authentication
- See all available operations
