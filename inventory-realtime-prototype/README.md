# Inventory Realtime Prototype

Simple end-to-end prototype demonstrating:
- Java Spring Boot backend (REST + WebSocket + Kafka producer/consumer)
- MySQL for persistence
- Kafka for event streaming
- Docker & Docker Compose to run everything locally
- Minimal frontend (HTML/JS) that subscribes to real-time inventory changes via WebSocket (STOMP)

## Quick start (requires Docker & Docker Compose)
```bash
# from repo root
docker-compose up --build
```

- Backend REST API: `http://localhost:8080/api/inventory`
  - GET /api/inventory - list items
  - POST /api/inventory - create item `{ "name":"item", "quantity": 10 }`
  - PUT /api/inventory/{id}?qty=5 - update quantity (will publish Kafka event and broadcast to connected frontends)

- Frontend: open `http://localhost:8080/` to see the dashboard and real-time updates.

## Project structure highlights
- `backend/` - Spring Boot application
- `frontend/` - simple static HTML dashboard served by the backend (placed in resources/static)
- `docker-compose.yml` - MySQL, Zookeeper, Kafka, backend service

## Notes
- This is a small prototype intended for demonstration and learning. Production setups require secure configs, proper Kafka setup, credentials, TLS, and more robust error handling.
