# 📤 How to Upload This Project to GitHub

## Step 1: Create a GitHub Repository

1. Go to [GitHub](https://github.com)
2. Click the **"+"** icon in the top right
3. Select **"New repository"**
4. Fill in:
   - Repository name: `truck-management-system`
   - Description: `Smart Truck Transport Management System - Full Stack Application`
   - Choose **Public** or **Private**
   - **DO NOT** check "Initialize with README" (we already have one)
5. Click **"Create repository"**

---

## Step 2: Initialize Git in Your Project

Open Command Prompt in your project folder and run:

```cmd
cd C:\Users\sabar\OneDrive\ドキュメント\trans-truck
git init
```

---

## Step 3: Add Files to Git

```cmd
git add .
```

This adds all files except those in `.gitignore`

---

## Step 4: Commit Your Files

```cmd
git commit -m "Initial commit: Full stack truck management system"
```

---

## Step 5: Rename Main Branch (if needed)

```cmd
git branch -M main
```

---

## Step 6: Connect to GitHub Repository

Replace `YOUR_USERNAME` with your GitHub username:

```cmd
git remote add origin https://github.com/YOUR_USERNAME/truck-management-system.git
```

Example:
```cmd
git remote add origin https://github.com/sabar/truck-management-system.git
```

---

## Step 7: Push to GitHub

```cmd
git push -u origin main
```

If prompted for credentials:
- Username: Your GitHub username
- Password: Use a **Personal Access Token** (not your password)

### How to Create Personal Access Token:
1. Go to GitHub → Settings → Developer settings
2. Click "Personal access tokens" → "Tokens (classic)"
3. Click "Generate new token"
4. Select scopes: `repo` (full control)
5. Copy the token and use it as password

---

## Step 8: Verify Upload

1. Go to your GitHub repository URL
2. You should see all your files uploaded
3. The README.md will be displayed on the main page

---

## 🎉 Done! Your Project is Now on GitHub

Your repository URL will be:
```
https://github.com/YOUR_USERNAME/truck-management-system
```

---

## 📝 Quick Commands Summary

```cmd
# Navigate to project
cd C:\Users\sabar\OneDrive\ドキュメント\trans-truck

# Initialize Git
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit: Full stack truck management system"

# Rename branch
git branch -M main

# Add remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/truck-management-system.git

# Push to GitHub
git push -u origin main
```

---

## 🔄 Future Updates

After making changes to your code:

```cmd
# Add changed files
git add .

# Commit with message
git commit -m "Description of changes"

# Push to GitHub
git push
```

---

## ⚠️ Important Notes

1. **Never commit sensitive data**:
   - Database passwords are in `.gitignore`
   - Update `application.properties` after cloning

2. **Files excluded** (in `.gitignore`):
   - `node_modules/`
   - `target/`
   - Maven downloads
   - Database credentials

3. **Before sharing**:
   - Remove any personal information
   - Update MySQL password in README to placeholder
   - Test cloning and running on another machine

---

## 🆘 Troubleshooting

### Error: "fatal: not a git repository"
```cmd
git init
```

### Error: "remote origin already exists"
```cmd
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/truck-management-system.git
```

### Error: "failed to push"
```cmd
git pull origin main --allow-unrelated-histories
git push -u origin main
```

---

## 📱 Clone Your Repository Later

To download your project on another computer:

```cmd
git clone https://github.com/YOUR_USERNAME/truck-management-system.git
cd truck-management-system
```

Then follow setup instructions in README.md

---

Need help? Check [GitHub Docs](https://docs.github.com/en/get-started)
