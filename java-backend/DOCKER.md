# Docker Setup for Book Management Microservices

This directory contains Docker configurations to run all your Java backend microservices in containers.

## Architecture

The setup includes 7 microservices and 1 MySQL database:

| Service | Port | Description |
|---------|------|-------------|
| **Eureka** | 8761 | Service Registry (Eureka Server) |
| **Gateway** | 8050 | API Gateway |
| **Auth** | 8079 | Authentication Service |
| **Book** | 8080 | Book Management Service |
| **Wishlist** | 8081 | Wishlist Service |
| **User** | 8083 | User Management Service |
| **Post** | 8084 | Post Service |
| **MySQL** | 3306 | Database Server |

## Prerequisites

- Docker (version 20.10+)
- Docker Compose (version 1.29+)
- At least 4GB of available RAM for containers
- All services must be built (JAR files generated)

## Quick Start

### 1. Build and Start All Services

```bash
# Navigate to the java-backend directory
cd /path/to/java-backend

# Build images and start all containers
docker-compose up -d

# View logs
docker-compose logs -f
```

### 2. Stop All Services

```bash
docker-compose down
```

### 3. Remove All Containers and Volumes (Including Database)

```bash
docker-compose down -v
```

## Services Startup Order

The `docker-compose.yml` is configured with proper dependency management:

1. **MySQL** starts first (with health check)
2. **Eureka** starts after MySQL (with health check)
3. **Microservices** (Auth, Book, Wishlist, User, Post) start after Eureka
4. **Gateway** starts after Eureka

Health checks ensure services are ready before dependent services start.

## Database

- **Container Name**: book-mysql
- **Username**: root
- **Password**: root123
- **Database**: project
- **Data Persistence**: Stored in Docker named volume `mysql_data`

### Access MySQL

```bash
docker exec -it book-mysql mysql -uroot -proot123 project
```

## Accessing Services

### Via Direct Ports

- **Book Service**: http://localhost:8080
- **Wishlist Service**: http://localhost:8081
- **User Service**: http://localhost:8083
- **Auth Service**: http://localhost:8079
- **Post Service**: http://localhost:8084
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8050

### Via API Gateway

```bash
# Example: Access Book service through Gateway
curl http://localhost:8050/book/v3/api-docs

# Available routes (based on gateway config)
curl http://localhost:8050/book/...
curl http://localhost:8050/wishlist/...
curl http://localhost:8050/auth/...
curl http://localhost:8050/post/...
curl http://localhost:8050/user/...
```

## Environment Variables

The `docker-compose.yml` automatically sets these environment variables for each service:

- `SPRING_DATASOURCE_URL`: Points to the MySQL container (mysql:3306)
- `SPRING_DATASOURCE_USERNAME`: root
- `SPRING_DATASOURCE_PASSWORD`: root123
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE`: Points to Eureka container (eureka:8761)

These override the localhost values in `application.properties`.

## Useful Docker Commands

### View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth
docker-compose logs -f book
docker-compose logs -f eureka

# Last 100 lines
docker-compose logs --tail=100 -f auth
```

### Check Service Status

```bash
# List all running containers
docker-compose ps

# Check Eureka registered services
curl http://localhost:8761/eureka/apps
```

### Access Container Shell

```bash
# Access Eureka container
docker exec -it eureka-server /bin/bash

# Access MySQL container
docker exec -it book-mysql /bin/bash
```

### Rebuild Images (After Code Changes)

```bash
# Rebuild a single service
docker-compose build auth

# Rebuild all services
docker-compose build --no-cache

# Then restart
docker-compose up -d
```

## Troubleshooting

### Services Can't Connect to Database

- Ensure MySQL has started and passed health checks: `docker-compose ps`
- Check MySQL logs: `docker-compose logs mysql`
- Verify database exists: `docker exec -it book-mysql mysql -uroot -proot123 -e "SHOW DATABASES;"`

### Eureka Not Registering Services

- Wait 30-60 seconds for services to register
- Check Eureka logs: `docker-compose logs -f eureka`
- Visit Eureka dashboard: http://localhost:8761

### Port Conflicts

If ports are already in use, modify the port mappings in `docker-compose.yml`:

```yaml
ports:
  - "9080:8080"  # Change 9080 to desired external port
```

### Services Keep Restarting

- Check logs: `docker-compose logs [service-name]`
- Ensure all dependencies are healthy
- Verify database connectivity
- Check available memory: `docker stats`

## Production Considerations

For production deployments, consider:

1. **Environment-Specific Configuration**
   - Use `.env` file for secrets
   - Create separate `docker-compose.prod.yml` for production
   - Use Docker secrets for sensitive data

2. **Resource Limits**
   - Add memory/CPU limits in docker-compose.yml:
     ```yaml
     deploy:
       resources:
         limits:
           cpus: '1'
           memory: 1G
     ```

3. **Persistent Storage**
   - MySQL data is already persisted in named volume
   - Consider backing up `mysql_data` volume regularly

4. **Logging**
   - Configure centralized logging (ELK Stack, Splunk, etc.)
   - Set log levels appropriately

5. **Security**
   - Use strong database credentials
   - Don't commit `.env` files with secrets
   - Use private Docker registries
   - Implement proper network policies

## Dockerfile Details

The `Dockerfile` uses a multi-stage build:

1. **Builder Stage**: Uses Maven to build all JAR files
2. **Runtime Stage**: Uses lightweight JRE image with all JAR files

This approach:
- Reduces final image size
- Keeps build dependencies separate from runtime
- Allows running any service from the same image

## Network

All services run on a custom bridge network `microservices-network`, allowing them to communicate using service names (e.g., `mysql`, `eureka`).

## Volume Management

- **mysql_data**: Stores MySQL database files for data persistence

To backup the database:

```bash
docker run --rm -v mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql_backup.tar.gz /data
```

To restore:

```bash
docker run --rm -v mysql_data:/data -v $(pwd):/backup alpine tar xzf /backup/mysql_backup.tar.gz -C /
```
