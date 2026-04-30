📑 Kanban Task Manager API
A robust RESTful API for managing Kanban-style tasks. Built with Spring Boot 21, secured with JWT, and deployed on Render. It features a complex Role-Based Access Control (RBAC) system and live documentation.

🚀 Live Documentation (Swagger)
You can interact with the API and test all endpoints directly from your browser:

🔗 Explore API on Swagger UI

Note: Since this is hosted on a free Render tier, the first request may take 30-50 seconds to load while the server "wakes up".

🛠️ Tech Stack

Java 21 & Spring Boot 3.3.4
Spring Security with JWT (JSON Web Tokens)
Spring Data JPA for ORM and persistence.
PostgreSQL (Hosted on Supabase).
Lombok to reduce boilerplate code.
Maven for dependency management.
Docker for containerization.

⚖️ Business Rules & RBAC
The system implements a strict Role-Based Access Control (RBAC) to ensure project integrity:

Feature                Master (Owner)           Developer (Member)
Create Project              ✅                            ❌
Manage Members              ✅                            ❌
Create/Delete Cards         ✅                            ❌
Edit/Move Cards             ✅                            ✅
Delete Project              ✅                            ❌
Self-Remove            ❌(Protected)                      ✅

🛡️ Security Layers

URL Level (Spring Security): Authentication filter checks for valid JWTs. Auth routes (/auth/) are public.
Service Level: Custom Java logic validates resource ownership (e.g., verifying the userId matches the Master role before critical deletions).

🧪 How to Test (Step-by-Step)
Follow this flow in Swagger to test the full collaboration logic:

User Setup:
Register a Master user at /auth/register.
Register a Developer user (different email) to simulate a teammate.

Authentication:
Login at /auth/login with the Master credentials.
Copy the token from the response.
Click "Authorize" (top-right lock icon) and enter: Bearer YOUR_TOKEN.

Project Workflow:
Create Project: POST /api/projects. You are now the Master of this ID.
Add Member: Use POST /api/projects/{projectId}/members/{userId} using the ID of the Developer user.
Create Tasks: Use POST /api/tasks linked to your Project ID.
Role Verification:
Switch authorization to the Developer token.
Attempt to delete the project or a task. You should receive a 403 Forbidden error.

📂 Project Structure

src/main/java/com/example/demo/
├── controller/    # REST Endpoints & HTTP handling
├── service/       # Business logic & RBAC rules
├── repository/    # JPA Data access layer (PostgreSQL)
├── model/         # Database Entities (Project, Task, User)
├── dto/           # Data Transfer Objects
├── security/      # JWT Configuration & Security Filters
├── exception/     # Global Error Handling
└── DemoApplication.java # Application Entry Point
