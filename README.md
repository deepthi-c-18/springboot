# Student Management System - Complete DevOps CI/CD Project

> A complete, production-ready Student Management System web application demonstrating real-world DevOps practices with Spring Boot, Docker, Jenkins, Ansible, and MySQL.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Local Development](#local-development)
- [Docker Deployment](#docker-deployment)
- [Jenkins CI/CD Pipeline](#jenkins-cicd-pipeline)
- [Ansible Deployment](#ansible-deployment)
- [GitHub Repository Setup](#github-repository-setup)
- [DockerHub Setup](#dockerhub-setup)
- [Production Deployment](#production-deployment)
- [API Endpoints](#api-endpoints)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 Project Overview

The Student Management System is a full-stack web application that demonstrates enterprise-level DevOps practices including:

- **Backend**: Spring Boot 3 REST API with Java 21
- **Frontend**: Responsive HTML/CSS/Bootstrap 5 interface
- **Database**: MySQL 8.0 for data persistence
- **Containerization**: Docker and Docker Compose
- **CI/CD**: Jenkins with declarative pipeline
- **Infrastructure as Code**: Ansible playbooks for deployment
- **Version Control**: GitHub integration

### Features

- ✅ Add, view, update, and delete student records
- ✅ Responsive web interface with Bootstrap 5
- ✅ RESTful API endpoints
- ✅ MySQL database with JPA/Hibernate ORM
- ✅ Docker containerization
- ✅ Automated CI/CD pipeline
- ✅ Infrastructure automation with Ansible
- ✅ Health checks and monitoring
- ✅ Comprehensive logging and error handling
- ✅ Security best practices

---

## 🏗️ Architecture

### System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     GitHub Repository                        │
│                  (Version Control)                           │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│              Jenkins Master Node (CI/CD)                     │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ 1. Checkout Code                                    │   │
│  │ 2. Build with Maven                                │   │
│  │ 3. Run Tests                                        │   │
│  │ 4. Build Docker Image                              │   │
│  │ 5. Push to DockerHub                               │   │
│  │ 6. Deploy with Ansible                             │   │
│  └─────────────────────────────────────────────────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│  DockerHub   │ │  Ansible     │ │    SSH       │
│  Repository  │ │  Inventory   │ │   Keys       │
└──────────────┘ └──────────────┘ └──────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│            Worker Node (Production Deployment)              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │ Docker Containers                                    │  │
│  │  ┌──────────────┐          ┌──────────────┐         │  │
│  │  │ Spring App   │          │   MySQL DB   │         │  │
│  │  │ Port: 8080   │──────────│ Port: 3306   │         │  │
│  │  │              │          │              │         │  │
│  │  │ Java 21      │          │ studentdb    │         │  │
│  │  └──────────────┘          └──────────────┘         │  │
│  │                                                      │  │
│  │ Docker Network: student-network                     │  │
│  └──────────────────────────────────────────────────────┘  │
│                                                             │
│ External Access: http://worker-ip:8085                    │
└─────────────────────────────────────────────────────────────┘
```

### Master Node Components

- **Ubuntu Server**: Operating system
- **Git**: Version control client
- **Docker**: Container runtime
- **Jenkins**: CI/CD orchestration
- **Ansible**: Infrastructure automation
- **Java 21**: Application runtime
- **Maven 3.x**: Build tool

### Worker Node Components

- **Ubuntu Server**: Operating system
- **Docker**: Container runtime
- **Docker Compose**: Container orchestration
- **Java 21**: Optional (if running directly)

---

## 🛠️ Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Application runtime |
| Spring Boot | 3.x | Web framework |
| Maven | 3.9+ | Build tool |
| MySQL | 8.0 | Database |
| Docker | 20.10+ | Containerization |
| Docker Compose | 2.0+ | Container orchestration |
| Jenkins | 2.x | CI/CD platform |
| Ansible | 2.12+ | Configuration management |
| Git | 2.x | Version control |
| Bootstrap | 5.x | Frontend framework |

---

## 📦 Prerequisites

### Master Node Requirements

- **OS**: Ubuntu 20.04 LTS or later
- **CPU**: 4 cores minimum
- **RAM**: 8 GB minimum
- **Disk**: 50 GB minimum
- **Network**: Static IP address

### Worker Node Requirements

- **OS**: Ubuntu 20.04 LTS or later
- **CPU**: 4 cores minimum
- **RAM**: 8 GB minimum
- **Disk**: 50 GB minimum
- **Network**: Static IP address

### Local Development Requirements

- Java 21 JDK
- Maven 3.9+
- Git
- Docker & Docker Compose
- IDE (IntelliJ, VS Code, Eclipse)

---

## 🚀 Installation & Setup

### 1. Master Node Setup

#### 1.1 Install Java 21

```bash
# Update system packages
sudo apt update
sudo apt upgrade -y

# Install OpenJDK 21
sudo apt install openjdk-21-jdk -y

# Verify installation
java -version
javac -version

# Expected output: openjdk version "21"
```

#### 1.2 Install Maven

```bash
# Install Maven
sudo apt install maven -y

# Verify installation
mvn --version

# Expected output: Apache Maven 3.9.x
```

#### 1.3 Install Git

```bash
# Install Git
sudo apt install git -y

# Verify installation
git --version

# Configure Git
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

#### 1.4 Install Docker

```bash
# Install Docker prerequisites
sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Add Docker GPG key
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Add Docker repository
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Update package list
sudo apt update

# Install Docker
sudo apt install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y

# Verify installation
docker --version
docker ps

# Add user to docker group
sudo usermod -aG docker $USER
newgrp docker
```

#### 1.5 Install Jenkins

```bash
# Add Jenkins repository key
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee /usr/share/keyrings/jenkins-keyring.asc > /dev/null

# Add Jenkins repository
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

# Update package list
sudo apt update

# Install Jenkins
sudo apt install jenkins -y

# Start Jenkins service
sudo systemctl start jenkins
sudo systemctl enable jenkins

# Get initial admin password
sudo cat /var/lib/jenkins/secrets/initialAdminPassword

# Access Jenkins at http://localhost:8080
```

#### 1.6 Install Ansible

```bash
# Install Ansible
sudo apt install ansible -y

# Verify installation
ansible --version

# Create SSH key for Ansible
ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa -N ""
```

#### 1.7 Configure SSH for Worker Node

```bash
# Copy SSH public key to worker node
ssh-copy-id -i ~/.ssh/id_rsa.pub ubuntu@<WORKER_NODE_IP>

# Test SSH connection
ssh -i ~/.ssh/id_rsa ubuntu@<WORKER_NODE_IP> "echo 'SSH connection successful'"

# Verify SSH connection works
ssh ubuntu@<WORKER_NODE_IP>
```

### 2. Worker Node Setup

#### 2.1 Install Java 21

```bash
# Update system packages
sudo apt update
sudo apt upgrade -y

# Install OpenJDK 21
sudo apt install openjdk-21-jdk -y

# Verify installation
java -version
```

#### 2.2 Install Docker

```bash
# Install Docker prerequisites
sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Add Docker GPG key
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Add Docker repository
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Update package list
sudo apt update

# Install Docker and Docker Compose
sudo apt install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y

# Verify installation
docker --version

# Start Docker service
sudo systemctl start docker
sudo systemctl enable docker

# Add user to docker group
sudo usermod -aG docker $USER
newgrp docker
```

#### 2.3 Install Docker Compose

```bash
# Install Docker Compose standalone
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# Make executable
sudo chmod +x /usr/local/bin/docker-compose

# Verify installation
docker-compose --version
```

---

## ⚙️ Configuration

### 1. Jenkins Configuration

#### 1.1 Initial Setup

1. Access Jenkins at `http://localhost:8080`
2. Unlock Jenkins with the initial admin password
3. Install recommended plugins
4. Create first admin user
5. Start using Jenkins

#### 1.2 Configure Jenkins Credentials

```bash
# Navigate to: Manage Jenkins > Manage Credentials > Stored scoped to Jenkins

# Create Docker Hub credentials:
# - Kind: Username with password
# - Username: <your-dockerhub-username>
# - Password: <your-dockerhub-password>
# - ID: dockerhub-credentials

# Create SSH credentials:
# - Kind: SSH Username with private key
# - Username: ubuntu
# - Private key: Copy contents of ~/.ssh/id_rsa
# - ID: ssh-worker-node

# Create Database credentials:
# - Kind: Username with password
# - Username: student
# - Password: student123
# - ID: db-credentials
```

#### 1.3 Configure Maven in Jenkins

1. Go to: `Manage Jenkins > Tools`
2. Find Maven section
3. Click "Add Maven"
4. Name: Maven 3.9
5. Choose "Install automatically"
6. Version: 3.9.1

#### 1.4 Configure Java in Jenkins

1. Go to: `Manage Jenkins > Tools`
2. Find JDK section
3. Click "Add JDK"
4. Name: Java 21
5. JAVA_HOME: `/usr/lib/jvm/java-21-openjdk-amd64`

### 2. Ansible Configuration

#### 2.1 Configure Inventory File

```bash
# Edit hosts inventory file
sudo nano /etc/ansible/hosts

# Add the following:
[prod]
worker-node-1 ansible_host=192.168.1.100 ansible_user=ubuntu ansible_port=22 ansible_ssh_private_key_file=/home/jenkins/.ssh/id_rsa

[prod:vars]
ansible_python_interpreter=/usr/bin/python3
```

#### 2.2 Test Ansible Connection

```bash
# Test connection to worker nodes
ansible all -i /etc/ansible/hosts -m ping

# Expected output: SUCCESS
```

#### 2.3 Configure Ansible Vault (Optional)

```bash
# Create vault for sensitive data
ansible-vault create /etc/ansible/group_vars/prod/vault.yml

# Add sensitive variables:
vault_db_password: student123
vault_dockerhub_password: your_password

# Use in playbooks:
ansible-playbook -i inventory.ini playbook.yml --ask-vault-pass
```

### 3. Docker Configuration

#### 3.1 Docker Daemon Configuration

```bash
# Create daemon configuration
sudo nano /etc/docker/daemon.json

# Add:
{
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "10m",
    "max-file": "3"
  },
  "storage-driver": "overlay2",
  "insecure-registries": []
}

# Restart Docker
sudo systemctl restart docker
```

#### 3.2 Docker Hub Configuration

```bash
# Login to Docker Hub
docker login

# Enter your DockerHub credentials

# Verify login
docker ps

# Tag local image
docker tag student-management-system:latest deepthic18/student-management-system:latest

# Push to DockerHub
docker push deepthic18/student-management-system:latest
```

---

## 💻 Local Development

### 1. Clone Repository

```bash
# Clone the repository
git clone https://github.com/deepthi-c-18/student-management-system.git

# Navigate to project directory
cd student-management-system

# Verify project structure
ls -la
```

### 2. Build Project

```bash
# Clean and build project
mvn clean package

# Build without running tests
mvn clean package -DskipTests

# Run specific test
mvn test -Dtest=StudentServiceTest

# Run all tests
mvn test
```

### 3. Run Locally

```bash
# Run Spring Boot application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Access application at http://localhost:8080
```

### 4. Database Setup (Local)

```bash
# Using Docker Compose for local MySQL
docker-compose up -d mysql-db

# Or install MySQL locally
sudo apt install mysql-server -y

# Create database
mysql -u root -p -e "CREATE DATABASE studentdb;"

# Create user
mysql -u root -p -e "CREATE USER 'student'@'%' IDENTIFIED BY 'student123';"

# Grant privileges
mysql -u root -p -e "GRANT ALL PRIVILEGES ON studentdb.* TO 'student'@'%';"

# Flush privileges
mysql -u root -p -e "FLUSH PRIVILEGES;"
```

### 5. IDE Setup

#### IntelliJ IDEA

1. Open project in IntelliJ
2. Go to: File > Project Structure
3. Set Java SDK: Java 21
4. Enable annotation processing: Settings > Compiler > Annotation Processing
5. Run: Run > Run 'StudentManagementApplication'

#### VS Code

1. Install extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Docker

2. Create `.vscode/settings.json`:
```json
{
  "java.home": "/usr/lib/jvm/java-21-openjdk-amd64",
  "java.compiler.codeLens.enabled": true,
  "[java]": {
    "editor.defaultFormatter": "redhat.java",
    "editor.formatOnSave": true
  }
}
```

---

## 🐳 Docker Deployment

### 1. Build Docker Image

```bash
# Build image with Dockerfile
docker build -t student-management-system:latest .

# Build image with custom name
docker build -t <username>/student-management-system:1.0 .

# Verify build
docker images | grep student-management-system
```

### 2. Run Docker Container

```bash
# Run Spring Boot container
docker run -d \
  -p 8085:8080 \
  --name spring-app \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/studentdb \
  -e SPRING_DATASOURCE_USERNAME=student \
  -e SPRING_DATASOURCE_PASSWORD=student123 \
  student-management-system:latest

# Check container status
docker ps

# View container logs
docker logs spring-app

# Follow logs in real-time
docker logs -f spring-app
```

### 3. Docker Compose Deployment

```bash
# Start all services
docker-compose up -d

# View services
docker-compose ps

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Rebuild images
docker-compose up -d --build
```

### 4. Docker Network

```bash
# Create Docker network
docker network create student-network

# List networks
docker network ls

# Inspect network
docker network inspect student-network

# Connect container to network
docker network connect student-network spring-app
```

### 5. Docker Volumes

```bash
# List volumes
docker volume ls

# Inspect volume
docker volume inspect mysql_data

# Remove volume
docker volume rm mysql_data

# Backup volume
docker run --rm -v mysql_data:/data -v $(pwd):/backup alpine tar czf /backup/mysql_backup.tar.gz /data
```

---

## 🔄 Jenkins CI/CD Pipeline

### 1. Create Jenkins Job

```bash
# Create new Pipeline job:
1. New Item
2. Enter job name: "student-management-system"
3. Select "Pipeline"
4. Click "OK"

# Configure pipeline:
1. Definition: Pipeline script from SCM
2. SCM: Git
3. Repository URL: https://github.com/deepthi-c-18/student-management-system.git
4. Branch: */main
5. Script Path: Jenkinsfile
```

### 2. Pipeline Parameters

The Jenkins pipeline accepts the following parameters:

- **OPERATION**: deploy / remove / update
- **DEPLOY_DB**: true / false
- **DEPLOY_APP**: true / false
- **RUN_TESTS**: true / false
- **PUSH_DOCKER_IMAGE**: true / false

### 3. Pipeline Stages

1. **Checkout**: Clone source code from GitHub
2. **Build**: Compile with Maven
3. **Test**: Run unit tests
4. **Docker Build**: Create Docker image
5. **Docker Tag**: Tag image for registry
6. **Docker Push**: Push to DockerHub
7. **Cleanup Docker**: Remove dangling images
8. **Deploy MySQL**: Deploy database container
9. **Deploy Spring Boot**: Deploy application container
10. **Update Application**: Update running application
11. **Remove Containers**: Remove containers if requested
12. **Final Cleanup**: Clean up temporary files

### 4. Trigger Pipeline

#### Manual Trigger

```bash
# Access Jenkins
http://localhost:8080

# Navigate to job
student-management-system

# Click "Build with Parameters"

# Select parameters and click "Build"
```

#### GitHub Webhook

```bash
# In GitHub repository settings:
1. Settings > Webhooks
2. Add webhook
3. Payload URL: http://your-jenkins:8080/github-webhook/
4. Content type: application/json
5. Events: Push events
6. Active: Yes
```

#### Pipeline Script

```bash
# Trigger pipeline via script
curl -X POST \
  -u jenkins-user:jenkins-token \
  "http://localhost:8080/job/student-management-system/buildWithParameters?OPERATION=deploy&DEPLOY_DB=true&DEPLOY_APP=true"
```

### 5. Pipeline Logs

```bash
# View pipeline logs in Jenkins UI
# Or access via terminal:
curl -u jenkins-user:jenkins-token \
  "http://localhost:8080/job/student-management-system/lastBuild/consoleText"
```

---

## 📜 Ansible Deployment

### 1. Deploy Database

```bash
# Deploy MySQL database
ansible-playbook -i hosts.ini deploy-db.yml \
  -e "db_name=studentdb" \
  -e "db_user=student" \
  -e "db_password=student123" \
  -v

# With specific host
ansible-playbook -i hosts.ini deploy-db.yml \
  --limit prod \
  -v
```

### 2. Deploy Application

```bash
# Deploy Spring Boot application
ansible-playbook -i hosts.ini deploy-app.yml \
  -e "app_image=username/student-management-system:latest" \
  -e "db_host=mysql-db" \
  -v
```

### 3. Update Application

```bash
# Update to new version
ansible-playbook -i hosts.ini update-app.yml \
  -e "app_image=username/student-management-system:v2.0" \
  -v
```

### 4. Remove Containers

```bash
# Remove database container
ansible-playbook -i hosts.ini remove-db.yml -v

# Remove application container
ansible-playbook -i hosts.ini remove-app.yml -v

# Remove all
ansible-playbook -i hosts.ini remove-db.yml remove-app.yml -v
```

### 5. Verify Deployment

```bash
# Check container status on worker node
ansible prod -i hosts.ini -a "docker ps"

# Check application health
ansible prod -i hosts.ini -a "curl -s http://localhost:8085/actuator/health"

# Check database connection
ansible prod -i hosts.ini -a "docker exec mysql-db mysqladmin ping"
```

---

## 🔐 GitHub Repository Setup

### 1. Create GitHub Repository

```bash
# Initialize local git repository
git init

# Add all files
git add .

# Initial commit
git commit -m "Initial commit: Student Management System with Spring Boot and DevOps"

# Add remote repository
git remote add origin https://github.com/deepthi-c-18/student-management-system.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### 2. Repository Structure

```
project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/studentapp/
│   │   │       ├── StudentManagementApplication.java
│   │   │       ├── controller/
│   │   │       │   └── StudentController.java
│   │   │       ├── service/
│   │   │       │   └── StudentService.java
│   │   │       ├── repository/
│   │   │       │   └── StudentRepository.java
│   │   │       └── entity/
│   │   │           └── Student.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── student-list.html
│   │       │   ├── add-student.html
│   │       │   └── update-student.html
│   │       └── static/
│   │           ├── css/
│   │           │   └── style.css
│   │           └── js/
│   │               └── main.js
│   └── test/
│       └── java/com/studentapp/
│           └── StudentServiceTest.java
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── init.sql
├── Jenkinsfile
├── hosts.ini
├── deploy-db.yml
├── deploy-app.yml
├── update-app.yml
├── remove-db.yml
├── remove-app.yml
├── .gitignore
└── README.md
```

### 3. GitHub Actions (Alternative to Jenkins)

```yaml
# .github/workflows/build-deploy.yml
name: Build and Deploy

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 21
        uses: actions/setup-java@v2
        with:
          java-version: '21'
      - name: Build with Maven
        run: mvn clean package
      - name: Build Docker image
        run: docker build -t student-management-system:latest .
```

---

## 🐳 DockerHub Setup

### 1. DockerHub Account

```bash
# Create account at https://hub.docker.com

# Login locally
docker login

# Enter username and password/token
```

### 2. Create Repository

```bash
# In DockerHub:
1. Create a new repository
2. Name: student-management-system
3. Description: Student Management System - DevOps CI/CD Project
4. Visibility: Public/Private as needed
```

### 3. Push Image

```bash
# Tag image
docker tag student-management-system:latest deepthic18/student-management-system:latest

# Push to DockerHub
docker push deepthic18/student-management-system:latest

# Push with version tag
docker tag student-management-system:latest deepthic18/student-management-system:1.0
docker push deepthic18/student-management-system:1.0
```

### 4. Pull Image

```bash
# Pull image from DockerHub
docker pull deepthic18/student-management-system:latest

# Run container
docker run -d -p 8085:8080 deepthic18/student-management-system:latest
```

---

## 🌐 Production Deployment

### 1. Pre-Deployment Checklist

- [ ] All tests passing
- [ ] Docker image built and tested
- [ ] Database migrations verified
- [ ] Environment variables configured
- [ ] SSL/TLS certificates ready (if HTTPS)
- [ ] Firewall rules configured
- [ ] Backup strategy in place
- [ ] Monitoring configured
- [ ] Logging configured
- [ ] Documentation updated

### 2. Deployment Steps

```bash
# 1. SSH into master node
ssh -i ~/.ssh/id_rsa ubuntu@<MASTER_NODE_IP>

# 2. Navigate to project directory
cd /opt/student-management-system

# 3. Pull latest code
git pull origin main

# 4. Run Jenkins build with parameters
curl -X POST \
  -u jenkins-user:jenkins-token \
  "http://localhost:8080/job/student-management-system/buildWithParameters?OPERATION=deploy&DEPLOY_DB=true&DEPLOY_APP=true&PUSH_DOCKER_IMAGE=true"

# 5. Monitor deployment
watch 'curl -s http://<WORKER_NODE_IP>:8085/actuator/health'

# 6. Verify all services
ansible prod -i hosts.ini -a "docker ps"
```

### 3. Health Checks

```bash
# Check application health
curl http://<WORKER_NODE_IP>:8085/actuator/health

# Check database connection
curl http://<WORKER_NODE_IP>:8085/actuator/health/db

# Access application
http://<WORKER_NODE_IP>:8085

# View logs
docker logs spring-app

# Check resource usage
docker stats spring-app
```

### 4. Rollback Procedure

```bash
# If deployment fails, rollback to previous version
ansible-playbook -i hosts.ini update-app.yml \
  -e "app_image=username/student-management-system:previous-version" \
  -v

# Or remove and redeploy
ansible-playbook -i hosts.ini remove-app.yml
ansible-playbook -i hosts.ini deploy-app.yml \
  -e "app_image=username/student-management-system:stable" \
  -v
```

---

## 📡 API Endpoints

### Base URL
```
http://<host>:8085/students
```

### Web UI Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Home page |
| `/home` | GET | Home page redirect |
| `/list` | GET | View all students |
| `/add` | GET | Add student form |
| `/save` | POST | Save new student |
| `/edit/{id}` | GET | Edit student form |
| `/update/{id}` | POST | Update student |
| `/delete/{id}` | GET | Delete student |

### REST API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/all` | GET | Get all students |
| `/api/{id}` | GET | Get student by ID |
| `/api/create` | POST | Create new student |
| `/api/update/{id}` | PUT | Update student |
| `/api/delete/{id}` | DELETE | Delete student |

### Example API Requests

```bash
# Get all students
curl -X GET http://localhost:8085/students/api/all

# Get student by ID
curl -X GET http://localhost:8085/students/api/1

# Create new student
curl -X POST http://localhost:8085/students/api/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "course": "Java"
  }'

# Update student
curl -X PUT http://localhost:8085/students/api/update/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com",
    "course": "Spring Boot"
  }'

# Delete student
curl -X DELETE http://localhost:8085/students/api/delete/1
```

---

## 🔧 Troubleshooting

### Common Issues and Solutions

#### Issue 1: Jenkins Pipeline Failing at Build Stage

```bash
# Problem: Maven build fails
# Solution:

# 1. Verify Java is installed
java -version

# 2. Verify Maven is installed
mvn --version

# 3. Clear Maven cache
rm -rf ~/.m2/repository
mvn clean

# 4. Check Jenkins Java configuration
# In Jenkins: Manage Jenkins > Tools > JDK > Check Java 21 path
```

#### Issue 2: Docker Image Build Failure

```bash
# Problem: Docker build fails
# Solution:

# 1. Check Docker is running
docker ps

# 2. Build with verbose output
docker build -t student-management-system:latest . --verbose

# 3. Check Dockerfile syntax
docker build -t test:latest . --no-cache

# 4. Check available disk space
df -h

# 5. Clear Docker cache
docker builder prune
```

#### Issue 3: Ansible Playbook Failure

```bash
# Problem: Ansible deployment fails
# Solution:

# 1. Check SSH connectivity
ssh -i ~/.ssh/id_rsa ubuntu@<WORKER_NODE_IP> "echo 'Connected'"

# 2. Check inventory file
ansible all -i hosts.ini -m ping

# 3. Run with verbose output
ansible-playbook -i hosts.ini deploy-app.yml -vvv

# 4. Check Docker on worker node
ssh ubuntu@<WORKER_NODE_IP> "docker ps"
```

#### Issue 4: Application Not Accessible

```bash
# Problem: Cannot access application at port 8085
# Solution:

# 1. Check if container is running
docker ps | grep spring-app

# 2. Check port mapping
docker port spring-app

# 3. Check firewall rules
sudo ufw status
sudo ufw allow 8085/tcp

# 4. Check container logs
docker logs spring-app

# 5. Check application health
curl http://localhost:8085/actuator/health
```

#### Issue 5: Database Connection Error

```bash
# Problem: Application cannot connect to database
# Solution:

# 1. Check MySQL container is running
docker ps | grep mysql-db

# 2. Check MySQL connection
docker exec mysql-db mysql -u root -p<password> -e "SELECT 1"

# 3. Verify environment variables
docker inspect spring-app | grep -i datasource

# 4. Check database exists
docker exec mysql-db mysql -u root -p<password> -e "SHOW DATABASES;"

# 5. Check user permissions
docker exec mysql-db mysql -u root -p<password> -e "SELECT user, host FROM mysql.user;"
```

### Debug Mode

```bash
# Enable debug logging in application.properties
logging.level.com.studentapp=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# View container logs
docker logs -f spring-app

# Exec into container
docker exec -it spring-app /bin/bash

# View running processes in container
docker exec spring-app ps aux
```

---

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👥 Author

**DevOps Engineer**
- GitHub: [@deepthi-c-18](https://github.com/deepthi-c-18)
- Email: your.email@example.com

---

## 🙏 Acknowledgments

- Spring Boot team for excellent framework
- Docker for containerization technology
- Jenkins for CI/CD automation
- Ansible for infrastructure automation
- Bootstrap for responsive frontend framework

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Docker Documentation](https://docs.docker.com/)
- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Ansible Documentation](https://docs.ansible.com/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## 🚀 Quick Start Commands

```bash
# Development
mvn clean package
mvn spring-boot:run

# Docker
docker-compose up -d
docker-compose down

# Ansible
ansible-playbook -i hosts.ini deploy-db.yml
ansible-playbook -i hosts.ini deploy-app.yml

# Testing
mvn test
curl http://localhost:8085

# Logs
docker logs -f spring-app
docker logs -f mysql-db
```

---

**Last Updated**: 2024
**Version**: 1.0.0
**Status**: Production Ready ✅
