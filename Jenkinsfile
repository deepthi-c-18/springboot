// =====================================================
// Jenkinsfile - Student Management System CI/CD Pipeline
// Declarative Pipeline with Java 21, Maven, Docker, Ansible
// =====================================================

pipeline {
    agent any

    // Environment variables
    environment {
        // Application properties
        APP_NAME = 'student-management-system'
        REPO_URL = 'https://github.com/deepthi-c-18/student-management-system.git'
        REPO_BRANCH = 'main'
        
        // Docker properties
        DOCKERHUB_USERNAME = credentials('dockerhub-username')
        DOCKERHUB_PASSWORD = credentials('dockerhub-password')
        DOCKER_IMAGE = "${DOCKERHUB_USERNAME}/${APP_NAME}"
        DOCKER_TAG = "${BUILD_NUMBER}"
        REGISTRY_URL = 'docker.io'
        
        // SSH credentials for Ansible
        SSH_CREDENTIALS_ID = 'ssh-worker-node'
        SSH_KEY_PATH = '/home/jenkins/.ssh/id_rsa'
        
        // Worker node details
        WORKER_NODE_IP = '192.168.1.100'
        WORKER_NODE_USER = 'ubuntu'
        
        // Maven properties
        MAVEN_HOME = '/usr/share/maven'
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        
        // Database properties
        DB_HOST = 'mysql-db'
        DB_PORT = '3306'
        DB_NAME = 'studentdb'
        DB_USER = credentials('db-username')
        DB_PASSWORD = credentials('db-password')
        
        // Build properties
        BUILD_TIMEOUT = '30'
        TEST_TIMEOUT = '10'
    }

    // Build parameters
    parameters {
        choice(name: 'OPERATION', choices: ['deploy', 'remove', 'update'], description: 'Choose operation')
        booleanParam(name: 'DEPLOY_DB', defaultValue: true, description: 'Deploy MySQL database')
        booleanParam(name: 'DEPLOY_APP', defaultValue: true, description: 'Deploy Spring Boot application')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run unit tests')
        booleanParam(name: 'PUSH_DOCKER_IMAGE', defaultValue: true, description: 'Push Docker image to DockerHub')
    }

    options {
        // Keep last 30 builds
        buildDiscarder(logRotator(numToKeepStr: '30', daysToKeepStr: '7'))
        
        // Timeout for entire pipeline
        timeout(time: 2, unit: 'HOURS')
        
        // Disable concurrent builds
        disableConcurrentBuilds()
        
        // Add timestamps to log
        timestamps()
    }

    stages {
        stage('1. Checkout') {
            steps {
                script {
                    echo "========== STAGE: Checkout =========="
                    echo "Checking out source code from ${REPO_URL}"
                }
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "${REPO_BRANCH}"]],
                    userRemoteConfigs: [[url: "${REPO_URL}"]]
                ])
            }
            post {
                success {
                    echo "✓ Source code checked out successfully"
                }
                failure {
                    echo "✗ Failed to checkout source code"
                    error("Checkout failed")
                }
            }
        }

        stage('2. Build') {
            steps {
                script {
                    echo "========== STAGE: Build =========="
                    echo "Building application using Maven with Java 21"
                }
                sh '''
                    echo "Java version:"
                    java -version
                    echo "Maven version:"
                    mvn --version
                    echo "Starting build..."
                    mvn clean package -DskipTests -X
                '''
            }
            post {
                success {
                    echo "✓ Build completed successfully"
                    archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
                }
                failure {
                    echo "✗ Build failed"
                    error("Maven build failed")
                }
            }
        }

        stage('3. Test') {
            when {
                expression { params.RUN_TESTS }
            }
            steps {
                script {
                    echo "========== STAGE: Test =========="
                    echo "Running unit tests..."
                }
                sh '''
                    mvn test -Dtest.timeout=${TEST_TIMEOUT}
                '''
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    publishHTML([
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report'
                    ])
                }
                success {
                    echo "✓ All tests passed"
                }
                failure {
                    echo "✗ Tests failed"
                }
            }
        }

        stage('4. Docker Build') {
            steps {
                script {
                    echo "========== STAGE: Docker Build =========="
                    echo "Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
                sh '''
                    docker build \
                        --build-arg JAVA_VERSION=21 \
                        -t ${DOCKER_IMAGE}:${DOCKER_TAG} \
                        -t ${DOCKER_IMAGE}:latest \
                        -f Dockerfile .
                    
                    echo "Docker image built successfully"
                    docker images | grep student-management-system
                '''
            }
            post {
                success {
                    echo "✓ Docker image created: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
                failure {
                    echo "✗ Docker build failed"
                    error("Docker build failed")
                }
            }
        }

        stage('5. Docker Tag') {
            steps {
                script {
                    echo "========== STAGE: Docker Tag =========="
                    echo "Tagging Docker image with version ${DOCKER_TAG}"
                }
                sh '''
                    docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                    docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:${DOCKER_TAG}
                    docker images | grep student-management-system
                '''
            }
            post {
                success {
                    echo "✓ Docker image tagged successfully"
                }
                failure {
                    echo "✗ Docker tag failed"
                }
            }
        }

        stage('6. Docker Push') {
            when {
                expression { params.PUSH_DOCKER_IMAGE }
            }
            steps {
                script {
                    echo "========== STAGE: Docker Push =========="
                    echo "Pushing Docker image to DockerHub: ${DOCKER_IMAGE}"
                }
                sh '''
                    echo "${DOCKERHUB_PASSWORD}" | docker login -u "${DOCKERHUB_USERNAME}" --password-stdin ${REGISTRY_URL}
                    docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                    docker push ${DOCKER_IMAGE}:latest
                    docker logout
                    echo "Docker images pushed successfully"
                '''
            }
            post {
                success {
                    echo "✓ Docker image pushed to DockerHub"
                }
                failure {
                    echo "✗ Docker push failed"
                }
            }
        }

        stage('7. Cleanup Docker') {
            steps {
                script {
                    echo "========== STAGE: Cleanup Docker =========="
                    echo "Removing unused Docker images and containers"
                }
                sh '''
                    echo "Removing dangling images..."
                    docker image prune -f
                    
                    echo "Removing stopped containers..."
                    docker container prune -f
                    
                    echo "Docker cleanup completed"
                    docker images | head -10
                '''
            }
            post {
                always {
                    echo "✓ Docker cleanup completed"
                }
            }
        }

        stage('8. Deploy MySQL') {
            when {
                expression { params.DEPLOY_DB && params.OPERATION == 'deploy' }
            }
            steps {
                script {
                    echo "========== STAGE: Deploy MySQL =========="
                    echo "Deploying MySQL database to worker node: ${WORKER_NODE_IP}"
                }
                sh '''
                    # Run Ansible playbook to deploy MySQL
                    ansible-playbook \
                        -i ${WORKER_NODE_IP}, \
                        -u ${WORKER_NODE_USER} \
                        --private-key=${SSH_KEY_PATH} \
                        -e "db_image=${DOCKER_IMAGE}:${DOCKER_TAG}" \
                        -e "db_name=${DB_NAME}" \
                        -e "db_user=${DB_USER}" \
                        -e "db_password=${DB_PASSWORD}" \
                        deploy-db.yml \
                        -v
                '''
            }
            post {
                success {
                    echo "✓ MySQL deployed successfully"
                }
                failure {
                    echo "✗ MySQL deployment failed"
                }
            }
        }

        stage('9. Deploy Spring Boot App') {
            when {
                expression { params.DEPLOY_APP && params.OPERATION == 'deploy' }
            }
            steps {
                script {
                    echo "========== STAGE: Deploy Spring Boot Application =========="
                    echo "Deploying Spring Boot application to worker node: ${WORKER_NODE_IP}"
                }
                sh '''
                    # Run Ansible playbook to deploy Spring Boot app
                    ansible-playbook \
                        -i ${WORKER_NODE_IP}, \
                        -u ${WORKER_NODE_USER} \
                        --private-key=${SSH_KEY_PATH} \
                        -e "app_image=${DOCKER_IMAGE}:${DOCKER_TAG}" \
                        -e "db_host=${DB_HOST}" \
                        -e "db_port=${DB_PORT}" \
                        -e "db_name=${DB_NAME}" \
                        -e "db_user=${DB_USER}" \
                        -e "db_password=${DB_PASSWORD}" \
                        deploy-app.yml \
                        -v
                '''
            }
            post {
                success {
                    echo "✓ Spring Boot application deployed successfully"
                    echo "Application URL: http://${WORKER_NODE_IP}:8085"
                }
                failure {
                    echo "✗ Spring Boot deployment failed"
                }
            }
        }

        stage('10. Update Application') {
            when {
                expression { params.OPERATION == 'update' }
            }
            steps {
                script {
                    echo "========== STAGE: Update Application =========="
                    echo "Updating Spring Boot application on worker node"
                }
                sh '''
                    # Run Ansible playbook to update application
                    ansible-playbook \
                        -i ${WORKER_NODE_IP}, \
                        -u ${WORKER_NODE_USER} \
                        --private-key=${SSH_KEY_PATH} \
                        -e "app_image=${DOCKER_IMAGE}:${DOCKER_TAG}" \
                        -e "db_host=${DB_HOST}" \
                        -e "db_port=${DB_PORT}" \
                        update-app.yml \
                        -v
                '''
            }
            post {
                success {
                    echo "✓ Application updated successfully"
                }
                failure {
                    echo "✗ Application update failed"
                }
            }
        }

        stage('11. Remove Containers') {
            when {
                expression { params.OPERATION == 'remove' }
            }
            steps {
                script {
                    echo "========== STAGE: Remove Containers =========="
                    echo "Removing containers from worker node"
                }
                sh '''
                    # Remove app containers
                    if [ "${DEPLOY_APP}" = "true" ]; then
                        ansible-playbook \
                            -i ${WORKER_NODE_IP}, \
                            -u ${WORKER_NODE_USER} \
                            --private-key=${SSH_KEY_PATH} \
                            remove-app.yml \
                            -v
                    fi
                    
                    # Remove database containers
                    if [ "${DEPLOY_DB}" = "true" ]; then
                        ansible-playbook \
                            -i ${WORKER_NODE_IP}, \
                            -u ${WORKER_NODE_USER} \
                            --private-key=${SSH_KEY_PATH} \
                            remove-db.yml \
                            -v
                    fi
                '''
            }
            post {
                success {
                    echo "✓ Containers removed successfully"
                }
                failure {
                    echo "✗ Container removal failed"
                }
            }
        }

        stage('12. Final Cleanup') {
            steps {
                script {
                    echo "========== STAGE: Final Cleanup =========="
                    echo "Performing final cleanup..."
                }
                sh '''
                    echo "Removing dangling Docker images..."
                    docker image prune -af
                    
                    echo "Removing unused volumes..."
                    docker volume prune -f
                    
                    echo "Final cleanup completed"
                '''
                // Clean workspace
                cleanWs()
            }
            post {
                always {
                    echo "✓ Final cleanup completed"
                }
            }
        }
    }

    post {
        always {
            script {
                echo "========== PIPELINE EXECUTION COMPLETED =========="
                echo "Build Number: ${BUILD_NUMBER}"
                echo "Build Status: ${currentBuild.result}"
                echo "Build URL: ${BUILD_URL}"
            }
        }
        success {
            script {
                echo "✓ Pipeline execution successful!"
                // Send success notification
                emailext(
                    subject: "✓ Build #${BUILD_NUMBER} - SUCCESS",
                    body: """
                        Build: ${BUILD_NUMBER}
                        Status: SUCCESS
                        URL: ${BUILD_URL}
                        Duration: ${currentBuild.durationString}
                        
                        Docker Image: ${DOCKER_IMAGE}:${DOCKER_TAG}
                        Application: http://${WORKER_NODE_IP}:8085
                    """,
                    to: 'devops@example.com'
                )
            }
        }
        failure {
            script {
                echo "✗ Pipeline execution failed!"
                // Send failure notification
                emailext(
                    subject: "✗ Build #${BUILD_NUMBER} - FAILURE",
                    body: """
                        Build: ${BUILD_NUMBER}
                        Status: FAILURE
                        URL: ${BUILD_URL}
                        Duration: ${currentBuild.durationString}
                    """,
                    to: 'devops@example.com'
                )
            }
        }
    }
}
