# Production Deployment Guide

## Pre-Deployment Checklist

### Security
- [ ] Change all default passwords
- [ ] Update JWT secret key
- [ ] Configure HTTPS/SSL
- [ ] Set up firewall rules
- [ ] Enable CORS only for production domains
- [ ] Remove test/sample data
- [ ] Disable debug logging
- [ ] Set up rate limiting
- [ ] Configure security headers
- [ ] Enable SQL injection protection

### Configuration
- [ ] Update database credentials
- [ ] Configure email service
- [ ] Set up Google Maps API key
- [ ] Configure environment variables
- [ ] Update API base URLs
- [ ] Set up backup strategy
- [ ] Configure monitoring
- [ ] Set up logging
- [ ] Configure error tracking

### Testing
- [ ] Run all backend tests
- [ ] Test all API endpoints
- [ ] Test frontend build
- [ ] Perform security audit
- [ ] Load testing
- [ ] Cross-browser testing
- [ ] Mobile responsiveness testing

## Backend Deployment

### Option 1: Traditional Server (Linux)

#### 1. Prepare the Server

```bash
# Update system
sudo apt update && sudo apt upgrade -y

# Install Java 17
sudo apt install openjdk-17-jdk -y

# Verify installation
java -version

# Install MySQL
sudo apt install mysql-server -y

# Secure MySQL
sudo mysql_secure_installation
```

#### 2. Setup Database

```bash
# Login to MySQL
sudo mysql -u root -p

# Create database and user
CREATE DATABASE truck_management_db;
CREATE USER 'truckuser'@'localhost' IDENTIFIED BY 'STRONG_PASSWORD_HERE';
GRANT ALL PRIVILEGES ON truck_management_db.* TO 'truckuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Import schema
mysql -u truckuser -p truck_management_db < schema.sql
```

#### 3. Configure Application

Create `application-prod.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/truck_management_db?useSSL=true&serverTimezone=UTC
spring.datasource.username=truckuser
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}

# Google Maps
google.maps.api.key=${GOOGLE_MAPS_KEY}

# CORS
cors.allowed.origins=${ALLOWED_ORIGINS}

# Logging
logging.level.root=INFO
logging.file.name=/var/log/truck-management/application.log
```

#### 4. Build Application

```bash
# On your development machine
cd backend
mvn clean package -DskipTests

# Copy JAR to server
scp target/truck-management-1.0.0.jar user@server:/opt/truck-management/
```

#### 5. Create Systemd Service

Create `/etc/systemd/system/truck-management.service`:

```ini
[Unit]
Description=Truck Management System
After=syslog.target network.target

[Service]
User=truckapp
Type=simple
WorkingDirectory=/opt/truck-management
ExecStart=/usr/bin/java -jar /opt/truck-management/truck-management-1.0.0.jar --spring.profiles.active=prod
Restart=always
RestartSec=10

Environment="DB_PASSWORD=your_db_password"
Environment="JWT_SECRET=your_jwt_secret_key_min_256_bits"
Environment="MAIL_USERNAME=your_email@gmail.com"
Environment="MAIL_PASSWORD=your_app_password"
Environment="GOOGLE_MAPS_KEY=your_google_maps_key"
Environment="ALLOWED_ORIGINS=https://yourdomain.com"

[Install]
WantedBy=multi-user.target
```

#### 6. Start Service

```bash
# Create user
sudo useradd -r -s /bin/false truckapp

# Set permissions
sudo chown -R truckapp:truckapp /opt/truck-management

# Reload systemd
sudo systemctl daemon-reload

# Enable and start service
sudo systemctl enable truck-management
sudo systemctl start truck-management

# Check status
sudo systemctl status truck-management

# View logs
sudo journalctl -u truck-management -f
```

#### 7. Setup Nginx Reverse Proxy

Install Nginx:
```bash
sudo apt install nginx -y
```

