#### PROJECT PROMPTS

### 🛠️ Prompt 1: Project Setup, Models, and JWT Security

**Role:** Senior Java & Spring Boot Architect.
**Context:** Building a production-grade E-Commerce Backend REST API.
**Tech Stack:** Java 21, Spring Boot 3.4.2, PostgreSQL, Spring Security, JJWT, Lombok, Spring Data JPA.

**Task:**
Generate the core configuration and security layer for the Spring Boot application based on the requirements below.

**Requirements:**

1. **Application Main:** Class `com.ecommerce.EcommerceApplication`.
2. **CORS Configuration (`CorsConfig.java`):** Allow credentials, expose `Authorization` and `Content-Disposition` headers, allow methods (`GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `OPTIONS`), and configure allowed origins (`http://localhost:3000`, `http://localhost:5173`, `https://ecommerce-admin-two-phi.vercel.app`).
3. **Application Security Config (`ApplicationConfig.java`):** Configure `UserDetailsService` (find by email), `DaoAuthenticationProvider`, `AuthenticationManager`, and `BCryptPasswordEncoder`.
4. **JWT Infrastructure (`JwtService.java` & `JwtAuthenticationFilter.java`):**
   - Extract claims/username, sign tokens with HMAC-SHA, and set expiration from application properties (`application.security.jwt.secret`, `application.security.jwt.expiration`).
   - Create a `OncePerRequestFilter` to intercept Bearer tokens and populate the `SecurityContextHolder`.
5. **Security Filter Chain (`SecurityConfig.java`):**
   - Enable method-level security (`@EnableMethodSecurity`).
   - Permit public endpoints: `/api/v1/auth/**`, `/api/v1/health`, `/actuator/**`, Swagger docs (`/v3/api-docs/**`, `/swagger-ui/**`), uploaded files (`/uploads/**`), and public `GET` requests for `/api/v1/categories/**` and `/api/v1/products/**`.
   - Require authentication for all other requests. Stateless session management.
6. **OpenAPI Documentation (`OpenApiConfig.java`):** Setup Swagger UI with Bearer Token (`JWT`) security scheme.
7. **Static Resource Config (`WebConfig.java`):** Map `/uploads/**` URI pattern to local directory specified by `file.upload-dir`.
8. **Database Seeder (`DataSeeder.java`):** `CommandLineRunner` to seed default `ADMIN` user (`admin@ecommerce.com`) and default categories/products if DB is empty.

**Output Format:** Provide complete, compilable Java source files with package declarations under `com.ecommerce.config`.

### 📦 Prompt 2: Entities, Enums, DTOs, and Repositories

**Role:** Senior Java Developer.
**Context:** Spring Boot 3.4.2 E-Commerce Backend.

**Task:**
Generate JPA Entities, Enums, Repositories, and DTOs for the e-commerce domain.

**Requirements:**

1. **Entities & Enums:**

   - `User` (implements `UserDetails`): id, firstName, lastName, email, password, role (`ROLE_USER`, `ROLE_ADMIN`), enabled.
   - `Category`: id, name, description.
   - `Product`: id, name, description, price (BigDecimal), stockQuantity, category (`@ManyToOne`).
   - `Order` & `OrderStatus` enum (`PENDING`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`).
2. **DTOs:**
   - Auth: `AuthenticationRequest`, `AuthenticationResponse`, `RegisterRequest`.
   - User: `UserProfileResponse`, `UpdateProfileRequest`, `ChangePasswordRequest`, `UpdateRoleRequest`.
   - Catalog: `CategoryRequest`, `CategoryResponse`, `ProductRequest`, `ProductResponse`, `PageResponse<T>`.
   - Orders & Payments: `OrderRequest`, `OrderResponse`, `PaymentRequest`, `PaymentResponse`.
3. **Repositories:** Spring Data JPA repositories (`UserRepository`, `CategoryRepository`, `ProductRepository`, `OrderRepository`). `UserRepository` must include `findByEmail(String email)`.

**Output Format:** Provide clean Java source code with Lombok annotations (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`).

### 🧠 Prompt 3: Service Layer Implementation

**Role:** Backend Integration Engineer.
**Context:** Spring Boot E-Commerce System.

**Task:**
Implement the service interfaces and implementations for core business functionality.

**Requirements:**

1. **`AuthenticationService`:** Handle user registration, password hashing with BCrypt, authentication via `AuthenticationManager`, and token generation.
2. **`UserService`:** Support retrieving/updating the authenticated user's profile (`/me`), changing password, and admin features (paginated user listing, updating roles, toggling active status, deleting users).
3. **`CategoryService` & `ProductService`:** CRUD operations for categories and products. Support paginated/sorted product retrieval.
4. **`FileUploadService`:** Handle multipart file persistence to local disk (e.g., `./uploads`), generating unique file names.
5. **`OrderService`:** Handle order placement for authenticated users, fetching user order history, order details lookup, and admin status updates.
6. **Payment Polymorphism (`PaymentService`):**
   - Create interface `PaymentService` with methods `initializePayment(PaymentRequest)` and `verifyPayment(String txRef)`.
   - Implement two qualifier Spring beans: `@Service("stripePaymentService")` and `@Service("chapaPaymentService")`.

**Output Format:** Complete Java class files under `com.ecommerce.service`.

### 🌐 Prompt 4: REST Controller Layer

**Role:** REST API Developer.
**Context:** Exposing end-user and admin endpoints with Spring Doc / Swagger annotations.

**Task:**
Generate the REST Controller classes under `com.ecommerce.controller` matching these specification routes:

1. **`AuthenticationController` (`/api/v1/auth`):**
   - `POST /register` -> `register(@Valid @RequestBody RegisterRequest)`
   - `POST /login` -> `authenticate(@Valid @RequestBody AuthenticationRequest)`

2. **`CategoryController` (`/api/v1/categories`):**
   - `GET /` -> Public list all categories.
   - `GET /{id}` -> Public get category by ID.
   - `POST /`, `PUT /{id}`, `DELETE /{id}` -> Protected (Admin) with `@SecurityRequirement(name = "bearerAuth")`.

3. **`ProductController` (`/api/v1/products`):**
   - `GET /page` -> Paginated/sorted products (`PageResponse<ProductResponse>`).
   - `GET /`, `GET /{id}`, `GET /category/{categoryId}` -> Public endpoints.
   - `POST /`, `PUT /{id}`, `DELETE /{id}` -> Protected endpoints.

4. **`UserController` (`/api/v1/users`):**
   - `GET /me`, `PUT /me`, `PATCH /me/password` -> Authenticated user endpoints (`@AuthenticationPrincipal UserDetails`).
   - `GET /`, `GET /{id}`, `PATCH /{id}/role`, `PATCH /{id}/status`, `DELETE /{id}` -> Admin guarded with `@PreAuthorize("hasRole('ADMIN')")`. Use `@ParameterObject @PageableDefault` for user list pagination.

5. **`OrderController` (`/api/v1/orders`):**
   - `POST /` -> Checkout using `@AuthenticationPrincipal UserDetails`.
   - `GET /` -> Get user order history.
   - `GET /{id}` -> Order detail lookup.
   - `PATCH /{id}/status` -> Admin status update.

6. **`PaymentController` (`/api/v1/payments`):**
   - Inject `@Qualifier("stripePaymentService")` and `@Qualifier("chapaPaymentService")`.
   - `POST /stripe/initialize` & `POST /chapa/initialize`.
   - `GET /verify/{gateway}/{txRef}` -> Route verification dynamically to Stripe or Chapa implementation.

7. **`FileUploadController` (`/api/v1/files`):**
   - `POST /upload` -> `MultipartFile` handler returning `Map<String, String>` containing `fileName` and fully qualified `fileUrl` generated via `ServletUriComponentsBuilder`.

8. **`HealthController` (`/api/v1/health`):**
   - `GET /` -> Returns status "UP" and operational status message.

**Output Format:** Executable Spring Boot Controllers with SpringDoc annotations (`@Tag`, `@Operation`, `@SecurityRequirement`).
