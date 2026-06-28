# API Testing Guide

## Base URL
- Dev: `http://localhost:8080/journal`
- Prod: `http://localhost:8081/journal`

> The application uses `server.servlet.context-path: /journal`, so every endpoint is prefixed by `/journal`.

---

## Public Endpoints (no JWT required)

### 1. Health Check
- URL: `GET /journal/public/health-check`
- Auth: none
- Body: none
- Response: `Health is good`

### 2. Signup
- URL: `POST /journal/public/signup`
- Auth: none
- Body: JSON representing a `User`
- Example body:
```json
{
  "userName": "alice",
  "password": "Secret123",
  "roles": ["ROLE_USER"]
}
```
- Response: `200 OK` (no body)

### 3. Login
- URL: `POST /journal/public/login`
- Auth: none
- Body: JSON with username and password
- Example body:
```json
{
  "userName": "alice",
  "password": "Secret123"
}
```
- Response: `200 OK`
- Response body: raw JWT token string

> Use the returned JWT in subsequent protected requests.

---

## Authenticated Endpoints (Bearer JWT required)

### Header format
```
Authorization: Bearer <jwt-token>
```

### 4. Get current user greeting
- URL: `GET /journal/user`
- Method: GET
- Auth: yes
- Body: none
- Response: greeting string with weather information

### 5. Update logged-in user
- URL: `PUT /journal/user`
- Method: PUT
- Auth: yes
- Body: JSON with updated user data
- Example body:
```json
{
  "userName": "alice",
  "password": "NewSecret123"
}
```
- Response: `204 No Content`

### 6. Delete logged-in user
- URL: `DELETE /journal/user`
- Method: DELETE
- Auth: yes
- Body: none
- Response: `204 No Content`

### 7. Get all journal entries for logged-in user
- URL: `GET /journal/journal`
- Method: GET
- Auth: yes
- Body: none
- Response: `200 OK` with JSON list of entries

### 8. Create a journal entry
- URL: `POST /journal/journal`
- Method: POST
- Auth: yes
- Body: JSON representation of `JournalEntry`
- Example body:
```json
{
  "title": "My first entry",
  "content": "This is a test journal entry."
}
```
- Response: `201 Created`

### 9. Get journal entry by ID
- URL: `GET /journal/journal/id/{myId}`
Ex - localhost:8080/journal/journal/id/1
- Method: GET
- Auth: yes
- Body: none
- Response:
    {
    "id": 1,
    "title": "My first entry",
    "content": "This is a test journal entry.",
    "date": "2026-06-28T16:39:17.427333",
    "sentiment": null
    }

### 10. Update journal entry by ID
- URL: `PUT /journal/journal/id/{myId}`
- Method: PUT
- Auth: yes
- Body: JSON with fields to update
- Example body:
```json
{
  "title": "Updated title",
  "content": "Updated content"
}
```
- Response: `200 OK` with updated entry or `404 Not Found`

### 11. Delete journal entry by ID
- URL: `DELETE /journal/journal/id/{myId}`
- Method: DELETE
- Auth: yes
- Body: none
- Response: `204 No Content` or `404 Not Found`

### 12. Get weather for a city
- URL: `GET /journal/weather/{city}`
- Method: GET
- Auth: yes
- Body: none
- Response: `200 OK` with weather JSON

---

## Admin Endpoints

> These endpoints require the logged-in user to have `ROLE_ADMIN`.

### 13. Get all users
- URL: `GET /journal/admin/all-users`
- Method: GET
- Auth: yes (ADMIN role)
- Body: none
- Response: `200 OK` with JSON list of users

### 14. Create admin user
- URL: `POST /journal/admin/create-admin-user`
- Method: POST
- Auth: yes (ADMIN role)
- Body: JSON representing a `User`
- Example body:
```json
{
  "userName": "admin",
  "password": "AdminSecret",
  "roles": ["ROLE_ADMIN"]
}
```
- Response: `200 OK` (no body)

---

## Notes
- `POST /journal/public/login` returns the JWT as plain text. Use that exact token value in `Authorization: Bearer ...`.
- Protected routes are:
  - `/journal/user/**`
  - `/journal/journal/**`
  - `/journal/weather/**`
  - `/journal/admin/**`
- Public routes are:
  - `/journal/public/**`

## Example curl flow
1. Signup:
```bash
curl -X POST http://localhost:8080/journal/public/signup \
  -H "Content-Type: application/json" \
  -d '{"userName":"alice","password":"Secret123","roles":["ROLE_USER"]}'
```

2. Login:
```bash
curl -X POST http://localhost:8080/journal/public/login \
  -H "Content-Type: application/json" \
  -d '{"userName":"alice","password":"Secret123"}'
```

3. Use JWT for protected request:
```bash
curl -X GET http://localhost:8080/journal/journal \
  -H "Authorization: Bearer <jwt>"
```
