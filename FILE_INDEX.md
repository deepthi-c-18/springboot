# Project File Index - Student Management System

## Complete DevOps CI/CD Project with Spring Boot, Docker, Jenkins, and Ansible

### 📂 Project Directory Structure

```
springboot-project/
│
├── 📄 pom.xml                                      # Maven configuration
├── 📄 Dockerfile                                   # Multi-stage Docker build
├── 📄 docker-compose.yml                           # Docker Compose orchestration
├── 📄 docker-compose.override.yml                  # Local override (optional)
├── 📄 init.sql                                     # MySQL initialization
├── 📄 Jenkinsfile                                  # CI/CD pipeline definition
├── 📄 hosts.ini                                    # Ansible inventory
├── 📄 .gitignore                                   # Git ignore patterns
├── 📄 setup.sh                                     # Automated setup script
├── 📄 README.md                                    # Complete documentation
├── 📄 QUICK_REFERENCE.md                           # Quick command reference
├── 📄 FILE_INDEX.md                                # This file
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 com/studentapp/
│   │   │       ├── StudentManagementApplication.java
│   │   │       ├── 📁 entity/
│   │   │       │   └── Student.java
│   │   │       ├── 📁 repository/
│   │   │       │   └── StudentRepository.java
│   │   │       ├── 📁 service/
│   │   │       │   └── StudentService.java
│   │   │       └── 📁 controller/
│   │   │           └── StudentController.java
│   │   │
│   │   └── 📁 resources/
│   │       ├── application.properties               # Spring Boot config
│   │       ├── 📁 templates/
│   │       │   ├── index.html                       # Home page
│   │       │   ├── student-list.html                # List students
│   │       │   ├── add-student.html                 # Add student form
│   │       │   └── update-student.html              # Update student form
│   │       └── 📁 static/
│   │           ├── 📁 css/
│   │           │   └── style.css                    # Responsive styling
│   │           └── 📁 js/
│   │               └── main.js                      # Frontend logic
│   │
│   └── 📁 test/
│       └── 📁 java/
│           └── 📁 com/studentapp/
│               ├── 📁 service/
│               │   └── StudentServiceTest.java      # Unit tests
│               └── 📁 controller/
│
├── 📁 target/                                      # Maven build output
│   ├── student-management-system-1.0.0.jar
│   └── ...
│
├── 📁 ansible/                                     # Ansible playbooks (optional)
│   ├── deploy-db.yml                               # Deploy MySQL
│   ├── deploy-app.yml                              # Deploy app
│   ├── update-app.yml                              # Update app
│   ├── remove-db.yml                               # Remove DB
│   └── remove-app.yml                              # Remove app
│
└── 📁 docs/                                        # Additional documentation
    ├── ARCHITECTURE.md                             # Architecture details
    ├── SETUP_GUIDE.md                              # Setup instructions
    └── TROUBLESHOOTING.md                          # Troubleshooting
```

---

## 📋 File Descriptions

### Root Configuration Files

| File | Description | Purpose |
|------|-------------|---------|
| `pom.xml` | Maven configuration | Build management, dependencies |
| `Dockerfile` | Container definition | Build application image |
| `docker-compose.yml` | Container orchestration | Local development setup |
| `init.sql` | Database script | Initialize MySQL database |
| `Jenkinsfile` | CI/CD pipeline | Automate build & deploy |
| `hosts.ini` | Ansible inventory | Define deployment hosts |
| `.gitignore` | Git ignore rules | Exclude unnecessary files |
| `setup.sh` | Setup automation | Install dependencies |

---

### Java Source Code

#### Main Application
```
src/main/java/com/studentapp/
├── StudentManagementApplication.java (47 lines)
│   └── Spring Boot entry point with component scan
```

#### Entity Layer
```
src/main/java/com/studentapp/entity/
├── Student.java (97 lines)
│   ├── JPA entity with 5 fields (id, name, email, course, timestamps)
│   ├── Auto-timestamp management (@PrePersist, @PreUpdate)
│   └── Lombok annotations for boilerplate reduction
```

#### Repository Layer
```
src/main/java/com/studentapp/repository/
├── StudentRepository.java (67 lines)
│   ├── Extends JpaRepository<Student, Long>
│   ├── Custom query methods:
│   │   ├── findByEmail(String)
│   │   ├── findByCourse(String)
│   │   ├── searchByName(String)
│   │   ├── existsByEmail(String)
│   │   └── countByCourse(String)
│   └── Spring Data JPA integration
```

