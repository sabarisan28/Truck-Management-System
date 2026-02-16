# Troubleshooting Guide

## Common Issues and Solutions

### Backend Issues

#### 1. Port 8080 Already in Use

**Error**: `Port 8080 is already in use`

**Solution**:

Windows:
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

Linux/Mac:
```bash
lsof -ti:8080 | xargs kill -9
```

Or change port in `application.properties`:
```properties
server.port=8081
```

#### 2. Database Connection Failed

**Error**: `Unable to connect to database`

**Solutions**:

1. Check MySQL is running:
```bash
# Windows
net start MySQL80

# Linux
sudo systemctl start mysql

# Mac
brew services start mysql
```

2. Verify credentials in `application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Check database exists:
```sql
SHOW DATABASES;
```

4. Test connection:
```bash
mysql -u root -p -h localhost
```

#### 3. Maven Build Failed

**Error**: `Failed to execute goal`

**Solutions**:

1. Clean Maven cache:
```bash
mvn clean
mvn dependency:purge-local-repository
```

2. Update Maven:
```bash
mvn -version
# Should be 3.8+
```

3. Check Java version:
```bash
java -version
# Should be 17+
```

#### 4. JWT Token Issues

**Error**: `Invalid JWT token` or `Token expired`

**Solutions**:

1. Check JWT secret in `application.properties`:
```properties
jwt.secret=YOUR_SECRET_KEY_HERE
jwt.expiration=86400000
```

2. Clear browser localStorage:
```javascript
localStorage.clear();
```

3. Login again to get new token

#### 5. Email Sending Failed

**Error**: `Failed to send email`

**Solutions**:

1. Enable "Less secure app access" in Gmail (not recommended)
2. Use App Password:
   - Go to Google Account → Security
   - Enable 2-Step Verification
   - Generate App Password
   - Use in `application.properties`

3. Check SMTP settings:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

#### 6. Google Maps API Error

**Error**: `Distance calculation failed`

**Solutions**:

1. Check API key is valid
2. Enable Distance Matrix API in Google Cloud Console
3. Check billing is enabled
4. System will use fallback if API fails

#### 7. Hibernate/JPA Errors

**Error**: `Table doesn't exist` or `Column not found`

**Solutions**:

1. Check `ddl-auto` setting:
```properties
spring.jpa.hibernate.ddl-auto=update
```

2. Run schema manually:
```bash
mysql -u root -p truck_management_db < backend/src/main/resources/schema.sql
```

3. Check entity mappings match database

### Frontend Issues

#### 1. Port 5173 Already in Use

**Error**: `Port 5173 is already in use`

**Solution**:

Change port in `vite.config.js`:
```javascript
export default defineConfig({
  server: {
    port: 3000
  }
})
```

#### 2. npm install Failed

**Error**: `npm ERR!` during installation

**Solutions**:

1. Clear npm cache:
```bash
npm cache clean --force
```

2. Delete node_modules and package-lock.json:
```bash
rm -rf node_modules package-lock.json
npm install
```

3. Use different registry:
```bash
npm config set registry https://registry.npmjs.org/
```

4. Check Node version:
```bash
node -v
# Should be 18+
```

#### 3. CORS Errors

**Error**: `Access to XMLHttpRequest blocked by CORS policy`

**Solutions**:

1. Check backend CORS configuration in `application.properties`:
```properties
cors.allowed.origins=http://localhost:5173,http://localhost:3000
```

2. Verify backend is running on port 8080

3. Check API base URL in `frontend/src/services/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

#### 4. API Calls Failing

**Error**: `Network Error` or `404 Not Found`

**Solutions**:

1. Check backend is running:
```bash
curl http://localhost:8080/api-docs
```

2. Verify API endpoints in Swagger UI

3. Check browser console for errors (F12)

4. Verify token is being sent:
```javascript
// In browser console
localStorage.getItem('token')
```

#### 5. React Router Not Working

**Error**: `Cannot GET /some-route` on refresh

**Solutions**:

1. For development, Vite handles this automatically

2. For production, configure server:

Nginx:
```nginx
location / {
  try_files $uri $uri/ /index.html;
}
```

Apache (.htaccess):
```apache
RewriteEngine On
RewriteBase /
RewriteRule ^index\.html$ - [L]
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule . /index.html [L]
```

#### 6. Tailwind CSS Not Working

**Error**: Styles not applying

**Solutions**:

1. Check `tailwind.config.js` content paths:
```javascript
content: [
  "./index.html",
  "./src/**/*.{js,ts,jsx,tsx}",
]
```

2. Verify imports in `index.css`:
```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

3. Restart dev server:
```bash
npm run dev
```

#### 7. Chart.js Not Rendering

**Error**: Charts not displaying

**Solutions**:

1. Check Chart.js registration in component:
```javascript
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement } from 'chart.js';
ChartJS.register(CategoryScale, LinearScale, BarElement);
```

2. Verify data format matches chart type

3. Check container has height:
```jsx
<div style={{ height: '400px' }}>
  <Bar data={chartData} />
</div>
```

### Database Issues

#### 1. MySQL Won't Start

**Solutions**:

Windows:
```bash
net start MySQL80
```

Linux:
```bash
sudo systemctl start mysql
sudo systemctl status mysql
```

Mac:
```bash
brew services start mysql
```

#### 2. Access Denied for User

**Error**: `Access denied for user 'root'@'localhost'`

