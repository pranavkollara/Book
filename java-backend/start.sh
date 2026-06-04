#!/bin/bash
set -e

echo "Starting Book Backend Microservices..."

# Use PORT from environment or default
GATEWAY_PORT=${PORT:-8050}

echo "Gateway will run on port: $GATEWAY_PORT"
echo "=================================================="

# Start supporting services in background
echo "Starting Eureka Registry on port 8761..."
nohup java -Dserver.port=8761 \
  -Deureka.client.register-with-eureka=false \
  -Deureka.client.fetch-registry=false \
  -jar /app/eureka.jar > /tmp/eureka.log 2>&1 &
EUREKA_PID=$!
echo "Eureka PID: $EUREKA_PID"
sleep 20

echo "Starting Auth Service on port 8079..."
nohup java -Dserver.port=8079 -jar /app/auth.jar > /tmp/auth.log 2>&1 &
AUTH_PID=$!

echo "Starting Book Service on port 8080..."
nohup java -Dserver.port=8080 -jar /app/book.jar > /tmp/book.log 2>&1 &
BOOK_PID=$!

echo "Starting Wishlist Service on port 8081..."
nohup java -Dserver.port=8081 -jar /app/wishlist.jar > /tmp/wishlist.log 2>&1 &
WISHLIST_PID=$!

echo "Starting User Service on port 8083..."
nohup java -Dserver.port=8083 -jar /app/user.jar > /tmp/user.log 2>&1 &
USER_PID=$!

echo "Starting Post Service on port 8084..."
nohup java -Dserver.port=8084 -jar /app/post.jar > /tmp/post.log 2>&1 &
POST_PID=$!

echo "All background services started."
echo "Starting API Gateway on port $GATEWAY_PORT (foreground)..."
echo "=================================================="

# Trap to ensure cleanup on exit
cleanup() {
    echo "Shutting down services..."
    kill $EUREKA_PID $AUTH_PID $BOOK_PID $WISHLIST_PID $USER_PID $POST_PID 2>/dev/null || true
    sleep 2
}
trap cleanup EXIT

# Run Gateway in foreground (this is what Render monitors)
exec java -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar

