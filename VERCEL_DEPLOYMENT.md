# 🚀 Deploy to Vercel - Step by Step Guide

## ⚠️ Important Note
Vercel is designed for **frontend only** (static sites and serverless functions). 
For the backend, you'll need to use **Render.com** (free) or **Railway.app**.

## 📋 Complete Deployment Plan

### Frontend → Vercel (Free)
### Backend → Render.com (Free)
### Database → Render PostgreSQL (Free)

---

## STEP 1: Prepare Frontend for Vercel

### 1.1 Update API Configuration

The frontend needs to connect to your hosted backend URL.

Update `frontend/src/services/api.js` to use environment variable:

```javascript
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
```

This is already done in your project!

---

## STEP 2: Push to GitHub

```cmd
cd C:\Users\sabar\OneDrive\ドキュメント\trans-truck

git add .
git commit -m "Prepare for Vercel deployment"
git push origin main
```

---

## STEP 3: Deploy Frontend to Vercel

### 3.1 Sign Up / Login to Vercel

1. Go to [https://vercel.com](https://vercel.com)
2. Click "Sign Up" or "Login"
3. Choose "Continue with GitHub"
4. Authorize Vercel to access your GitHub

### 3.2 Import Your Project

1. Click "Add New..." → "Project"
2. Find your repository: `truck-management-system`
3. Click "Import"

### 3.3 Configure Project Settings

**Root Directory:**
```
frontend
```

**Framework Preset:**
```
Vite
```

**Build Command:**
```
npm run build
```

**Output Directory:**
```
dist
```

**Install Command:**
```
npm install
```

### 3.4 Add Environment Variable

Click "Environment Variables" and add:

**Key:**
```
VITE_API_URL
```

**Value:** (You'll update this after deploying backend)
```
https://your-backend-url.onrender.com/api
```

For now, use a placeholder:
```
https://truck-backend.onrender.com/api
```

### 3.5 Deploy!

Click "Deploy" button and wait 2-3 minutes.

Your frontend will be live at:
```
https://truck-management-system.vercel.app
```

---

## STEP 4: Deploy Backend to Render.com

### 4.1 Sign Up to Render

1. Go to [https://render.com](https://render.com)
2. Click "Get Started"
3. Sign up with GitHub

### 4.2 Create PostgreSQL Database

1. Click "New +" → "PostgreSQL"
2. **Name:** `truck-database`
3. **Database:** `truck_db`
4. **User:** `truck_user`
5. **Region:** Choose closest to you
6. **Plan:** Free
7. Click "Create Database"
8. **Copy the "Internal Database URL"** - you'll need this!

### 4.3 Deploy Backend Web Service

1. Click "New +" → "Web Service"
2. Connect your GitHub repository
3. **Name:** `truck-backend`
4. **Region:** Same as database
5. **Branch:** `main`
6. **Root Directory:** `backend`
7. **Environment:** `Java`
8. **Build Command:**
```
mvn clean install -DskipTests
```
9. **Start Command:**
```
java -jar target/truck-management-1.0.0.jar
```
10. **Instance Type:** Free

### 4.4 Add Environment Variables

Click "Environment" tab and add these:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://[paste-internal-db-url]/truck_db
SPRING_DATASOURCE_USERNAME=truck_user
SPRING_DATASOURCE_PASSWORD=[your-db-password]
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
CORS_ALLOWED_ORIGINS=https://truck-management-system.vercel.app
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production
SPRING_PROFILES_ACTIVE=prod
```

Replace:
- `[paste-internal-db-url]` with your database URL
- `[your-db-password]` with your database password
- Update `CORS_ALLOWED_ORIGINS` with your actual Vercel URL

### 4.5 Deploy Backend

Click "Create Web Service"

Wait 5-10 minutes for deployment.

Your backend will be live at:
```
https://truck-backend.onrender.com
```

---

## STEP 5: Update Frontend with Backend URL

### 5.1 Update Vercel Environment Variable

1. Go to your Vercel project dashboard
2. Click "Settings" → "Environment Variables"
3. Edit `VITE_API_URL` to:
```
https://truck-backend.onrender.com/api
```
4. Click "Save"

### 5.2 Redeploy Frontend

1. Go to "Deployments" tab
2. Click "..." on latest deployment
3. Click "Redeploy"

---

## STEP 6: Update Backend CORS

Make sure your backend allows your Vercel URL.

In Render backend environment variables, update:
```
CORS_ALLOWED_ORIGINS=https://truck-management-system.vercel.app
```

(Replace with your actual Vercel URL)

---

## ✅ DONE! Your App is Live!

**Frontend:** https://truck-management-system.vercel.app
**Backend:** https://truck-backend.onrender.com

---

## 🧪 Test Your Deployment

1. Open your Vercel URL
2. Try to register a new user
3. Login with credentials
4. Create a booking
5. Check if data is saved

---

## 📝 Files to Update Before Deployment

### 1. Add PostgreSQL Dependency

Update `backend/pom.xml`, add this dependency:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Create Production Properties

Create `backend/src/main/resources/application-prod.properties`:

```properties
# Database
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
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

### 3. Commit and Push

```cmd
git add .
git commit -m "Add production configuration for deployment"
git push origin main
```

---

## ⚠️ Important Notes

### Free Tier Limitations:

**Vercel:**
- ✅ Unlimited bandwidth
- ✅ Automatic HTTPS
- ✅ Fast CDN
- ✅ Instant deployments

**Render (Free):**
- ⚠️ Services sleep after 15 minutes of inactivity
- ⚠️ First request after sleep takes 30-60 seconds
- ✅ 750 hours/month free
- ✅ PostgreSQL database included

### Solutions for Sleeping Backend:

1. **Use a cron job** to ping your backend every 14 minutes
2. **Upgrade to paid plan** ($7/month - no sleeping)
3. **Use Railway.app** instead ($5 free credit/month)

---

## 🔄 Auto-Deploy on Git Push

Both Vercel and Render will automatically redeploy when you push to GitHub!

```cmd
# Make changes
git add .
git commit -m "Update feature"
git push origin main

# Vercel and Render will auto-deploy!
```

---

## 🆘 Troubleshooting

### Frontend shows "Network Error":
- Check if backend URL is correct in Vercel env variables
- Verify backend is running on Render
- Check browser console for CORS errors

### Backend won't start:
- Check Render logs
- Verify database connection string
- Ensure all environment variables are set

### Database connection failed:
- Use "Internal Database URL" from Render
- Check username and password
- Verify PostgreSQL dependency in pom.xml

### CORS Error:
- Update `CORS_ALLOWED_ORIGINS` in Render
- Include your exact Vercel URL
- Redeploy backend after changing

---

## 📱 Your Live URLs

After deployment, share these:

**Application:** https://truck-management-system.vercel.app
**API:** https://truck-backend.onrender.com/api
**GitHub:** https://github.com/YOUR_USERNAME/truck-management-system

---

**Ready to deploy? Follow the steps above!** 🚀