**Solutions**:

1. Reset MySQL password:
```bash
# Stop MySQL
# Start in safe mode
mysqld --skip-grant-tables

# In another terminal
mysql -u root
UPDATE mysql.user SET authentication_string=PASSWORD('newpassword') WHERE User='root';
FLUSH PRIVILEGES;
```

2. Create new user:
```sql
CREATE USER 'truckuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON truck_management_db.* TO 'truckuser'@'localhost';
FLUSH PRIVILEGES;
```

#### 3. Table Already Exists

**Error**: `Table 'users' already exists`

**Solutions**:

1. Drop and recreate:
```sql
DROP DATABASE truck_management_db;
CREATE DATABASE truck_management_db;
```

2. Change `ddl-auto`:
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

#### 4. Foreign Key Constraint Failed

**Error**: `Cannot add or update a child row`

**Solutions**:

1. Check referenced records exist
2. Disable foreign key checks temporarily:
```sql
SET FOREIGN_KEY_CHECKS=0;
-- Your operations
SET FOREIGN_KEY_CHECKS=1;
```

### Authentication Issues

#### 1. Cannot Login

**Solutions**:

1. Check user exists:
```sql
SELECT * FROM users WHERE email = 'your-email@example.com';
```

2. Verify password is hashed:
```sql
-- Password should start with $2a$ (BCrypt)
```

3. Check credentials are correct

4. Clear browser cache and cookies

#### 2. Token Not Working

**Solutions**:

1. Check token format:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

2. Verify token hasn't expired (24 hours default)

3. Check JWT secret matches in backend

4. Login again to get fresh token

#### 3. Admin Routes Not Accessible

**Solutions**:

1. Check user role:
```sql
SELECT role FROM users WHERE email = 'your-email@example.com';
```

2. Update role if needed:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

3. Logout and login again

### Performance Issues

#### 1. Slow API Responses

**Solutions**:

1. Add database indexes:
```sql
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_booking_status ON bookings(status);
```

2. Enable query caching

3. Optimize N+1 queries with JOIN FETCH

4. Use pagination for large datasets

#### 2. Frontend Loading Slowly

**Solutions**:

1. Build for production:
```bash
npm run build
```

2. Enable compression in Vite

3. Lazy load components:
```javascript
const AdminDashboard = lazy(() => import('./pages/AdminDashboard'));
```

4. Optimize images and assets

### Deployment Issues

#### 1. Production Build Fails

**Backend**:
```bash
# Check for compilation errors
mvn clean compile

# Run tests
mvn test

# Build without tests
mvn clean package -DskipTests
```

**Frontend**:
```bash
# Clear cache
rm -rf node_modules dist
npm install
npm run build
```

#### 2. Environment Variables Not Working

**Solutions**:

1. Create `.env` file in frontend:
```
VITE_API_URL=http://your-backend-url/api
```

2. Use in code:
```javascript
const API_BASE_URL = import.meta.env.VITE_API_URL;
```

3. For backend, use `application-prod.properties`

#### 3. Database Connection in Production

**Solutions**:

1. Use environment variables:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

2. Check firewall allows database connections

3. Verify database host and port

## Debugging Tips

### Backend Debugging

1. Enable debug logging:
```properties
logging.level.com.trucksystem=DEBUG
logging.level.org.springframework.security=DEBUG
```

2. Use Swagger UI for API testing

3. Check application logs

4. Use IDE debugger with breakpoints

### Frontend Debugging

1. Use React DevTools browser extension

2. Check browser console (F12)

3. Use Network tab to inspect API calls

4. Add console.log statements:
```javascript
console.log('User data:', user);
console.log('API response:', response.data);
```

5. Use Redux DevTools if using Redux

### Database Debugging

1. Enable SQL logging:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

2. Check MySQL logs:
```bash
# Linux
tail -f /var/log/mysql/error.log

# Windows
# Check MySQL data directory
```

3. Use MySQL Workbench for visual debugging

## Getting Help

### Before Asking for Help

1. Check this troubleshooting guide
2. Review error messages carefully
3. Check application logs
4. Search for similar issues online
5. Verify all prerequisites are installed

### Where to Get Help

1. GitHub Issues
2. Stack Overflow
3. Spring Boot Documentation
4. React Documentation
5. MySQL Documentation

### Information to Provide

When reporting issues, include:

1. Error message (full stack trace)
2. Steps to reproduce
3. Environment details:
   - OS version
   - Java version
   - Node version
   - MySQL version
4. Configuration files (remove sensitive data)
5. Relevant code snippets
6. What you've already tried

## Preventive Measures

### Regular Maintenance

1. Keep dependencies updated:
```bash
# Backend
mvn versions:display-dependency-updates

# Frontend
npm outdated
```

2. Regular database backups:
```bash
mysqldump -u root -p truck_management_db > backup_$(date +%Y%m%d).sql
```

3. Monitor logs regularly

4. Test after updates

### Best Practices

1. Use version control (Git)
2. Write tests
3. Document changes
4. Use environment variables for sensitive data
5. Regular code reviews
6. Keep documentation updated

## Still Having Issues?

If you've tried everything and still facing issues:

1. Create a minimal reproducible example
2. Open an issue on GitHub with detailed information
3. Check if it's a known issue
4. Consider asking on Stack Overflow with relevant tags

Remember: Most issues have been encountered by others before. Search thoroughly before asking for help!
