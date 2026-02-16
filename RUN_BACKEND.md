# Backend Running Instructions

## Issue
Maven wrapper and Maven are having compatibility issues with Java 25.

## Solutions

### Option 1: Install Maven Properly (Recommended)

1. Download Maven from: https://maven.apache.org/download.cgi
2. Extract to C:\Program Files\Apache\Maven
3. Add to PATH: C:\Program Files\Apache\Maven\bin
4. Set JAVA_HOME: C:\Program Files\Java\jdk-25.0.2
5. Run: `mvn spring-boot:run` in backend folder

### Option 2: Use IntelliJ IDEA or Eclipse

1. Open the `backend` folder as a Maven project
2. Let IDE download dependencies
3. Run `TruckManagementApplication.java`

### Option 3: Download Java 17 (Most Compatible)

Java 25 is very new. Spring Boot 3.2.0 works best with Java 17.

1. Download Java 17 from: https://www.oracle.com/java/technologies/downloads/#java17
2. Install it
3. Set JAVA_HOME to Java 17 path
4. Run Maven commands

## Current Status

✅ **Frontend is RUNNING**: http://localhost:5173

❌ **Backend needs Maven** to compile and run

## Quick Fix for Now

Since frontend is running, you can see the UI at:
**http://localhost:5173**

The backend will need proper Maven setup to work.
