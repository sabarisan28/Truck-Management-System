# Quick Start Guide - 5 Minutes Setup

## Prerequisites Check

```bash
# Check Java
java -version
# Should show Java 17 or higher

# Check Maven
mvn -version

# Check Node.js
node -v
# Should show v18 or higher

# Check MySQL
mysql --version
```

## Step 1: Database Setup (1 minute)

```bash
# Start MySQL and create database
mysql -u root -p

# In MySQL prompt:
CREATE DATABASE truck_management_db;
EXIT;
```

## Step 2: Backend Setup (2 minutes)

```bash
# Navigate to backend
cd backend

# Update application.properties with your MySQL credentials
# Edit: src/main/resources/application.properties
# Change: spring.datasource.username and spring.datasource.password

# Run backend
mvn spring-boot:run
```

Backend will start on http://localhost:8080

## Step 3: Frontend Setup (2 minutes)

Open a new terminal:

```bash
# Navigate to frontend
cd frontend

# Install dependencies
npm install

# Start frontend
npm run dev
```

Frontend will start on http://localhost:5173

## Step 4: Test the Application

### Create Admin User

Option 1 - Via Database:
```sql
mysql -u root -p truck_management_db

INSERT INTO users (name, email, password, role) VALUES 
('Admin', 'admin@test.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');
# Password is: admin123
```

Option 2 - Register and Update:
1. Go to http://localhost:5173/register
2. Register a new account
3. Update role in database:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

### Test User Flow

1. Open http://localhost:5173
2. Register a new user account
3. Login with credentials
4. Click "Book a Truck"
5. Fill in booking details
6. View "My Bookings"

### Test Admin Flow

1. Login with admin credentials
2. View dashboard statistics
3. Add a truck (Trucks page)
4. Add a driver (Drivers page)
5. Assign driver to booking (Bookings page)

## Troubleshooting

### Backend won't start
```bash
# Check if port 8080 is in use
netstat -ano | findstr :8080

# Kill the process or change port in application.properties
```

### Frontend won't start
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and reinstall
rm -rf node_modules
npm install
```

### Database connection error
- Verify MySQL is running
- Check username/password in application.properties
- Ensure database exists

### CORS errors
- Verify backend is running on port 8080
- Check CORS configuration in application.properties

## Default Credentials

After running schema.sql or manual insert:

**Admin:**
- Email: admin@test.com
- Password: admin123

## API Testing

Swagger UI: http://localhost:8080/swagger-ui.html

## Next Steps

1. Read SETUP_GUIDE.md for detailed configuration
2. Check API_DOCUMENTATION.md for API details
3. Review PROJECT_STRUCTURE.md for architecture

## Common Commands

### Backend
```bash
# Clean build
mvn clean install

# Run tests
mvn test

# Package JAR
mvn package

# Run JAR
java -jar target/truck-management-1.0.0.jar
```

### Frontend
```bash
# Development
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

### Database
```bash
# Backup database
mysqldump -u root -p truck_management_db > backup.sql

# Restore database
mysql -u root -p truck_management_db < backup.sql

# Run schema
mysql -u root -p truck_management_db < backend/src/main/resources/schema.sql
```

## Production Deployment

### Backend
```bash
cd backend
mvn clean package
java -jar target/truck-management-1.0.0.jar --spring.profiles.active=prod
```

### Frontend
```bash
cd frontend
npm run build
# Deploy dist/ folder to web server
```

## Support

- Backend logs: Check console output
- Frontend errors: Check browser console (F12)
- Database issues: Check MySQL logs

## Success Indicators

✅ Backend running: http://localhost:8080/swagger-ui.html loads
✅ Frontend running: http://localhost:5173 loads
✅ Database connected: No connection errors in backend logs
✅ Can register user: Registration form works
✅ Can login: Login redirects to dashboard
✅ Can create booking: Booking form submits successfully

Enjoy building with the Truck Management System! 🚚