#### Service Layer
```
src/main/java/com/studentapp/service/
├── StudentService.java (192 lines)
│   ├── Business logic implementation
│   ├── Methods:
│   │   ├── getAllStudents()
│   │   ├── getStudentById(Long)
│   │   ├── getStudentByEmail(String)
│   │   ├── getStudentsByCourse(String)
│   │   ├── searchStudentsByName(String)
│   │   ├── addStudent(Student)
│   │   ├── updateStudent(Long, Student)
│   │   ├── deleteStudent(Long)
│   │   ├── getTotalStudentCount()
│   │   └── getStudentCountByCourse(String)
│   ├── @Transactional management
│   ├── Error handling
│   └── Logging (Lombok @Slf4j)
```

#### Controller Layer
```
src/main/java/com/studentapp/controller/
├── StudentController.java (223 lines)
│   ├── Web Controllers:
│   │   ├── home() - Root redirect
│   │   ├── listStudents() - List view with search
│   │   ├── showAddForm() - Add form
│   │   ├── saveStudent() - Save new
│   │   ├── showEditForm() - Edit form
│   │   ├── updateStudent() - Update record
│   │   └── deleteStudent() - Delete record
│   ├── REST API Endpoints:
│   │   ├── /api/all - Get all (GET)
│   │   ├── /api/{id} - Get by ID (GET)
│   │   ├── /api/create - Create (POST)
│   │   ├── /api/update/{id} - Update (PUT)
│   │   └── /api/delete/{id} - Delete (DELETE)
│   ├── Error handling with RedirectAttributes
│   └── Comprehensive logging
```

#### Test Layer
```
src/test/java/com/studentapp/service/
├── StudentServiceTest.java (253 lines)
│   ├── 13 test methods using Mockito
│   ├── Tests:
│   │   ├── testGetAllStudents()
│   │   ├── testGetStudentById()
│   │   ├── testGetStudentByIdNotFound()
│   │   ├── testAddStudent()
│   │   ├── testAddStudentDuplicateEmail()
│   │   ├── testUpdateStudent()
│   │   ├── testUpdateStudentNotFound()
│   │   ├── testDeleteStudent()
│   │   ├── testDeleteStudentNotFound()
│   │   ├── testSearchStudentsByName()
│   │   ├── testGetStudentsByCourse()
│   │   ├── testGetTotalStudentCount()
│   │   ├── testGetStudentCountByCourse()
│   │   └── testGetStudentByEmail()
│   ├── Mocking with Mockito
│   └── Assertions with JUnit 5
```

---

### Configuration Files

| File | Lines | Description |
|------|-------|-------------|
| `application.properties` | 68 | Spring Boot configuration |
| `init.sql` | 25 | MySQL initialization |

#### application.properties
```properties
- Server configuration (port 8080)
- DataSource configuration (MySQL)
- JPA/Hibernate settings
- Thymeleaf configuration
- Logging setup
- Actuator configuration
```

#### init.sql
```sql
- Create students table
- Create indexes (email, course, created_at)
- Insert sample data (5 students)
- Grant user privileges
```

---

### Frontend Files

#### HTML Templates

| File | Lines | Components |
|------|-------|-----------|
| `index.html` | 156 | Home page with hero, features |
| `student-list.html` | 134 | List with search & actions |
| `add-student.html` | 168 | Form with validation |
| `update-student.html` | 188 | Edit form with danger zone |

#### CSS Styling

| File | Lines | Features |
|------|-------|----------|
| `style.css` | 834 | Responsive Bootstrap + custom |

#### JavaScript

| File | Lines | Functions |
|------|-------|-----------|
| `main.js` | 378 | API calls, validation, utilities |

---

### DevOps Files

#### Docker

| File | Lines | Purpose |
|------|-------|---------|
| `Dockerfile` | 61 | Multi-stage build |
| `docker-compose.yml` | 81 | Compose orchestration |
| `init.sql` | 25 | Database init |

#### Jenkins

| File | Lines | Stages |
|------|-------|--------|
| `Jenkinsfile` | 527 | 12-stage pipeline |

#### Ansible

| File | Lines | Tasks |
|------|-------|-------|
| `deploy-db.yml` | 198 | Deploy MySQL (15 tasks) |
| `deploy-app.yml` | 214 | Deploy Spring Boot (18 tasks) |
| `update-app.yml` | 203 | Update app (16 tasks) |
| `remove-db.yml` | 43 | Remove DB (5 tasks) |
| `remove-app.yml` | 43 | Remove app (5 tasks) |
| `hosts.ini` | 33 | Inventory + vars |

---

### Documentation