Create `/etc/nginx/sites-available/truck-management`:

```nginx
server {
    listen 80;
    server_name api.yourdomain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Enable site:
```bash
sudo ln -s /etc/nginx/sites-available/truck-management /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

#### 8. Setup SSL with Let's Encrypt

```bash
# Install Certbot
sudo apt install certbot python3-certbot-nginx -y

# Obtain certificate
sudo certbot --nginx -d api.yourdomain.com

# Auto-renewal is configured automatically
```

### Option 2: Docker Deployment

#### 1. Create Dockerfile

`backend/Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/truck-management-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
```

#### 2. Create docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: truck_management_db
      MYSQL_USER: truckuser
      MYSQL_PASSWORD: ${DB_PASSWORD}
    volumes:
      - mysql-data:/var/lib/mysql
      - ./backend/src/main/resources/schema.sql:/docker-entrypoint-initdb.d/schema.sql
    ports:
      - "3306:3306"
    networks:
      - truck-network

  backend:
    build: ./backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/truck_management_db
      SPRING_DATASOURCE_USERNAME: truckuser
      SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
      MAIL_USERNAME: ${MAIL_USERNAME}
      MAIL_PASSWORD: ${MAIL_PASSWORD}
      GOOGLE_MAPS_KEY: ${GOOGLE_MAPS_KEY}
      CORS_ALLOWED_ORIGINS: ${ALLOWED_ORIGINS}
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    networks:
      - truck-network
    restart: unless-stopped

volumes:
  mysql-data:

networks:
  truck-network:
    driver: bridge
```

#### 3. Deploy with Docker

```bash
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop
docker-compose down

# Rebuild
docker-compose up -d --build
```

### Option 3: Cloud Deployment (AWS)

#### AWS Elastic Beanstalk

1. Install AWS CLI and EB CLI
2. Initialize EB application:
```bash
eb init -p java-17 truck-management
```

3. Create environment:
```bash
eb create truck-management-prod
```

4. Deploy:
```bash
mvn clean package
eb deploy
```

#### AWS EC2

Follow traditional server deployment steps on EC2 instance.

## Frontend Deployment

### Option 1: Static Hosting (Nginx)

#### 1. Build Frontend

```bash
cd frontend

# Update API URL in src/services/api.js
const API_BASE_URL = 'https://api.yourdomain.com/api';

