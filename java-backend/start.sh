#!/bin/bash
set -e

echo "Starting Book Backend Microservices..."

# Use PORT from environment or default
GATEWAY_PORT=${PORT:-8050}

echo "Starting Eureka Registry on port 8761..."
java -Dserver.port=8761 -jar /app/eureka.jar &
EUREKA_PID=$!
sleep 10

echo "Starting Auth Service on port 8079..."
java -Dserver.port=8079 -jar /app/auth.jar &
AUTH_PID=$!

echo "Starting Book Service on port 8080..."
java -Dserver.port=8080 -jar /app/book.jar &
BOOK_PID=$!

echo "Starting Wishlist Service on port 8081..."
java -Dserver.port=8081 -jar /app/wishlist.jar &
WISHLIST_PID=$!

echo "Starting User Service on port 8083..."
java -Dserver.port=8083 -jar /app/user.jar &
USER_PID=$!

echo "Starting Post Service on port 8084..."
java -Dserver.port=8084 -jar /app/post.jar &
POST_PID=$!

echo "Starting API Gateway on port $GATEWAY_PORT..."
java -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar &
GATEWAY_PID=$!

echo "All services started. Waiting for termination..."

# Trap to ensure cleanup on exit
cleanup() {
    echo "Shutting down services..."
    kill $EUREKA_PID $AUTH_PID $BOOK_PID $WISHLIST_PID $USER_PID $POST_PID $GATEWAY_PID 2>/dev/null || true
}
trap cleanup EXIT

# Wait for any process to exit (will trigger cleanup)
wait

