# 🚚 Smart Truck Transport Management System

A full-stack web application for managing truck transport operations with user and admin functionalities.

## 🛠️ Tech Stack

### Frontend
- React 18
- Vite
- React Router
- Axios
- Tailwind CSS
- Chart.js

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Security
- JWT Authentication
- JPA/Hibernate
- MySQL

## ✨ Features

### User Features
- User registration and login with JWT authentication
- Book trucks with automatic distance-based pricing
- View booking history
- Track booking status (Pending/Assigned/Delivered)
- Responsive dashboard

### Admin Features
- Admin dashboard with statistics
- Manage trucks (CRUD operations)
- Manage drivers (CRUD operations)
- View and manage all bookings
- Assign drivers to bookings
- Update booking status
- Revenue tracking with charts

## 📋 Prerequisites

- Java 17 or higher
- Node.js 16+ and npm
- MySQL 8.0+
- Maven 3.6+

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/truck-management-system.git
cd truck-management-system
```

### 2. Database Setup
```sql
CREATE DATABASE truck_management_db;
```

### 3. Backend Setup

Navigate to backend folder:
```bash
cd backend
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/truck_management_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Run the backend:
```bash
mvn spring-boot:run
```

Backend will start on: `http://localhost:8080`

### 4. Frontend Setup

Navigate to frontend folder:
```bash
cd frontend
```

Install dependencies:
```bash
npm install
```

Run the frontend:
```bash
npm run dev
```

Frontend will start on: `http://localhost:5173`

## 📱 Usage

1. Open `http://localhost:5173` in your browser
2. Register a new account
3. Login with your credentials
4. Start booking trucks!

### Admin Access
To access admin features, update user role in database:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your@email.com';
```

## 📁 Project Structure

```
truck-management-system/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/trucksystem/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   ├── security/
│   │   │   │   └── exception/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── context/
│   │   └── App.jsx
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## 🔐 Security Features

- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control (USER, ADMIN)
- CORS configuration
- Secure API endpoints

## 📚 API Documentation

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings/my-bookings` - Get user bookings

### Admin (Requires ADMIN role)
- `GET /api/admin/dashboard` - Dashboard statistics
- `GET /api/admin/trucks` - List trucks
- `POST /api/admin/trucks` - Add truck
- `GET /api/admin/drivers` - List drivers
- `POST /api/admin/drivers` - Add driver
- `GET /api/admin/bookings` - List all bookings

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Your Name - [Your GitHub Profile](https://github.com/YOUR_USERNAME)

## 🙏 Acknowledgments

- Spring Boot Documentation
- React Documentation
- Tailwind CSS
- Chart.js

---

⭐ Star this repository if you find it helpful!
