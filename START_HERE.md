# 🚀 Quick Start - Truck Management System

## ✅ FRONTEND IS RUNNING!

Your React frontend is successfully running at:

### **http://localhost:5173**

You can open this in your browser right now to see the UI!

---

## ❌ Backend Issue: Java 25 Compatibility

The backend cannot start because **Java 25 is too new** for Maven and Spring Boot 3.2.0.

### Solution: Install Java 17 (Recommended)

#### Step 1: Download Java 17
Go to: https://www.oracle.com/java/technologies/downloads/#java17

Or use this direct link:
https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe

#### Step 2: Install Java 17
- Run the installer
- Default installation path: `C:\Program Files\Java\jdk-17`

#### Step 3: Set JAVA_HOME
Open PowerShell as Administrator and run:
```powershell
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-17', 'Machine')
```

Or manually:
1. Search "Environment Variables" in Windows
2. Click "Environment Variables"
3. Under "System Variables", click "New"
4. Variable name: `JAVA_HOME`
5. Variable value: `C:\Program Files\Java\jdk-17`
6. Click OK

#### Step 4: Update PATH
Add to PATH: `%JAVA_HOME%\bin`

#### Step 5: Restart PowerShell and Run
```powershell
cd backend
..\apache-maven-3.9.5\bin\mvn.cmd spring-boot:run
```

---

## Alternative: Use IDE (Easier!)

### Option A: IntelliJ IDEA (Recommended)

1. Download IntelliJ IDEA Community (Free): https://www.jetbrains.com/idea/download/
2. Open IntelliJ IDEA
3. Click "Open" and select the `backend` folder
4. Wait for Maven to download dependencies
5. Find `TruckManagementApplication.java`
6. Right-click → Run

### Option B: VS Code with Extensions

1. Install "Extension Pack for Java" in VS Code
2. Install "Spring Boot Extension Pack"
3. Open `backend` folder
4. Press F5 to run

### Option C: Eclipse

1. Download Eclipse IDE for Java Developers
2. File → Import → Existing Maven Projects
3. Select `backend` folder
4. Right-click project → Run As → Spring Boot App

---

## What You Can Do Right Now

### 1. Explore the Frontend (Working!)

Open **http://localhost:5173** in your browser

You'll see:
- Login page
- Registration page
- User dashboard (after login)
- Booking form
- Admin panel

Note: API calls will fail until backend is running, but you can see the complete UI!

### 2. Review the Code

**Frontend Code:**
- `frontend/src/pages/` - All page components
- `frontend/src/components/` - Reusable components
- `frontend/src/services/api.js` - API integration

**Backend Code:**
- `backend/src/main/java/com/trucksystem/` - All Java code
- `backend/src/main/resources/` - Configuration and SQL

### 3. Read Documentation

- `README.md` - Project overview
- `QUICK_START.md` - 5-minute setup
- `API_DOCUMENTATION.md` - API endpoints
- `TROUBLESHOOTING.md` - Common issues

---

## Current Status

| Component | Status | URL |
|-----------|--------|-----|
| Frontend | ✅ RUNNING | http://localhost:5173 |
| Backend | ❌ Needs Java 17 | http://localhost:8080 (when running) |
| Database | ⚠️ Not checked | localhost:3306 |

---

## Next Steps

1. **Install Java 17** (see instructions above)
2. **Start Backend** with Maven
3. **Setup MySQL** database
4. **Test the application**

---

## Need Help?

Check these files:
- `TROUBLESHOOTING.md` - Solutions to common problems
- `SETUP_GUIDE.md` - Detailed setup instructions
- `RUN_BACKEND.md` - Backend-specific instructions

---

## Quick Commands Reference

### Frontend (Already Running!)
```powershell
cd frontend
npm install        # Already done
npm run dev        # Already running!
```

### Backend (After installing Java 17)
```powershell
cd backend
..\apache-maven-3.9.5\bin\mvn.cmd spring-boot:run
```

### Database Setup
```sql
CREATE DATABASE truck_management_db;
```

---

**🎉 Your frontend is live! Visit http://localhost:5173 now!**
