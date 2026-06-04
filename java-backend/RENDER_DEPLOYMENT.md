# Render Deployment Guide

## Single Container Setup (All Microservices in One)

This guide explains how to deploy all microservices in a single Render container.

### Architecture

```
┌─────────────────────────────────────────────────┐
│            Render Container                      │
├─────────────────────────────────────────────────┤
│  Port 8761: Eureka Registry                     │
│  Port 8079: Auth Service                        │
│  Port 8080: Book Service                        │
│  Port 8081: Wishlist Service                    │
│  Port 8083: User Service                        │
│  Port 8084: Post Service                        │
│  Port $PORT: API Gateway (Exposed externally)   │
├─────────────────────────────────────────────────┤
│  MySQL (External Database)                      │
└─────────────────────────────────────────────────┘
```

### How It Works

1. **Single Render Service** runs `start.sh` which launches all 7 microservices
2. **Services communicate internally** via `localhost` (same container)
3. **Eureka Registry** (port 8761) internally coordinates all services
4. **API Gateway** exposes on Render's assigned `$PORT` (default 8050)
5. **External access** routes through the Gateway

### Deployment Steps

#### Step 1: Set Up Database

Create a MySQL database on Render (or use external):
- Host: `your-db-host.render.com`
- Database: `project`
- Username: `root`
- Password: `root123`

#### Step 2: Push to GitHub

```bash
git add .
git commit -m "Update for single-container Render deployment"
git push origin main
```

#### Step 3: Create Render Service

1. Go to **Render Dashboard** → **New** → **Web Service**
2. **Connect** your GitHub repository
3. **Configure:**
   - **Name:** `book-backend`
   - **Runtime:** `Docker`
   - **Build Command:** (leave blank - uses Dockerfile)
   - **Start Command:** `/app/start.sh`

#### Step 4: Environment Variables

Add in Render dashboard:

```
SPRING_DATASOURCE_URL=jdbc:mysql://YOUR_DB_HOST:3306/project
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root123
EUREKA_URL=http://localhost:8761/eureka
CLOUD_AWS_CREDENTIALS_ACCESSKEY=your_key
CLOUD_AWS_CREDENTIALS_SECRETKEY=your_secret
SPRING_MAIL_USERNAME=your_mailtrap_user
SPRING_MAIL_PASSWORD=your_mailtrap_pass
```

#### Step 5: Deploy

Click **Deploy** and wait ~5 minutes for build

### Verification

**Check Render logs:**
```
All services started. Waiting for termination...
```

**Test services:**
- Eureka: `https://your-service.onrender.com:8761/` (internal only)
- Gateway: `https://your-service.onrender.com/`
- Health: `https://your-service.onrender.com/actuator/health`

### Access Services

From external:
```bash
# Via Gateway
https://your-service.onrender.com/book/api/books
https://your-service.onrender.com/user/api/users
https://your-service.onrender.com/wishlist/api/wishlist
```

Internally (services to services):
```
http://localhost:8761/eureka
http://localhost:8079 (auth)
http://localhost:8080 (book)
http://localhost:8083 (user)
http://localhost:8084 (post)
http://localhost:8081 (wishlist)
```

### Troubleshooting

**Services not starting:**
```
docker logs book-backend  # In Render UI
```

**Database connection failed:**
- Verify `SPRING_DATASOURCE_URL` is correct
- Check database credentials
- Ensure database is accessible from Render

**Eureka not showing services:**
- Check logs for registration errors
- Verify `EUREKA_URL=http://localhost:8761/eureka`
- Wait 30 seconds for services to register

**Port conflicts:**
- Each service uses a different port (8079, 8080, etc.)
- Gateway uses Render's `$PORT` environment variable
- All internal - no external port conflicts

### Production Considerations

For production, consider:
1. **Separate services** - Deploy each as individual Render service
2. **Database** - Use managed database (Render PostgreSQL/MySQL)
3. **CORS** - Update frontend to use `https://your-service.onrender.com`
4. **Monitoring** - Add logging and error tracking
5. **Auto-scaling** - Consider separate services for better scaling

### Local Development

Still works with Docker Compose:
```bash
docker-compose down
docker-compose build
docker-compose up -d
```

Uses:
- `EUREKA_URL=http://eureka:8761/eureka` (via docker-compose)
- `SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/project` (local mysql service)
