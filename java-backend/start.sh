#!/bin/bash

echo "Starting Book Backend Microservices..."

# Use PORT from environment or default
GATEWAY_PORT=${PORT:-8050}

echo "Gateway will run on port: $GATEWAY_PORT"

# JVM optimization flags for faster startup
JVM_OPTS="-Xmx256m -Xms128m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:CICompilerCount=2"

echo "Starting Eureka Registry on port 8761..."
nohup java $JVM_OPTS -Dserver.port=8761 \
  -Deureka.client.register-with-eureka=false \
  -Deureka.client.fetch-registry=false \
  -jar /app/eureka.jar > /tmp/eureka.log 2>&1 &

# Small delay for Eureka to start accepting connections
sleep 3

echo "Starting supporting services..."
nohup java $JVM_OPTS -Dserver.port=8079 -jar /app/auth.jar > /tmp/auth.log 2>&1 &
nohup java $JVM_OPTS -Dserver.port=8080 -jar /app/book.jar > /tmp/book.log 2>&1 &
nohup java $JVM_OPTS -Dserver.port=8081 -jar /app/wishlist.jar > /tmp/wishlist.log 2>&1 &
nohup java $JVM_OPTS -Dserver.port=8083 -jar /app/user.jar > /tmp/user.log 2>&1 &
nohup java $JVM_OPTS -Dserver.port=8084 -jar /app/post.jar > /tmp/post.log 2>&1 &

echo "Starting API Gateway on port $GATEWAY_PORT..."

# Function to cleanup on exit
cleanup() {
    echo "Shutting down services..."
    pkill -f "java.*-jar.*\.jar" 2>/dev/null || true
}
trap cleanup EXIT

# Start Gateway in foreground with JVM optimization
exec java $JVM_OPTS -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar

