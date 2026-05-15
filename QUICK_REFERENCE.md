# Quick Reference Guide - Student Management System

## 🚀 Quick Start

### Local Development
```bash
# Clone and setup
git clone https://github.com/YOUR_USERNAME/student-management-system.git
cd student-management-system

# Build project
mvn clean package

# Run application
mvn spring-boot:run

# Access at http://localhost:8080
```

### Docker Development
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Access at http://localhost:8085
```

### Jenkins CI/CD
```bash
# Access Jenkins
http://localhost:8080

# Build and deploy
curl -X POST \
  -u jenkins-user:jenkins-token \
  "http://localhost:8080/job/student-management-system/buildWithParameters?OPERATION=deploy&DEPLOY_DB=true&DEPLOY_APP=true"
```

---

## 🛠️ Common Maven Commands

```bash
# Clean and build
mvn clean package

# Build without tests
mvn clean package -DskipTests

# Run tests
mvn test

# Run specific test
mvn test -Dtest=StudentServiceTest

# Run application
mvn spring-boot:run

# Generate javadoc
mvn javadoc:javadoc

# Check dependencies
mvn dependency:tree
```

---

## 🐳 Common Docker Commands

```bash
# Build image
docker build -t student-management-system:latest .

# Run container
docker run -d -p 8085:8080 student-management-system:latest

# List containers
docker ps -a

# View logs
docker logs -f spring-app

# Stop container
docker stop spring-app

# Remove container
docker rm spring-app

# Push to DockerHub
docker push username/student-management-system:latest

# Pull from DockerHub
docker pull username/student-management-system:latest
```

---

## 📦 Common Docker Compose Commands

```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f

# View services
docker-compose ps

# Restart service
docker-compose restart spring-app

# View volumes
docker volume ls

# Clean up
docker-compose down -v
```

---

## 📡 Common Ansible Commands

```bash
# Test connection
ansible all -i hosts.ini -m ping

# Deploy database
ansible-playbook -i hosts.ini deploy-db.yml -v

# Deploy application
ansible-playbook -i hosts.ini deploy-app.yml -v

# Update application
ansible-playbook -i hosts.ini update-app.yml -v

# Remove containers
ansible-playbook -i hosts.ini remove-app.yml remove-db.yml -v

# Run command on all hosts
ansible prod -i hosts.ini -a "docker ps"
```

---

## 🌐 Common API Commands

```bash
# Get all students
curl http://localhost:8085/students/api/all

# Get student by ID
curl http://localhost:8085/students/api/1

# Create student
curl -X POST http://localhost:8085/students/api/create \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","course":"Java"}'

# Update student
curl -X PUT http://localhost:8085/students/api/update/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Smith","email":"john@example.com","course":"Spring"}'

# Delete student
curl -X DELETE http://localhost:8085/students/api/delete/1

# Check health
curl http://localhost:8085/actuator/health
```

---

## 🔍 Debugging Commands

```bash
# View Docker logs
docker logs spring-app
docker logs mysql-db

# Exec into container
docker exec -it spring-app /bin/bash
docker exec -it mysql-db mysql -u root -p

# View container details
docker inspect spring-app

# View resource usage
docker stats

# Check port binding
lsof -i :8085
netstat -tulpn | grep 8085

# View network
docker network ls
docker network inspect student-network
```

---

## 🔧 Troubleshooting Commands

```bash
# Check Java
java -version
echo $JAVA_HOME

# Check Maven
mvn --version
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin:3.13.0

# Check Docker
docker ps
docker images
docker volume ls

# Check Jenkins
systemctl status jenkins
tail -f /var/log/jenkins/jenkins.log

# Check Ansible
ansible --version
ansible all -i hosts.ini -m setup

# Test connectivity
ping <WORKER_NODE_IP>
ssh ubuntu@<WORKER_NODE_IP>
```

---

## 💾 Database Commands

```bash
# Connect to MySQL
docker exec -it mysql-db mysql -u student -pstudent123

# List databases
SHOW DATABASES;

# Select database
USE studentdb;

# List tables
SHOW TABLES;

# View table structure
DESCRIBE students;

# View data
SELECT * FROM students;

# Delete all students
TRUNCATE TABLE students;

# Backup database
docker exec mysql-db mysqldump -u root -prootpassword studentdb > backup.sql

# Restore database
docker exec -i mysql-db mysql -u root -prootpassword studentdb < backup.sql
```

---

## 📊 Monitoring Commands

```bash
# Docker resource usage
docker stats

# System resources
top
df -h
free -h

# Jenkins console log
curl http://localhost:8080/job/student-management-system/lastBuild/consoleText

# Application health
curl http://localhost:8085/actuator/health/db
curl http://localhost:8085/actuator/health/livenessState
curl http://localhost:8085/actuator/health/readinessState

# Application metrics
curl http://localhost:8085/actuator/metrics
```

---

## 🔐 Security Commands

```bash
# Generate SSH key
ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa

# Copy SSH key to worker
ssh-copy-id -i ~/.ssh/id_rsa.pub ubuntu@<WORKER_NODE_IP>

# Test SSH connection
ssh -i ~/.ssh/id_rsa ubuntu@<WORKER_NODE_IP>

# View Docker login status
cat ~/.docker/config.json

# Logout from Docker
docker logout
```

---

## 📝 Useful File Locations

```
Project Root: /opt/student-management-system/
Source Code: src/
Build Output: target/
Docker Compose: docker-compose.yml
Jenkinsfile: Jenkinsfile
Ansible Playbooks: deploy-db.yml, deploy-app.yml, etc.
Configuration: application.properties
Database Init: init.sql
Jenkins Home: /var/lib/jenkins/
Ansible Config: /etc/ansible/ansible.cfg
Docker Data: /var/lib/docker/
```

---

## 🔗 Useful URLs

```
Application: http://localhost:8085
Jenkins: http://localhost:8080
Docker Hub: https://hub.docker.com
GitHub: https://github.com/your-username/student-management-system
Ansible Docs: https://docs.ansible.com/
Spring Boot Docs: https://spring.io/projects/spring-boot
```

---

## 📞 Support

For issues and questions:
1. Check README.md for detailed documentation
2. Review Troubleshooting section
3. Check logs for error messages
4. Open GitHub issue
5. Contact DevOps team

---

**Version**: 1.0.0  
**Last Updated**: 2024
