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
"userName": "rupesh",
"password": "rupesh",
"roles":["USER"],     // optional
"email":"rupesh@gmail.com", // optional
"sentimentAnalysis": "HAPPY"
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
  "userName": "rupesh",
  "password": "rupesh"
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
- Response: Hi rupesh

### 5. Update logged-in user
- URL: `PUT /journal/user`
- Method: PUT
- Auth: yes
- Body: JSON with updated user data
- Example body:
```json
{
    "userName":"teerth",
    "password":"teerth",
    "email":"teerth@gmail.com",
    "sentimentAnalysis": "SAD"
}
```
- Response: `204 with User updated successfully`

### 6. Delete logged-in user
- URL: `DELETE /journal/user`
- Method: DELETE
- Auth: yes
- Body: none
- Response: `200 - User deleted successfully `

### 7. Get all journal entries for logged-in user
- URL: `GET /journal/journal`
- Method: GET
- Auth: yes
- Body: none
- Response: `200 OK` 
  [
      {
          "id": 5,
          "title": "My first entry",
          "content": "This is a test journal entry.",
          "date": "2026-06-29T09:12:08.62029",
          "sentiment": null
      },
      {
          "id": 6,
          "title": "My first phone was Mi",
          "content": "This is a test journal entry.",
          "date": "2026-06-29T09:12:46.553482",
          "sentiment": null
      },
      {
          "id": 7,
          "title": "My first phone was Mi",
          "content": "This is a test journal entry.",
          "date": "2026-06-29T09:14:03.075719",
          "sentiment": "HAPPY"
      }
  ]
  
### 8. Create a journal entry
- URL: `POST /journal/journal`
- Method: POST
- Auth: yes
- Body: JSON representation of `JournalEntry`
- Example body:
```json
{
  "title": "My first phone was Mi",
  "content": "This is a test journal entry.",
  "sentiment": "HAPPY"
}
```
- Response: `201 Created`

### 9. Get journal entry by ID
- URL: `GET /journal/journal/id/{myId}`
Ex - localhost:8080/journal/journal/id/7
- Method: GET
- Auth: yes
- Body: none
- Response:
{
    "id": 7,
    "title": "My first phone was Mi",
    "content": "This is a test journal entry.",
    "date": "2026-06-29T09:14:03.075719",
    "sentiment": "HAPPY"
}

### 10. Update journal entry by ID
- URL: `PUT /journal/journal/id/{myId}`
- Method: PUT
- Auth: yes
- Body: JSON with fields to update
- Example body:
```json
{
  "title": "teerth was funny guy",
  "content": "He was friend of in college",
  "email":"teerth123@gmail.com",
  "sentiment": "HAPPY"
}
```
- Response: `200 OK` with updated entry or `404 Not Found`

### 11. Delete journal entry by ID
- URL: `DELETE /journal/journal/id/{myId}`
  localhost:8080/journal/journal/id/7
- Method: DELETE
- Auth: yes
- Body: none
- Response: `204 No Content` or `404 Not Found`

<!-- ### 12. Get weather for a city
- URL: `GET /journal/weather/{city}`
- Method: GET
- Auth: yes
- Body: none
- Response: `200 OK` with weather JSON -->

---

## Admin Endpoints

> These endpoints require the logged-in user to have `ROLE_ADMIN`.

### 13. Get all users
- URL: `GET /journal/admin/all-users`
- Method: GET
- Auth: yes (ADMIN role)
  -> add the jwt token for user has role 'ADMIN' otherwise it dont work
- Body: none
- Response: `200 OK` with JSON list of all users

### 14. Create admin user
- URL: `POST /journal/admin/create-admin-user`
- Method: POST
- Auth: yes (ADMIN role)
  -> only ADMIN role user can add another ADMIN role user
- Body: JSON representing a `User`
- Example body:
```json
{
  "userName": "saurav",
  "password": "saurav",
  "email":"saurav973532@gmail.com",
  "sentiment":"ANGRY",
  "roles": ["ADMIN"]
}

// The user should not exist in DB.

```
- Response: `200 OK` (no body)

