# Docker Setup Summary

This document summarizes the Docker files created for the Java backend microservices.

## Files Created

### 1. **Dockerfile**
**Location:** `java-backend/Dockerfile`

Multi-stage Docker build that:
- Uses Maven 3.9.0 with Eclipse Temurin JDK 17 to compile all services
- Builds all 7 microservices (Auth, Book, Eureka, Gateway, Post, User, Wishlist)
- Creates a runtime image based on Eclipse Temurin JRE 17
- Includes all JAR files ready to run

**Key Features:**
- Multi-stage build keeps image size optimized
- All services available in one image
- Easy to extend for additional services

### 2. **docker-compose.yml**
**Location:** `java-backend/docker-compose.yml`

Orchestrates 8 services:
- **MySQL Database** - Database server
- **Eureka** - Service registry
- **7 Microservices** - Auth, Book, Wishlist, User, Post, Gateway

**Features:**
- Automatic startup ordering with health checks
- Environment variable injection for database connections
- Custom Docker network for inter-service communication
- MySQL persistent volume for data persistence
- Proper dependency management

### 3. **.dockerignore**
**Location:** `java-backend/.dockerignore`

Excludes unnecessary files from Docker build context:
- Build artifacts and logs
- IDE configuration
- Git files
- Development dependencies
- Speeds up build process

### 4. **docker-manage.sh**
**Location:** `java-backend/docker-manage.sh`

Bash script for managing services on Linux/macOS:

```bash
./docker-manage.sh up         # Start services
./docker-manage.sh down       # Stop services
./docker-manage.sh logs auth  # View service logs
./docker-manage.sh health     # Check health
./docker-manage.sh shell book # Access container
```

### 5. **docker-manage.bat**
**Location:** `java-backend/docker-manage.bat`

Batch script for managing services on Windows:

```cmd
docker-manage.bat up
docker-manage.bat down
docker-manage.bat logs auth
docker-manage.bat health
docker-manage.bat shell book
```

### 6. **DOCKER.md**
**Location:** `java-backend/DOCKER.md`

Comprehensive documentation including:
- Architecture overview
- Quick start guide
- Detailed service information and ports
- Database access instructions
- Troubleshooting guide
- Production considerations
- Security recommendations

### 7. **.env.example**
**Location:** `java-backend/.env.example`

Example environment variables file:
- Database credentials
- Service ports
- Eureka configuration
- AWS S3 settings
- Java memory options

## Quick Start Guide

### Step 1: Build and Start (Linux/macOS)
```bash
cd java-backend
chmod +x docker-manage.sh
./docker-manage.sh up
```

### Step 2: Build and Start (Windows)
```cmd
cd java-backend
docker-manage.bat up
```

### Step 3: Alternative - Direct Docker Compose
```bash
cd java-backend
docker-compose up -d
```

### Step 4: Verify Services
- **Eureka Dashboard:** http://localhost:8761
- **API Gateway:** http://localhost:8050
- **MySQL:** localhost:3306 (user: root, password: root123)

### Step 5: Access Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth
docker-compose logs -f book
```

### Step 6: Stop Services
```bash
# Using management script
./docker-manage.sh down    # Linux/macOS
docker-manage.bat down     # Windows

# Or direct compose
docker-compose down
```

## Service Details

| Service | Port | Purpose | Database |
|---------|------|---------|----------|
| Eureka | 8761 | Service Registry | None |
| Gateway | 8050 | API Gateway | None |
| Auth | 8079 | Authentication | MySQL |
| Book | 8080 | Book Management | MySQL |
| Wishlist | 8081 | Wishlist Management | MySQL |
| User | 8083 | User Management | MySQL |
| Post | 8084 | Post Management | MySQL |
| MySQL | 3306 | Database | N/A |

## Common Tasks

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f book
docker-compose logs -f auth
docker-compose logs -f eureka
```

