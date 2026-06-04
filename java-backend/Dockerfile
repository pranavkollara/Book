# Multi-stage build for all microservices
# Stage 1: Build stage using Maven
FROM maven:3.9.0-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy all service directories
COPY Auth ./Auth
COPY Book ./Book
COPY Eureka ./Eureka
COPY Gateway ./Gateway
COPY Post ./Post
COPY User ./User
COPY Wishlist ./Wishlist

# Build all services
RUN cd Auth && mvn clean package -DskipTests
RUN cd Book && mvn clean package -DskipTests
RUN cd Eureka && mvn clean package -DskipTests
RUN cd Gateway && mvn clean package -DskipTests
RUN cd Post && mvn clean package -DskipTests
RUN cd User && mvn clean package -DskipTests
RUN cd Wishlist && mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy all built JARs
COPY --from=builder /app/Auth/target/*.jar /app/auth.jar
COPY --from=builder /app/Book/target/*.jar /app/book.jar
COPY --from=builder /app/Eureka/target/*.jar /app/eureka.jar
COPY --from=builder /app/Gateway/target/*.jar /app/gateway.jar
COPY --from=builder /app/Post/target/*.jar /app/post.jar
COPY --from=builder /app/User/target/*.jar /app/user.jar
COPY --from=builder /app/Wishlist/target/*.jar /app/wishlist.jar

# Copy startup script
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# Set Java options for container memory awareness
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Expose all service ports
EXPOSE 8050 8079 8080 8081 8083 8084 8761

# Run startup script that starts all services
CMD ["/app/start.sh"]
