# 🌐 How to Host Your Project Online (FREE)

## Option 1: Render.com (Recommended - Easiest)

### ✅ Advantages:
- Free tier available
- Hosts both frontend and backend
- Free PostgreSQL database
- Automatic deployments from GitHub
- HTTPS included

### 📋 Steps:

#### 1. Push to GitHub First
```cmd
git remote add origin https://github.com/YOUR_USERNAME/truck-management-system.git
git push -u origin main
```

#### 2. Deploy Backend on Render

1. Go to [Render.com](https://render.com) and sign up
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: truck-backend
   - **Environment**: Java
   - **Build Command**: `cd backend && mvn clean install -DskipTests`
   - **Start Command**: `cd backend && java -jar target/truck-management-1.0.0.jar`
   - **Instance Type**: Free

5. Add Environment Variables:
   ```
   SPRING_DATASOURCE_URL=jdbc:postgresql://[your-db-url]/truck_db
   SPRING_DATASOURCE_USERNAME=your_username
   SPRING_DATASOURCE_PASSWORD=your_password
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
   CORS_ALLOWED_ORIGINS=https://your-frontend-url.onrender.com
   ```

6. Click "Create Web Service"

#### 3. Create PostgreSQL Database on Render

1. Click "New +" → "PostgreSQL"
2. Name: truck-database
3. Click "Create Database"
4. Copy the "Internal Database URL"
5. Add it to backend environment variables

#### 4. Deploy Frontend on Render

1. Click "New +" → "Static Site"
2. Connect same GitHub repository
3. Configure:
   - **Name**: truck-frontend
   - **Build Command**: `cd frontend && npm install && npm run build`
   - **Publish Directory**: `frontend/dist`

4. Add Environment Variable:
   ```
   VITE_API_URL=https://truck-backend.onrender.com/api
   ```

5. Update `frontend/src/services/api.js`:
   ```javascript
   const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
   ```

6. Click "Create Static Site"

---

## Option 2: Railway.app

### ✅ Advantages:
- $5 free credit monthly
- Easy deployment
- PostgreSQL included
- GitHub integration

### 📋 Steps:

1. Go to [Railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Add PostgreSQL database
6. Configure environment variables
7. Deploy!

---

## Option 3: Vercel (Frontend) + Render (Backend)

### Frontend on Vercel:

1. Go to [Vercel.com](https://vercel.com)
2. Sign up with GitHub
3. Click "New Project"
4. Import your repository
5. Configure:
   - **Framework Preset**: Vite
   - **Root Directory**: frontend
   - **Build Command**: `npm run build`
   - **Output Directory**: dist

6. Add Environment Variable:
   ```
   VITE_API_URL=https://your-backend-url.onrender.com/api
   ```

7. Deploy!

### Backend on Render:
Follow steps from Option 1 (Backend section)

---

## Option 4: Netlify (Frontend) + Heroku (Backend)

### Frontend on Netlify:

1. Go to [Netlify.com](https://netlify.com)
2. Sign up with GitHub
3. Click "Add new site" → "Import an existing project"
4. Select your repository
5. Configure:
   - **Base directory**: frontend
   - **Build command**: `npm run build`
   - **Publish directory**: frontend/dist

6. Deploy!

### Backend on Heroku:

1. Go to [Heroku.com](https://heroku.com)
2. Create new app
3. Connect GitHub repository
4. Add PostgreSQL addon
5. Configure buildpack: `heroku/java`
6. Deploy!

---

## 📝 Required Changes for Deployment

### 1. Update Backend `pom.xml`

Add PostgreSQL dependency (for Render/Railway):

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Update `application.properties`

Create `application-prod.properties`:

```properties
# Database (will be overridden by environment variables)
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server
server.port=${PORT:8080}

# CORS
cors.allowed.origins=${CORS_ALLOWED_ORIGINS}

# JWT
jwt.secret=${JWT_SECRET:your-secret-key-change-in-production}
jwt.expiration=86400000
```

### 3. Update Frontend API Configuration

In `frontend/src/services/api.js`:

```javascript
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
```

### 4. Add `render.yaml` (Optional - for Render)

Create in root directory:

```yaml
services:
  - type: web
    name: truck-backend
    env: java
    buildCommand: cd backend && mvn clean install -DskipTests
    startCommand: cd backend && java -jar target/truck-management-1.0.0.jar
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: prod
      - key: DATABASE_URL
        fromDatabase:
          name: truck-database
          property: connectionString

  - type: web
    name: truck-frontend
    env: static
    buildCommand: cd frontend && npm install && npm run build
    staticPublishPath: frontend/dist

databases:
  - name: truck-database
    databaseName: truck_db
    user: truck_user
```

---

## 🚀 Quick Deploy Commands

### After pushing to GitHub:

```cmd
# Commit deployment changes
git add .
git commit -m "Add deployment configuration"
git push origin main
```

Then follow the hosting platform steps above!

---

## 🔗 Your Live URLs Will Be:

**Render:**
- Frontend: `https://truck-frontend.onrender.com`
- Backend: `https://truck-backend.onrender.com`

**Vercel + Render:**
- Frontend: `https://truck-management.vercel.app`
- Backend: `https://truck-backend.onrender.com`

**Netlify + Heroku:**
- Frontend: `https://truck-management.netlify.app`
- Backend: `https://truck-backend.herokuapp.com`

---

## ⚠️ Important Notes

1. **Free tier limitations:**
   - Render: Services sleep after 15 min of inactivity
   - Heroku: 550-1000 free hours/month
   - Vercel/Netlify: Unlimited for static sites

2. **Database:**
   - Switch from MySQL to PostgreSQL for free hosting
   - Or use PlanetScale (free MySQL hosting)

3. **Environment Variables:**
   - Never commit sensitive data
   - Use platform's environment variable settings

4. **First deployment:**
   - May take 5-10 minutes
   - Backend might need restart after database connection

---

## 🆘 Troubleshooting

### Backend won't start:
- Check environment variables
- Verify database connection string
- Check logs in hosting platform

### Frontend can't connect to backend:
- Update CORS settings in backend
- Verify API_BASE_URL in frontend
- Check if backend is running

### Database connection failed:
- Verify database credentials
- Check if database is created
- Ensure correct dialect (PostgreSQL vs MySQL)

---

## 📚 Additional Resources

- [Render Documentation](https://render.com/docs)
- [Vercel Documentation](https://vercel.com/docs)
- [Railway Documentation](https://docs.railway.app)
- [Netlify Documentation](https://docs.netlify.com)

---

**Choose Option 1 (Render) for the easiest all-in-one solution!**