### Access MySQL
```bash
# Using management script
./docker-manage.sh mysql

# Or direct docker
docker exec -it book-mysql mysql -uroot -proot123 project
```

### Access Container Shell
```bash
# Using management script
./docker-manage.sh shell book

# Or direct docker
docker exec -it book-service /bin/bash
```

### Rebuild After Code Changes
```bash
# Using management script
./docker-manage.sh rebuild

# Or direct compose
docker-compose build --no-cache
docker-compose up -d
```

### Check Service Health
```bash
# Using management script
./docker-manage.sh health

# Or check manually
docker-compose ps
```

## Environment Variables

The compose file automatically sets:
- `SPRING_DATASOURCE_URL` - MySQL connection string
- `SPRING_DATASOURCE_USERNAME` - Database user
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` - Eureka server URL

These override the localhost values in `application.properties`.

## Network

All services run on a custom bridge network called `microservices-network`. Services communicate using:
- Service names (e.g., `mysql`, `eureka`) instead of localhost
- Internal Docker DNS resolves service names to IPs

## Volume Management

### MySQL Data Persistence
Database data is stored in a Docker named volume: `mysql_data`

```bash
# List volumes
docker volume ls

# Inspect volume
docker volume inspect java-backend_mysql_data

# Backup database
docker run --rm -v mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql_backup.tar.gz /data
```

## Build Process

The Dockerfile follows this process:

1. **Builder Stage:**
   - Uses Maven 3.9.0 with JDK 17
   - Copies all service directories
   - Runs `mvn clean package` for each service
   - Generates JAR files

2. **Runtime Stage:**
   - Uses lightweight JRE 17
   - Copies all compiled JAR files
   - Exposes all necessary ports
   - Sets up startup command

## Customization

### Change Database Credentials
Edit `docker-compose.yml` and `.env`:
```yaml
environment:
  - SPRING_DATASOURCE_USERNAME=newuser
  - SPRING_DATASOURCE_PASSWORD=newpassword
```

### Change Service Ports
Edit `docker-compose.yml`:
```yaml
ports:
  - "9080:8080"  # Change external port
```

### Add New Service
1. Add new service section in `docker-compose.yml`
2. Update `Dockerfile` to build the new service
3. Add port exposure to `Dockerfile`
4. Update documentation

## Troubleshooting

### Services Won't Start
```bash
# Check logs
docker-compose logs

# Check specific service
docker-compose logs auth

# Check resource availability
docker stats
```

### Database Connection Issues
```bash
# Verify MySQL is running
docker-compose ps

# Test MySQL connection
docker exec -it book-mysql mysql -uroot -proot123 -e "SELECT 1;"

# Check network connectivity
docker exec book-service ping mysql
```

### Port Already in Use
```bash
# Find what's using the port
lsof -i :8080  # macOS/Linux
netstat -ano | findstr :8080  # Windows

# Change port in docker-compose.yml
# Or stop the conflicting service
```

## Performance Optimization

### Memory Settings
The Dockerfile can be updated to configure JVM memory:
```dockerfile
ENV JAVA_OPTS="-Xmx1g -Xms512m"
```

### Database Optimization
Consider adding:
- Connection pooling configuration
- Query optimization
- Proper indexing

### Resource Limits
Add to docker-compose.yml:
```yaml
deploy:
  resources:
    limits:
      cpus: '2'
      memory: 2G
```

## Security Considerations

1. **Credentials** - Don't commit real credentials, use `.env` files
2. **Network** - Services are isolated in a custom network
3. **Database** - Default credentials should be changed in production
4. **Images** - Consider using private registries for production
5. **Logging** - Avoid logging sensitive information

## References

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot in Docker](https://spring.io/guides/gs/spring-boot-docker/)
- [Spring Cloud Eureka](https://spring.io/projects/spring-cloud-netflix)
- [MySQL Docker Image](https://hub.docker.com/_/mysql)
