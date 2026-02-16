# Project Structure - Smart Truck Transport Management System

## Complete Directory Structure

```
truck-management-system/
│
├── backend/                                    # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/trucksystem/
│   │   │   │   ├── TruckManagementApplication.java    # Main application class
│   │   │   │   │
│   │   │   │   ├── config/                            # Configuration classes
│   │   │   │   │   └── OpenApiConfig.java             # Swagger/OpenAPI config
│   │   │   │   │
│   │   │   │   ├── controller/                        # REST Controllers
│   │   │   │   │   ├── AuthController.java            # Authentication endpoints
│   │   │   │   │   ├── BookingController.java         # User booking endpoints
│   │   │   │   │   └── AdminController.java           # Admin management endpoints
│   │   │   │   │
│   │   │   │   ├── dto/                               # Data Transfer Objects
│   │   │   │   │   ├── AuthRequest.java               # Login request
│   │   │   │   │   ├── AuthResponse.java              # Login/Register response
│   │   │   │   │   ├── RegisterRequest.java           # Registration request
│   │   │   │   │   ├── BookingRequest.java            # Booking creation request
│   │   │   │   │   ├── BookingResponse.java           # Booking response
│   │   │   │   │   ├── TruckDTO.java                  # Truck data transfer
│   │   │   │   │   ├── DriverDTO.java                 # Driver data transfer
│   │   │   │   │   └── DashboardResponse.java         # Dashboard statistics
│   │   │   │   │
│   │   │   │   ├── entity/                            # JPA Entities
│   │   │   │   │   ├── User.java                      # User entity
│   │   │   │   │   ├── Truck.java                     # Truck entity
│   │   │   │   │   ├── Driver.java                    # Driver entity
│   │   │   │   │   ├── Booking.java                   # Booking entity
│   │   │   │   │   └── Payment.java                   # Payment entity
│   │   │   │   │
│   │   │   │   ├── repository/                        # JPA Repositories
│   │   │   │   │   ├── UserRepository.java            # User data access
│   │   │   │   │   ├── TruckRepository.java           # Truck data access
│   │   │   │   │   ├── DriverRepository.java          # Driver data access
│   │   │   │   │   ├── BookingRepository.java         # Booking data access
│   │   │   │   │   └── PaymentRepository.java         # Payment data access
│   │   │   │   │
│   │   │   │   ├── service/                           # Business Logic Layer
│   │   │   │   │   ├── AuthService.java               # Authentication service
│   │   │   │   │   ├── BookingService.java            # Booking management
│   │   │   │   │   ├── TruckService.java              # Truck management
│   │   │   │   │   ├── DriverService.java             # Driver management
│   │   │   │   │   ├── DashboardService.java          # Dashboard statistics
│   │   │   │   │   ├── DistanceService.java           # Distance calculation
│   │   │   │   │   └── EmailService.java              # Email notifications
│   │   │   │   │
│   │   │   │   ├── security/                          # Security Configuration
│   │   │   │   │   ├── SecurityConfig.java            # Spring Security config
│   │   │   │   │   ├── JwtUtil.java                   # JWT token utility
│   │   │   │   │   ├── JwtAuthenticationFilter.java   # JWT filter
│   │   │   │   │   └── CustomUserDetailsService.java  # User details service
│   │   │   │   │
│   │   │   │   └── exception/                         # Exception Handling
│   │   │   │       ├── GlobalExceptionHandler.java    # Global exception handler
│   │   │   │       ├── ResourceNotFoundException.java # Custom exception
│   │   │   │       └── ResourceAlreadyExistsException.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties              # Application configuration
│   │   │       └── schema.sql                          # Database schema
│   │   │
│   │   └── test/                                       # Test classes
│   │
│   └── pom.xml                                         # Maven dependencies
│
├── frontend/                                   # React Frontend
│   ├── public/                                 # Static assets
│   │
│   ├── src/
│   │   ├── components/                         # Reusable components
│   │   │   └── Navbar.jsx                      # Navigation bar
│   │   │
│   │   ├── context/                            # React Context
│   │   │   └── AuthContext.jsx                 # Authentication context
│   │   │
│   │   ├── pages/                              # Page components
│   │   │   ├── Login.jsx                       # Login page
│   │   │   ├── Register.jsx                    # Registration page
│   │   │   ├── UserDashboard.jsx               # User dashboard
│   │   │   ├── BookTruck.jsx                   # Booking form
│   │   │   ├── MyBookings.jsx                  # User bookings list
│   │   │   ├── Payment.jsx                     # Payment page (dummy)
│   │   │   ├── AdminDashboard.jsx              # Admin dashboard with charts
│   │   │   ├── ManageTrucks.jsx                # Truck management
│   │   │   ├── ManageDrivers.jsx               # Driver management
│   │   │   └── ManageBookings.jsx              # Booking management
│   │   │
│   │   ├── services/                           # API services
│   │   │   └── api.js                          # Axios configuration & API calls
│   │   │
│   │   ├── App.jsx                             # Main app component with routing
│   │   ├── main.jsx                            # React entry point
│   │   └── index.css                           # Tailwind CSS imports
│   │
│   ├── index.html                              # HTML template
│   ├── package.json                            # NPM dependencies
│   ├── vite.config.js                          # Vite configuration
│   ├── tailwind.config.js                      # Tailwind CSS configuration
│   └── postcss.config.js                       # PostCSS configuration
│
├── README.md                                   # Project overview
├── SETUP_GUIDE.md                              # Detailed setup instructions
├── API_DOCUMENTATION.md                        # API endpoints documentation
└── PROJECT_STRUCTURE.md                        # This file
```

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: Hibernate/JPA
- **API Documentation**: Swagger/OpenAPI 3.0
- **Email**: Spring Mail
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Styling**: Tailwind CSS
- **Charts**: Chart.js + react-chartjs-2
- **State Management**: React Context API

