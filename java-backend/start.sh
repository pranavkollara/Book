#!/bin/bash
set -e

echo "Starting Book Backend Microservices..."

# Use PORT from environment or default
GATEWAY_PORT=${PORT:-8050}

echo "Gateway will run on port: $GATEWAY_PORT"

# Start supporting services in background (with delays for startup)
echo "Starting Eureka Registry on port 8761..."
java -Dserver.port=8761 \
  -Deureka.client.register-with-eureka=false \
  -Deureka.client.fetch-registry=false \
  -jar /app/eureka.jar > /proc/1/fd/1 2>&1 &
EUREKA_PID=$!
sleep 15

echo "Starting Auth Service on port 8079..."
java -Dserver.port=8079 -jar /app/auth.jar > /proc/1/fd/1 2>&1 &
AUTH_PID=$!

echo "Starting Book Service on port 8080..."
java -Dserver.port=8080 -jar /app/book.jar > /proc/1/fd/1 2>&1 &
BOOK_PID=$!

echo "Starting Wishlist Service on port 8081..."
java -Dserver.port=8081 -jar /app/wishlist.jar > /proc/1/fd/1 2>&1 &
WISHLIST_PID=$!

echo "Starting User Service on port 8083..."
java -Dserver.port=8083 -jar /app/user.jar > /proc/1/fd/1 2>&1 &
USER_PID=$!

echo "Starting Post Service on port 8084..."
java -Dserver.port=8084 -jar /app/post.jar > /proc/1/fd/1 2>&1 &
POST_PID=$!

echo "Starting API Gateway on port $GATEWAY_PORT..."

# Trap to ensure cleanup on exit
cleanup() {
    echo "Shutting down services..."
    kill $EUREKA_PID $AUTH_PID $BOOK_PID $WISHLIST_PID $USER_PID $POST_PID 2>/dev/null || true
}
trap cleanup EXIT

# Run Gateway in foreground (keeps container alive and lets Render detect port)
exec java -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar

