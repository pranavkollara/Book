#!/bin/bash

echo "Starting Book Backend Microservices..."

# Use PORT from environment or default
GATEWAY_PORT=${PORT:-8050}

echo "Gateway will run on port: $GATEWAY_PORT"

# Start all services in background with minimal delays
# Services don't need to be fully ready - they'll register with Eureka once it's up

echo "Starting Eureka Registry on port 8761..."
nohup java -Dserver.port=8761 \
  -Deureka.client.register-with-eureka=false \
  -Deureka.client.fetch-registry=false \
  -jar /app/eureka.jar > /tmp/eureka.log 2>&1 &

# Small delay for Eureka to start accepting connections
sleep 5

echo "Starting supporting services..."
nohup java -Dserver.port=8079 -jar /app/auth.jar > /tmp/auth.log 2>&1 &
nohup java -Dserver.port=8080 -jar /app/book.jar > /tmp/book.log 2>&1 &
nohup java -Dserver.port=8081 -jar /app/wishlist.jar > /tmp/wishlist.log 2>&1 &
nohup java -Dserver.port=8083 -jar /app/user.jar > /tmp/user.log 2>&1 &
nohup java -Dserver.port=8084 -jar /app/post.jar > /tmp/post.log 2>&1 &

# Small delay to let services start registering
sleep 3

echo "Starting API Gateway on port $GATEWAY_PORT..."

# Function to cleanup on exit
cleanup() {
    echo "Shutting down services..."
    pkill -f "java.*-jar.*\.jar" 2>/dev/null || true
}
trap cleanup EXIT

# Start Gateway in foreground - this is what Render monitors for port binding
# Using exec replaces the shell process with Java, so Render sees the actual process
exec java -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar

