#!/bin/bash

# Docker Management Script for Book Management Microservices
# Usage: ./docker-manage.sh [command]

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
print_usage() {
    echo -e "${BLUE}Book Management Microservices - Docker Management${NC}"
    echo ""
    echo "Usage: ./docker-manage.sh [command]"
    echo ""
    echo "Commands:"
    echo "  ${GREEN}up${NC}              - Build and start all services"
    echo "  ${GREEN}down${NC}            - Stop all services"
    echo "  ${GREEN}restart${NC}         - Restart all services"
    echo "  ${GREEN}logs${NC}            - View logs from all services"
    echo "  ${GREEN}logs [service]${NC}  - View logs from a specific service"
    echo "  ${GREEN}ps${NC}              - Show running containers"
    echo "  ${GREEN}rebuild${NC}         - Rebuild all images (no cache)"
    echo "  ${GREEN}clean${NC}           - Remove containers and volumes"
    echo "  ${GREEN}health${NC}          - Check services health status"
    echo "  ${GREEN}shell [service]${NC} - Access a service container shell"
    echo "  ${GREEN}mysql${NC}           - Access MySQL database"
    echo ""
    echo "Examples:"
    echo "  ./docker-manage.sh up"
    echo "  ./docker-manage.sh logs auth"
    echo "  ./docker-manage.sh shell book"
    echo ""
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# Check if docker-compose is installed
check_docker() {
    if ! command -v docker-compose &> /dev/null; then
        print_error "docker-compose is not installed"
        exit 1
    fi
    print_success "docker-compose is available"
}

# Command: up
cmd_up() {
    print_info "Building images and starting services..."
    docker-compose up -d
    echo ""
    print_success "Services started successfully!"
    echo ""
    print_info "Waiting for services to be ready (this may take 30-60 seconds)..."
    echo ""
    
    sleep 10
    
    # Check Eureka
    if curl -s http://localhost:8761 > /dev/null 2>&1; then
        print_success "Eureka is ready"
    else
        print_error "Eureka is not ready yet (wait 20-30 more seconds)"
    fi
    
    echo ""
    docker-compose ps
}

# Command: down
cmd_down() {
    print_info "Stopping all services..."
    docker-compose down
    print_success "Services stopped"
}

# Command: restart
cmd_restart() {
    print_info "Restarting services..."
    docker-compose restart
    print_success "Services restarted"
}

# Command: logs
cmd_logs() {
    local service=$1
    if [ -z "$service" ]; then
        print_info "Showing logs from all services (Ctrl+C to exit)..."
        docker-compose logs -f
    else
        print_info "Showing logs from $service service..."
        docker-compose logs -f "$service"
    fi
}

# Command: ps
cmd_ps() {
    docker-compose ps
}

# Command: rebuild
cmd_rebuild() {
    print_info "Rebuilding all images (this may take several minutes)..."
    docker-compose build --no-cache
    print_success "Images rebuilt successfully"
    echo ""
    print_info "Starting services..."
    docker-compose up -d
    print_success "Services started"
}

# Command: clean
cmd_clean() {
    print_error "This will remove all containers and volumes (database data will be lost)"
    read -p "Are you sure? (y/N) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_info "Removing containers and volumes..."
        docker-compose down -v
        print_success "Cleanup completed"
    else
        print_info "Cleanup cancelled"
    fi
}

# Command: health
cmd_health() {
    print_info "Checking service health..."
    echo ""
    
    echo -n "MySQL:     "
    if docker exec book-mysql mysqladmin ping -h localhost -u root -proot123 &> /dev/null; then
        print_success "Ready"
    else
        print_error "Not ready"
    fi
    
    echo -n "Eureka:    "
    if curl -s http://localhost:8761 > /dev/null 2>&1; then
        print_success "Ready"
    else
        print_error "Not ready"
    fi
    
    echo -n "Gateway:   "
    if curl -s http://localhost:8050/actuator/health > /dev/null 2>&1 2>/dev/null; then
        print_success "Ready"
    else
        print_error "Not ready"
    fi
    
    echo ""
    print_info "Full docker-compose status:"
    docker-compose ps
}

# Command: shell
cmd_shell() {
    local service=$1
    if [ -z "$service" ]; then
        print_error "Please specify a service name"
        echo "Available services: eureka, gateway, auth, book, wishlist, user, post, mysql"
        exit 1
    fi
    
    local container=""
    case $service in
        eureka) container="eureka-server" ;;
        gateway) container="api-gateway" ;;
        auth) container="auth-service" ;;
        book) container="book-service" ;;
        wishlist) container="wishlist-service" ;;
        user) container="user-service" ;;
        post) container="post-service" ;;
        mysql) container="book-mysql" ;;
        *) print_error "Unknown service: $service"; exit 1 ;;
    esac
    
    print_info "Accessing $service container shell..."
    docker exec -it "$container" /bin/bash
}

# Command: mysql
cmd_mysql() {
    print_info "Connecting to MySQL database..."
    docker exec -it book-mysql mysql -uroot -proot123 project
}

# Main script
main() {
    local command=${1:-}
    
    if [ -z "$command" ]; then
        print_usage
        exit 0
    fi
    
    check_docker
    echo ""
    
    case $command in
        up) cmd_up ;;
        down) cmd_down ;;
        restart) cmd_restart ;;
        logs) cmd_logs "$2" ;;
        ps) cmd_ps ;;
        rebuild) cmd_rebuild ;;
        clean) cmd_clean ;;
        health) cmd_health ;;
        shell) cmd_shell "$2" ;;
        mysql) cmd_mysql ;;
        -h|--help|help) print_usage ;;
        *) print_error "Unknown command: $command"; echo ""; print_usage; exit 1 ;;
    esac
}

main "$@"
