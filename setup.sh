#!/bin/bash

# =====================================================
# Setup Script - Student Management System
# =====================================================
# This script automates the setup of the project on Master Node

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Student Management System Setup${NC}"
echo -e "${BLUE}========================================${NC}"

# Function to print colored output
print_status() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# Check if running as sudo
if [[ $EUID -ne 0 ]]; then
   print_error "This script must be run as root"
   exit 1
fi

# 1. Update system
print_info "Step 1: Updating system packages..."
apt update
apt upgrade -y
print_status "System packages updated"

# 2. Install Java 21
print_info "Step 2: Installing Java 21..."
apt install -y openjdk-21-jdk
java_version=$(java -version 2>&1 | grep "openjdk version")
print_status "Java 21 installed: $java_version"

# 3. Install Maven
print_info "Step 3: Installing Maven..."
apt install -y maven
mvn_version=$(mvn --version | head -1)
print_status "Maven installed: $mvn_version"

# 4. Install Git
print_info "Step 4: Installing Git..."
apt install -y git
git_version=$(git --version)
print_status "Git installed: $git_version"

# 5. Install Docker
print_info "Step 5: Installing Docker..."
apt install -y apt-transport-https ca-certificates curl gnupg lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
apt update
apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
systemctl start docker
systemctl enable docker
docker_version=$(docker --version)
print_status "Docker installed: $docker_version"

# 6. Install Jenkins
print_info "Step 6: Installing Jenkins..."
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | gpg --dearmor -o /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/ | tee /etc/apt/sources.list.d/jenkins.list > /dev/null
apt update
apt install -y jenkins
systemctl start jenkins
systemctl enable jenkins
print_status "Jenkins installed"

# 7. Install Ansible
print_info "Step 7: Installing Ansible..."
apt install -y ansible
ansible_version=$(ansible --version | head -1)
print_status "Ansible installed: $ansible_version"

# 8. Generate SSH key
print_info "Step 8: Generating SSH key for automation..."
if [ ! -f ~/.ssh/id_rsa ]; then
    ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa -N "" 2>/dev/null
    print_status "SSH key generated"
else
    print_warning "SSH key already exists"
fi

# 9. Get Jenkins initial admin password
print_info "Step 9: Retrieving Jenkins initial admin password..."
sleep 10
JENKINS_PASSWORD=$(cat /var/lib/jenkins/secrets/initialAdminPassword 2>/dev/null || echo "Password not yet available")
print_status "Jenkins initial password: $JENKINS_PASSWORD"

# 10. Display summary
echo -e "\n${BLUE}========================================${NC}"
echo -e "${GREEN}Setup Completed Successfully!${NC}"
echo -e "${BLUE}========================================${NC}"

echo -e "\n${YELLOW}Next Steps:${NC}"
echo "1. Access Jenkins at: http://localhost:8080"
echo "2. Enter the initial admin password: $JENKINS_PASSWORD"
echo "3. Install recommended plugins"
echo "4. Create first admin user"
echo "5. Configure DockerHub credentials"
echo "6. Configure SSH credentials for worker node"
echo "7. Create Jenkins pipeline job"

echo -e "\n${YELLOW}Useful Commands:${NC}"
echo "View Java version: java -version"
echo "View Maven version: mvn --version"
echo "View Docker version: docker --version"
echo "View Ansible version: ansible --version"
echo "View Jenkins status: systemctl status jenkins"
echo "View Jenkins logs: tail -f /var/log/jenkins/jenkins.log"

echo -e "\n${YELLOW}Configuration Files:${NC}"
echo "Jenkins: /etc/default/jenkins"
echo "Docker: /etc/docker/daemon.json"
echo "Ansible: /etc/ansible/ansible.cfg"

echo -e "\n${BLUE}========================================${NC}"
print_status "Setup script completed!"
echo -e "${BLUE}========================================${NC}"
