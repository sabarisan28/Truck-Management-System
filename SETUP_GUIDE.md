# Smart Truck Transport Management System - Setup Guide

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+
- Node.js 18+ and npm
- MySQL 8.0+
- Git

## Backend Setup (Spring Boot)

### Step 1: Database Configuration

1. Start MySQL server
2. Create database:
```sql
CREATE DATABASE truck_management_db;
```

3. Update `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/truck_management_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 2: Email Configuration (Optional)

For email notifications, configure Gmail SMTP in `application.properties`:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

To get Gmail app password:
1. Go to Google Account settings
2. Enable 2-Step Verification
3. Generate App Password for "Mail"

### Step 3: Google Maps API (Optional)

For distance calculation:
1. Get API key from [Google Cloud Console](https://console.cloud.google.com/)
2. Enable "Distance Matrix API"
3. Update in `application.properties`:
```properties
google.maps.api.key=YOUR_GOOGLE_MAPS_API_KEY
```

Note: Without API key, system will use fallback random distance calculation.

### Step 4: Run Backend

Navigate to backend directory:
```bash
cd backend
```

Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

Or run directly:
```bash
mvn spring-boot:run
```

Backend will start on: http://localhost:8080

### Step 5: Verify Backend

- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Frontend Setup (React + Vite)

### Step 1: Install Dependencies

Navigate to frontend directory:
```bash
cd frontend
```

Install packages:
```bash
npm install
```

### Step 2: Configure API URL (Optional)

The frontend is pre-configured to connect to `http://localhost:8080/api`.

If your backend runs on a different port, update `frontend/src/services/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:YOUR_PORT/api';
```

### Step 3: Run Frontend

Start development server:
```bash
npm run dev
```

Frontend will start on: http://localhost:5173

### Step 4: Build for Production

To create production build:
```bash
npm run build
```

Build files will be in `frontend/dist/`

## Database Initialization

### Option 1: Automatic (Recommended)

The application will automatically create tables on first run due to:
```properties
spring.jpa.hibernate.ddl-auto=update
```

### Option 2: Manual SQL Script

Run the SQL script:
```bash
mysql -u root -p truck_management_db < backend/src/main/resources/schema.sql
```

## Default Admin Account

After first run, create admin user manually or use:

```sql
INSERT INTO users (name, email, password, role) VALUES 
('Admin User', 'admin@trucksystem.com', '$2a$10$xqxQ8Z9X8Z9X8Z9X8Z9X8eKvN5Z9X8Z9X8Z9X8Z9X8Z9X8Z9X8Z9X', 'ADMIN');
```

Note: You'll need to hash the password using BCrypt. Default password in schema is `admin123`.

Or register a new user and manually update role in database:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

## Testing the Application

### 1. Register User
- Go to http://localhost:5173/register
- Create a new account

### 2. Login
- Use registered credentials
- Or use admin credentials

### 3. User Flow
- Book a truck
- View bookings
- Track status

### 4. Admin Flow
- Login as admin
- View dashboard
- Manage trucks and drivers
- Assign drivers to bookings
- Update booking status

## API Testing with Swagger

1. Open Swagger UI: http://localhost:8080/swagger-ui.html
2. Click "Authorize" button
3. Login via `/api/auth/login` endpoint
4. Copy the JWT token from response
5. Enter token in format: `Bearer YOUR_TOKEN`
6. Test other endpoints

## Common Issues & Solutions

### Issue 1: Port Already in Use

Backend (8080):
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

Frontend (5173):
```bash
# Change port in vite.config.js
server: {
  port: 3000
}
```

### Issue 2: Database Connection Failed

- Verify MySQL is running
- Check username/password in application.properties
- Ensure database exists

### Issue 3: CORS Errors

Update `application.properties`:
```properties
cors.allowed.origins=http://localhost:5173,http://localhost:3000
```

### Issue 4: JWT Token Expired

- Token expires after 24 hours (configurable)
- Login again to get new token

## Production Deployment

### Backend

1. Update `application.properties` for production:
```properties
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
```

2. Build JAR:
```bash
mvn clean package
```

3. Run JAR:
```bash
java -jar target/truck-management-1.0.0.jar
```

### Frontend

1. Update API URL in `api.js` to production backend URL
2. Build:
```bash
npm run build
```
3. Deploy `dist/` folder to web server (Nginx, Apache, etc.)

## Environment Variables (Production)

Create `application-prod.properties`:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
google.maps.api.key=${GOOGLE_MAPS_KEY}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

Run with profile:
```bash
java -jar app.jar --spring.profiles.active=prod
```

## Support

For issues or questions:
- Check logs in `backend/logs/`
- Review browser console for frontend errors
- Verify all services are running
- Check database connections

## Next Steps

1. Customize email templates in `EmailService.java`
2. Add more truck types and load categories
3. Implement real payment gateway
4. Add SMS notifications
5. Implement real-time tracking with WebSocket
6. Add reporting and analytics features