| File | Lines | Topics |
|------|-------|--------|
| `README.md` | 1200+ | Complete documentation |
| `QUICK_REFERENCE.md` | 350+ | Quick commands |
| `FILE_INDEX.md` | 250+ | This file |

---

## 🔢 Code Statistics

### Source Code
- **Total Java Files**: 6
- **Total Test Files**: 1
- **Total Lines of Java Code**: ~1,000

### Frontend
- **Total HTML Files**: 4
- **Total HTML Lines**: ~650
- **Total CSS Lines**: ~834
- **Total JavaScript Lines**: ~378

### Configuration
- **Configuration Files**: 6
- **Total Config Lines**: ~400

### Pipeline & IaC
- **Jenkinsfile**: 527 lines (12 stages)
- **Ansible Playbooks**: 5 files, ~700 lines
- **Docker Files**: 2 files, ~140 lines

### Documentation
- **Total Documentation Lines**: ~2,000+

### Total Project
- **Total Lines of Code**: ~6,000+
- **Total Files**: 30+
- **Build Artifacts**: 1 JAR
- **Docker Images**: 2 (Application + MySQL)

---

## 🏗️ Architecture Summary

```
Frontend Layer (HTML/CSS/JavaScript)
        ↓
Web Controller (Thymeleaf + REST API)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Spring Data JPA)
        ↓
Entity Layer (JPA Entities)
        ↓
Database Layer (MySQL)
```

---

## 🔄 CI/CD Pipeline Flow

```
1. Checkout (Git)
       ↓
2. Build (Maven)
       ↓
3. Test (JUnit/Mockito)
       ↓
4. Docker Build
       ↓
5. Docker Tag
       ↓
6. Docker Push (DockerHub)
       ↓
7. Docker Cleanup
       ↓
8. Deploy DB (Ansible)
       ↓
9. Deploy App (Ansible)
       ↓
10. Health Check
       ↓
11. Verify Deployment
       ↓
12. Cleanup & Notify
```

---

## 📦 Dependencies

### Maven Dependencies (pom.xml)
- Spring Boot 3.3.0
- Spring Data JPA
- Spring Web
- Thymeleaf
- MySQL Driver 8.0.33
- Lombok
- JUnit 5
- Mockito

### Docker Dependencies
- eclipse-temurin:21-jdk-jammy
- mysql:8.0
- bootstrap:5.3.0 (CDN)
- Font Awesome 6.4.0 (CDN)

### System Dependencies
- Java 21
- Maven 3.9+
- Docker 20.10+
- Ansible 2.12+
- Ubuntu 20.04+

---

## 🚀 Quick Navigation

### To Build Application
```bash
mvn clean package
```

### To Run Locally
```bash
mvn spring-boot:run
```

### To Deploy with Docker
```bash
docker-compose up -d
```

### To Run Tests
```bash
mvn test
```

### To Deploy with Ansible
```bash
ansible-playbook -i hosts.ini deploy-app.yml
```

---

## 📝 File Naming Conventions

- `*Entity.java` - JPA entities
- `*Repository.java` - Spring Data repositories
- `*Service.java` - Service/business logic
- `*Controller.java` - REST controllers
- `*Test.java` - Unit tests
- `*-db.yml` - Database playbooks
- `*-app.yml` - Application playbooks

---

## 🔐 Security Considerations

- Non-root Docker user
- Environment variables for secrets
- SSH key authentication
- Health checks
- Error handling
- Logging without sensitive data

---

## 📊 Project Metrics

| Metric | Value |
|--------|-------|
| Java Classes | 6 |
| Test Classes | 1 |
| HTML Templates | 4 |
| REST Endpoints | 8 |
| Database Tables | 1 |
| Docker Containers | 2 |
| Ansible Playbooks | 5 |
| Jenkins Stages | 12 |
| Test Cases | 13 |
| Code Lines | ~6,000 |

---

## ✅ Checklist

- [x] Spring Boot 3 with Java 21
- [x] MySQL Database Integration
- [x] Responsive Frontend
- [x] REST API Endpoints
- [x] Docker Containerization
- [x] Jenkins CI/CD Pipeline
- [x] Ansible Automation
- [x] Unit Tests
- [x] Comprehensive Documentation
- [x] Production Ready

---

## 📞 Support & Help

For detailed information about specific files, refer to:
- `README.md` - Main documentation
- `QUICK_REFERENCE.md` - Command reference
- Individual file headers - Code documentation

---

**Last Updated**: 2024
**Project Version**: 1.0.0
**Status**: Production Ready ✅
