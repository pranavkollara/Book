#!/bin/bash

echo "Starting Book Backend Microservices..."

GATEWAY_PORT=${PORT:-8050}

# Limit JVM heap to prevent OOM (512MB total / 7 services)
JVM_OPTS="-Xmx96m -Xms32m"

# Start Eureka
java $JVM_OPTS -Dserver.port=8761 -jar /app/eureka.jar > /tmp/eureka.log 2>&1 &
echo "Started Eureka on 8761"
sleep 5

# Start all services in background
java $JVM_OPTS -Dserver.port=8079 -jar /app/auth.jar > /tmp/auth.log 2>&1 &
java $JVM_OPTS -Dserver.port=8080 -jar /app/book.jar > /tmp/book.log 2>&1 &
java $JVM_OPTS -Dserver.port=8081 -jar /app/wishlist.jar > /tmp/wishlist.log 2>&1 &
java $JVM_OPTS -Dserver.port=8083 -jar /app/user.jar > /tmp/user.log 2>&1 &
java $JVM_OPTS -Dserver.port=8084 -jar /app/post.jar > /tmp/post.log 2>&1 &

echo "Started all services"

# Start Gateway in foreground (allow larger heap)
java -Xmx128m -Xms64m -Dserver.port=$GATEWAY_PORT -jar /app/gateway.jar