# Build
npm run build
```

#### 2. Configure Nginx

Create `/etc/nginx/sites-available/truck-management-frontend`:

```nginx
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;
    root /var/www/truck-management/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # Gzip compression
    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

    # Cache static assets
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
```

#### 3. Deploy Files

```bash
# Copy build files to server
scp -r dist/* user@server:/var/www/truck-management/dist/

# Set permissions
sudo chown -R www-data:www-data /var/www/truck-management

# Enable site
sudo ln -s /etc/nginx/sites-available/truck-management-frontend /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx
```

#### 4. Setup SSL

```bash
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com
```

### Option 2: Vercel Deployment

```bash
# Install Vercel CLI
npm i -g vercel

# Login
vercel login

# Deploy
cd frontend
vercel --prod
```

### Option 3: Netlify Deployment

```bash
# Install Netlify CLI
npm install -g netlify-cli

# Login
netlify login

# Deploy
cd frontend
npm run build
netlify deploy --prod --dir=dist
```

### Option 4: AWS S3 + CloudFront

```bash
# Build
npm run build

# Upload to S3
aws s3 sync dist/ s3://your-bucket-name --delete

# Invalidate CloudFront cache
aws cloudfront create-invalidation --distribution-id YOUR_DIST_ID --paths "/*"
```

## Database Backup Strategy

### Automated Backups

Create `/opt/scripts/backup-db.sh`:

```bash
#!/bin/bash
BACKUP_DIR="/var/backups/mysql"
DATE=$(date +%Y%m%d_%H%M%S)
DB_NAME="truck_management_db"
DB_USER="truckuser"
DB_PASS="your_password"

mkdir -p $BACKUP_DIR

mysqldump -u $DB_USER -p$DB_PASS $DB_NAME | gzip > $BACKUP_DIR/backup_$DATE.sql.gz

# Keep only last 30 days
find $BACKUP_DIR -name "backup_*.sql.gz" -mtime +30 -delete

echo "Backup completed: backup_$DATE.sql.gz"
```

Add to crontab:
```bash
# Daily backup at 2 AM
0 2 * * * /opt/scripts/backup-db.sh
```

## Monitoring and Logging

### Application Monitoring

#### 1. Spring Boot Actuator

Add to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Configure in `application-prod.properties`:
```properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

#### 2. Log Aggregation

Use ELK Stack (Elasticsearch, Logstash, Kibana) or cloud services like AWS CloudWatch.

#### 3. Application Performance Monitoring

Consider tools like:
- New Relic
- Datadog
- AppDynamics
- Prometheus + Grafana

### Health Checks

```bash
# Backend health
curl https://api.yourdomain.com/actuator/health

# Frontend availability
curl -I https://yourdomain.com
```

## Performance Optimization

### Backend

1. **Database Indexing**:
```sql
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_booking_user ON bookings(user_id);
```

2. **Connection Pooling**:
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

3. **Caching**:
```properties
spring.cache.type=redis
```

### Frontend

1. **Code Splitting**: Already handled by Vite
2. **Lazy Loading**: Implement for routes
3. **CDN**: Use for static assets
4. **Compression**: Enable gzip/brotli

## Security Hardening

### Backend

1. **Rate Limiting**: Implement with Spring Security
2. **SQL Injection**: Use parameterized queries (already done with JPA)
3. **XSS Protection**: Sanitize inputs
4. **CSRF Protection**: Enable for state-changing operations

### Server

```bash
# Firewall rules
sudo ufw allow 22/tcp
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw enable

# Fail2ban
sudo apt install fail2ban -y
```

## Rollback Strategy

### Backend

```bash
# Stop service
sudo systemctl stop truck-management

# Replace JAR with previous version
sudo cp /opt/truck-management/backup/truck-management-1.0.0.jar /opt/truck-management/

# Start service
sudo systemctl start truck-management
```

### Frontend

```bash
# Restore previous build
sudo cp -r /var/www/truck-management/backup/dist/* /var/www/truck-management/dist/
```

### Database

```bash
# Restore from backup
gunzip < /var/backups/mysql/backup_YYYYMMDD_HHMMSS.sql.gz | mysql -u truckuser -p truck_management_db
```

## Post-Deployment

### Verification

1. Test all critical user flows
2. Check error logs
3. Monitor performance metrics
4. Verify email notifications
5. Test payment flow
6. Check admin functions

### Documentation

1. Update API documentation
2. Document deployment process
3. Create runbook for common issues
4. Update team on changes

## Maintenance

### Regular Tasks

- [ ] Weekly: Review logs
- [ ] Weekly: Check disk space
- [ ] Monthly: Update dependencies
- [ ] Monthly: Review security patches
- [ ] Quarterly: Performance audit
- [ ] Quarterly: Security audit

### Scaling Considerations

1. **Horizontal Scaling**: Add more application servers behind load balancer
2. **Database Replication**: Master-slave setup
3. **Caching Layer**: Redis for session management
4. **CDN**: For static assets
5. **Microservices**: Split into smaller services if needed

## Support and Troubleshooting

- Monitor logs: `/var/log/truck-management/`
- Check service status: `sudo systemctl status truck-management`
- View real-time logs: `sudo journalctl -u truck-management -f`
- Database logs: `/var/log/mysql/error.log`

For detailed troubleshooting, see [TROUBLESHOOTING.md](TROUBLESHOOTING.md)

---

**Remember**: Always test deployment process in staging environment first!