## Key Features Implementation

### 1. Authentication & Authorization
- **Location**: `backend/src/main/java/com/trucksystem/security/`
- JWT token-based authentication
- Role-based access control (USER, ADMIN)
- Password encryption with BCrypt
- Token expiration handling

### 2. Booking System
- **Backend**: `BookingService.java`, `BookingController.java`
- **Frontend**: `BookTruck.jsx`, `MyBookings.jsx`
- Automatic price calculation
- Distance calculation via Google Maps API
- Email notifications
- Status tracking

### 3. Admin Dashboard
- **Backend**: `DashboardService.java`, `AdminController.java`
- **Frontend**: `AdminDashboard.jsx`
- Real-time statistics
- Chart visualization
- Revenue tracking

### 4. Truck Management
- **Backend**: `TruckService.java`
- **Frontend**: `ManageTrucks.jsx`
- CRUD operations
- Availability status tracking
- Capacity management

### 5. Driver Management
- **Backend**: `DriverService.java`
- **Frontend**: `ManageDrivers.jsx`
- CRUD operations
- Truck assignment
- License tracking

### 6. Booking Assignment
- **Backend**: `BookingService.assignDriver()`
- **Frontend**: `ManageBookings.jsx`
- Assign driver and truck to booking
- Update booking status
- Automatic truck availability update

## Database Schema

### Tables
1. **users**: User accounts (customers and admins)
2. **trucks**: Truck inventory
3. **drivers**: Driver information
4. **bookings**: Booking records
5. **payments**: Payment transactions

### Relationships
- User → Bookings (One-to-Many)
- Truck → Bookings (One-to-Many)
- Driver → Bookings (One-to-Many)
- Truck → Driver (One-to-One via assigned_truck_id)
- Booking → Payment (One-to-One)

## API Architecture

### Layered Architecture
```
Controller Layer (REST endpoints)
    ↓
Service Layer (Business logic)
    ↓
Repository Layer (Data access)
    ↓
Database (MySQL)
```

### Request Flow
1. Client sends HTTP request
2. JWT filter validates token
3. Controller receives request
4. Service processes business logic
5. Repository interacts with database
6. Response sent back to client

## Security Implementation

### JWT Token Flow
1. User logs in with credentials
2. Server validates and generates JWT token
3. Client stores token (localStorage)
4. Client sends token in Authorization header
5. Server validates token on each request
6. Access granted/denied based on role

### Password Security
- Passwords hashed with BCrypt
- Minimum 6 characters required
- Never stored in plain text

## Frontend Routing

### Public Routes
- `/login` - Login page
- `/register` - Registration page

### User Routes (Protected)
- `/dashboard` - User dashboard
- `/book-truck` - Booking form
- `/my-bookings` - Booking history
- `/payment/:bookingId` - Payment page

### Admin Routes (Protected + Admin Role)
- `/admin/dashboard` - Admin dashboard
- `/admin/trucks` - Truck management
- `/admin/drivers` - Driver management
- `/admin/bookings` - Booking management

## State Management

### Authentication State
- Managed by `AuthContext`
- Persisted in localStorage
- Available throughout app via `useAuth()` hook

### Component State
- Local state with `useState`
- API calls with `useEffect`
- Form handling with controlled components

## API Integration

### Axios Configuration
- Base URL configuration
- Request interceptor (adds JWT token)
- Response interceptor (handles 401 errors)
- Centralized error handling

### Service Functions
- `authService`: Login, Register
- `bookingService`: Create, Get bookings
- `adminService`: All admin operations

## Styling Approach

### Tailwind CSS
- Utility-first CSS framework
- Responsive design
- Custom color schemes
- Component-based styling

### Design Patterns
- Card-based layouts
- Modal dialogs
- Table views
- Form inputs with validation feedback
- Status badges with color coding

## Development Workflow

### Backend Development
1. Create entity
2. Create repository
3. Create DTO
4. Create service
5. Create controller
6. Test with Swagger

### Frontend Development
1. Create page component
2. Add routing
3. Create API service function
4. Implement UI with Tailwind
5. Handle state and effects
6. Test in browser

## Production Considerations

### Backend
- Change `ddl-auto` to `validate`
- Disable SQL logging
- Use environment variables
- Enable HTTPS
- Configure CORS properly
- Set up logging

### Frontend
- Build optimized bundle
- Configure production API URL
- Enable compression
- Set up CDN
- Configure caching

### Database
- Regular backups
- Index optimization
- Connection pooling
- Query optimization

## Future Enhancements

1. Real-time tracking with WebSocket
2. Payment gateway integration
3. SMS notifications
4. Mobile app (React Native)
5. Advanced analytics
6. Multi-language support
7. Document upload
8. Rating system
9. Route optimization
10. Fleet management
