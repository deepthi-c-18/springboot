# =====================================================
# Dockerfile - Student Management System
# Spring Boot 3 with Java 21
# =====================================================

# Stage 1: Build Stage - Build the application using Maven
FROM eclipse-temurin:21-jdk AS builder

# Set working directory
WORKDIR /build

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Install Maven (if not available in base image)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Build the application
RUN mvn clean package -DskipTests

# =====================================================
# Stage 2: Runtime Stage - Run the application
# =====================================================
FROM eclipse-temurin:21-jdk-jammy

# Metadata
LABEL maintainer="Student Management System"
LABEL description="Student Management System - Spring Boot 3 Application with Java 21"
LABEL version="1.0.0"

# Set environment variables
ENV APP_HOME=/app \
    JAVA_OPTS="-Xms256m -Xmx512m" \
    TZ=UTC \
    LANG=C.UTF-8

# Create application user for security
RUN useradd -m -u 1000 appuser

# Set working directory
WORKDIR $APP_HOME

# Copy the built application from builder stage
COPY --from=builder /build/target/student-management-system-*.jar app.jar

# Create directories for logs
RUN mkdir -p /app/logs && chown -R appuser:appuser /app

# Switch to non-root user for security
USER appuser

# Expose port 8080 for Spring Boot application
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -cp app.jar com.studentapp.StudentManagementApplication || exit 1

# Run the application
ENTRYPOINT ["java"]
CMD ["-server", \
     "-Xms256m", \
     "-Xmx512m", \
     "-XX:+UseG1GC", \
     "-XX:MaxGCPauseMillis=200", \
     "-XX:+ParallelRefProcEnabled", \
     "-XX:+UnlockDiagnosticVMOptions", \
     "-XX:G1SummarizeRSetStatsPeriod=1", \
     "-Dspring.profiles.active=prod", \
     "-jar", \
     "app.jar"]
