#!/usr/bin/env bash

# Exit immediately if a command exits with a non-zero status
set -e

# Color definitions
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Helper output functions
log_info() { echo -e "${BLUE}[INFO]${NC} $1"; }
log_success() { echo -e "${GREEN}[SUCCESS]${NC} $1"; }
log_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

COMMAND=$1
PARAM=$2

case "$COMMAND" in
  dev)
    log_info "Starting Local Development Environment with .env.local..."
    docker compose --env-file .env.local up --build -d
    log_success "Dev stack is up! Run './run.sh logs' to watch API output."
    ;;

  dev-down)
    log_warn "Stopping Local Development Containers..."
    docker compose down
    log_success "Dev environment stopped."
    ;;

  prod-test)
    log_info "Spinning up local test using Production config (.env.production)..."
    docker compose -f docker-compose.prod.yml --env-file .env.production up --build -d
    log_success "Production environment running locally."
    ;;

  logs)
    log_info "Streaming API logs (Ctrl+C to stop)..."
    docker logs -f ecommerce-api-dev
    ;;

  # --- TEST COMMANDS ---
  test)
    if [ -n "$PARAM" ]; then
      log_info "Running specific test: $PARAM..."
      ./mvnw test -Dtest="$PARAM"
    else
      log_info "Running all unit tests..."
      ./mvnw test
    fi
    log_success "All executed tests passed!"
    ;;

  test-debug)
    if [ -z "$PARAM" ]; then
      log_error "Please specify a test class name. Example: ./run.sh test-debug UserControllerTest"
      exit 1
    fi
    log_info "Running $PARAM with full stack traces (-e -X)..."
    ./mvnw test -Dtest="$PARAM" -e -X
    ;;

  test-report)
    log_info "Checking Surefire test reports for failures..."
    if [ -d "target/surefire-reports" ]; then
      echo -e "${YELLOW}=== Failure Reports Summary ===${NC}"
      cat target/surefire-reports/*.txt 2>/dev/null || log_info "No failing test text reports found."
    else
      log_warn "No target/surefire-reports directory found. Run './run.sh test' first."
    fi
    ;;

  clean)
    log_warn "Cleaning Maven target folder and resetting containers..."
    docker compose down -v
    ./mvnw clean
    log_success "Cleanup complete."
    ;;

  *)
    echo -e "${YELLOW}E-Commerce REST API - Control Script${NC}"
    echo "Usage: ./run.sh <command> [option]"
    echo ""
    echo "Container Commands:"
    echo "  dev                 Start local dev stack (App + PostgreSQL)"
    echo "  dev-down            Stop local dev stack"
    echo "  prod-test           Test production compose locally with .env.production"
    echo "  logs                Stream live logs from the API container"
    echo "  clean               Stop containers, clear volumes, and run 'mvn clean'"
    echo ""
    echo "Testing Commands:"
    echo "  test                Run all Maven unit tests"
    echo "  test <TestClassName> Run a single test class (e.g., ./run.sh test UserControllerTest)"
    echo "  test-debug <Class>  Run a test class with verbose debug/stack traces"
    echo "  test-report         Print Surefire error text reports from target/"
    exit 1
    ;;
esac