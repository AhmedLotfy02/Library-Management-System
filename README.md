
<h1>Library Management System 

<h2>Table of Contents</h2>
<ul>
<li><a href="#overview">Overview</a></li>
<li><a href="#prerequisites">Prerequisites</a></li>
<li><a href="#getting-started">Getting Started</a>
  <ul>
      <li><a href="#clone-the-repository">Clone the Repository</a></li>
      <li><a href="#configure-the-database">Configure the Database</a></li>
      <li><a href="#run-the-application">Run the Application</a></li>
      <li><a href="#access-the-api">Access the API</a></li>
  </ul>
</li>
<li><a href="#authentication">Authentication</a></li>

<li><a href="#api-endpoints">API Endpoints</a>
  <ul>
      <li><a href="#authentication">Authentication</a></li>
 <li><a href="#books">Books</a>
        <ul>
            <li><a href="#get-all-books">Get All Books</a></li>
            <li><a href="#get-book-by-id">Get Book by ID</a></li>
            <li><a href="#create-book">Create Book</a></li>
            <li><a href="#update-book">Update Book</a></li>
            <li><a href="#delete-book">Delete Book</a></li>
        </ul>
    </li>
    <li><a href="#patrons">Patrons</a>
        <ul>
            <li><a href="#get-all-patrons">Get All Patrons</a></li>
            <li><a href="#get-patron-by-id">Get Patron by ID</a></li>
            <li><a href="#create-patron">Create Patron</a></li>
            <li><a href="#update-patron">Update Patron</a></li>
            <li><a href="#delete-patron">Delete Patron</a></li>
        </ul>
    </li>
    <li><a href="#borrowing-records">Borrowing Records</a>
        <ul>
            <li><a href="#borrow-book">Borrow Book</a></li>
            <li><a href="#return-book">Return Book</a></li>
        </ul>
    </li>
  </ul>
</li>
  
<li><a href="#Entity-Relationship-Diagram">Database Schema</a></li>
<li><a href="#caching">Caching</a></li>
<li><a href="#aop-aspect-oriented-programming">AOP (Aspect-Oriented Programming)</a></li>
<li><a href="#running-tests">Running Tests</a></li>
<li><a href="#contribution">Contribution</a></li>
<li><a href="#contact">Contact</a></li>
</ul>

<h2 id="overview">Overview</h2>
<p>
The Library Management System is a Spring Boot application designed to manage library operations, including managing books, patrons, and borrowing records. The system includes secure API endpoints for various CRUD operations.
</p>

<h2 id="prerequisites">Prerequisites</h2>
<ul>
<li>Java 20 or later</li>
<li>Maven 3.6+</li>
<li>Spring Boot 3.3.2</li>
<li>MySQL</li>
<li>Postman or any API testing tool (optional)</li>
</ul>

<h2 id="getting-started">Getting Started</h2>

<h3 id="clone-the-repository">1. Clone the Repository</h3>
<pre><code>git clone https://github.com/AhmedLotfy02/LibraryManagementSystem.git
cd LibraryManagementSystem
</code></pre>

<h3 id="configure-the-database">2. Configure the Database</h3>
<p>Update the <code>application.properties</code> file with your database configuration:</p>
<pre><code>spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
</code></pre>

<h3 id="run-the-application">3. Run the Application</h3>
<p>Use Maven to build and run the application:</p>
<pre><code>mvn clean install
mvn spring-boot:run
</code></pre>

<h2 id="authentication">Authentication</h2>
<p>
The application uses JWT for authentication. To access secured endpoints, you need to include the JWT token in the <code>Authorization</code> header as a Bearer token.
</p>

<p><strong>Example:</strong></p>
<pre><code>Authorization: Bearer your_jwt_token
</code></pre>

<h3>Obtaining a Token</h3>
<ol>
<li>Use the <code>/authenticate</code> endpoint to get a JWT token by providing valid user credentials.</li>
<li>Include the token in the <code>Authorization</code> header for subsequent API requests.</li>
</ol>

<h3 id="access-the-api">4. Access the API</h3>
<p>The application will start on <code>http://localhost:8081</code>.</p>
<blockquote>There is a postman collection contains all api documentations and requests in the repo "Library Management System APIs.postman_collection.json"</blockquote>
<h2 id="api-endpoints">API Endpoints</h2>

<h3 id="authentication">Authentication</h3>

<h4>Login</h4>
<p><strong>Endpoint:</strong> <code>/authenticate</code></p>
<p><strong>Method:</strong> POST</p>
<p><strong>Description:</strong> Authenticates a user and returns a JWT token.</p>
<blockquote>Note: for this demo use username = "admin" and password = "password" to access all APIs.</blockquote>
<p><strong>Request Body:</strong></p>
<pre><code>{
"username": "admin",
"password": "password"
}
</code></pre>

<p><strong>Response:</strong></p>
<pre><code>{
"token": "jwt_token"
}
</code></pre>

<h2 id="books">Books</h2>

<h3 id="get-all-books">Get All Books</h3>
<p><strong>Endpoint:</strong> <code>/api/books</code></p>
<p><strong>Method:</strong> GET</p>
<p><strong>Description:</strong> Retrieves a list of all books.</p>
<p><strong>Response:</strong></p>
<pre><code>[
    {
        "id": 1,
        "title": "Book Title",
        "author": "Book Author",
        "publicationYear": 2020,
        "isbn": "1234567890"
    }
]</code></pre>

<h3 id="get-book-by-id">Get Book by ID</h3>
<p><strong>Endpoint:</strong> <code>/api/books/{id}</code></p>
<p><strong>Method:</strong> GET</p>
<p><strong>Description:</strong> Retrieves a book by its ID.</p>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "title": "Book Title",
    "author": "Book Author",
    "publicationYear": 2020,
    "isbn": "1234567890"
}</code></pre>

<h3 id="create-book">Create Book</h3>
<p><strong>Endpoint:</strong> <code>/api/books</code></p>
<p><strong>Method:</strong> POST</p>
<p><strong>Description:</strong> Creates a new book.</p>
<p><strong>Request Body:</strong></p>
<pre><code>{
    "title": "New Book",
    "author": "New Author",
    "publicationYear": 2021,
    "isbn": "0987654321"
}</code></pre>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 2,
    "title": "New Book",
    "author": "New Author",
    "publicationYear": 2021,
    "isbn": "0987654321"
}</code></pre>

<h3 id="update-book">Update Book</h3>
<p><strong>Endpoint:</strong> <code>/api/books/{id}</code></p>
<p><strong>Method:</strong> PUT</p>
<p><strong>Description:</strong> Updates an existing book.</p>
<p><strong>Request Body:</strong></p>
<pre><code>{
    "title": "Updated Book",
    "author": "Updated Author",
    "publicationYear": 2022,
    "isbn": "1122334455"
}</code></pre>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "title": "Updated Book",
    "author": "Updated Author",
    "publicationYear": 2022,
    "isbn": "1122334455"
}</code></pre>

<h3 id="delete-book">Delete Book</h3>
<p><strong>Endpoint:</strong> <code>/api/books/{id}</code></p>
<p><strong>Method:</strong> DELETE</p>
<p><strong>Description:</strong> Deletes a book by its ID.</p>
<p><strong>Response:</strong></p>
<pre><code>HTTP Status 204 (No Content)</code></pre>

<h2 id="patrons">Patrons</h2>

<h3 id="get-all-patrons">Get All Patrons</h3>
<p><strong>Endpoint:</strong> <code>/api/patrons</code></p>
<p><strong>Method:</strong> GET</p>
<p><strong>Description:</strong> Retrieves a list of all patrons.</p>
<p><strong>Response:</strong></p>
<pre><code>[
    {
        "id": 1,
        "name": "John Doe",
        "email": "john.doe@example.com"
    }
]</code></pre>

<h3 id="get-patron-by-id">Get Patron by ID</h3>
<p><strong>Endpoint:</strong> <code>/api/patrons/{id}</code></p>
<p><strong>Method:</strong> GET</p>
<p><strong>Description:</strong> Retrieves a patron by its ID.</p>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
}</code></pre>

<h3 id="create-patron">Create Patron</h3>
<p><strong>Endpoint:</strong> <code>/api/patrons</code></p>
<p><strong>Method:</strong> POST</p>
<p><strong>Description:</strong> Creates a new patron.</p>
<p><strong>Request Body:</strong></p>
<pre><code>{
    "name": "Jane Doe",
    "email": "jane.doe@example.com"
}</code></pre>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 2,
    "name": "Jane Doe",
    "email": "jane.doe@example.com"
}</code></pre>

<h3 id="update-patron">Update Patron</h3>
<p><strong>Endpoint:</strong> <code>/api/patrons/{id}</code></p>
<p><strong>Method:</strong> PUT</p>
<p><strong>Description:</strong> Updates an existing patron.</p>
<p><strong>Request Body:</strong></p>
<pre><code>{
    "name": "John Smith",
    "email": "john.smith@example.com"
}</code></pre>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "name": "John Smith",
    "email": "john.smith@example.com"
}</code></pre>

<h3 id="delete-patron">Delete Patron</h3>
<p><strong>Endpoint:</strong> <code>/api/patrons/{id}</code></p>
<p><strong>Method:</strong> DELETE</p>
<p><strong>Description:</strong> Deletes a patron by its ID.</p>
<p><strong>Response:</strong></p>
<pre><code>HTTP Status 204 (No Content)</code></pre>

<h2 id="borrowing-records">Borrowing Records</h2>

<h3 id="borrow-book">Borrow Book</h3>
<p><strong>Endpoint:</strong> <code>/api/borrow/{bookId}/patron/{patronId}</code></p>
<p><strong>Method:</strong> POST</p>
<p><strong>Description:</strong> Borrows a book for a patron.</p>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "bookId": 1,
    "patronId": 1,
    "borrowDate": "2023-01-01",
    "returnDate": null
}</code></pre>

<h3 id="return-book">Return Book</h3>
<p><strong>Endpoint:</strong> <code>/api/return/{bookId}/patron/{patronId}</code></p>
<p><strong>Method:</strong> PUT</p>
<p><strong>Description:</strong> Returns a borrowed book.</p>
<p><strong>Response:</strong></p>
<pre><code>{
    "id": 1,
    "bookId": 1,
    "patronId": 1,
    "borrowDate": "2023-01-01",
    "returnDate": "2023-02-01"
}</code></pre>


<h2  id="Entity-Relationship-Diagram">Database Schema</h2>
<p>
    The Library Management System database schema includes the following entities and relationships:
</p>
<a href="https://ibb.co/VCLJQv1"><img src="https://i.ibb.co/Jpcmyz1/schema.png" alt="schema" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'></a><br />

<ul>
    <li><strong>Book:</strong> Represents the books in the library with attributes like <code>id</code>, <code>title</code>, <code>author</code>, <code>publicationYear</code>, and <code>isbn</code>.</li>
    <li><strong>BorrowingRecord:</strong> Represents the records of books borrowed by patrons with attributes like <code>id</code>, <code>borrowDate</code>, <code>returnDate</code>, <code>patronId</code>, and <code>bookId</code>.</li>
    <li><strong>Patron:</strong> Represents the library patrons with attributes like <code>id</code>, <code>name</code>, <code>email</code>, and <code>phone</code>.</li>
</ul>
<p>
    The relationships between these entities are as follows:
</p>
<ul>
    <li>A <strong>Book</strong> can have multiple <strong>BorrowingRecord</strong> entries (One-to-Many relationship).</li>
    <li>A <strong>BorrowingRecord</strong> is associated with one <strong>Book</strong> (Many-to-One relationship).</li>
    <li>A <strong>Patron</strong> can have multiple <strong>BorrowingRecord</strong> entries (One-to-Many relationship).</li>
    <li>A <strong>BorrowingRecord</strong> is associated with one <strong>Patron</strong> (Many-to-One relationship).</li>
</ul>


<h2 id="caching">Caching</h2>
<p>The application uses Spring Cache to improve performance by caching frequently accessed data.</p>

<h3>Configuration</h3>
<p>Enable caching in your main application class:</p>
<pre><code>@SpringBootApplication
@EnableCaching
public class LibraryManagementSystemApplication {
public static void main(String[] args) {
  SpringApplication.run(LibraryManagementSystemApplication.class, args);
}
}
</code></pre>

<h3>Usage</h3>
<p>Annotate the service methods you want to cache:</p>
<pre><code>@Service
public class BookService {

@Cacheable("books")
public List<Book> getAllBooks() {
  // method implementation
}

@Cacheable(value = "books", key = "#id")
public Book getBookById(Long id) {
  // method implementation
}
}
</code></pre>

<h2 id="aop-aspect-oriented-programming">AOP (Aspect-Oriented Programming)</h2>
<p>The application uses AOP for logging and performance monitoring.</p>

<h3>Configuration</h3>
<p>Enable AOP in your main application class:</p>
<pre><code>@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryManagementSystemApplication {
public static void main(String[] args) {
  SpringApplication.run(LibraryManagementSystemApplication.class, args);
}
}
</code></pre>

<h3>Usage</h3>
<p>Create an aspect for logging:</p>
<pre><code>@Aspect
@Component
public class LoggingAspect {

@Before("execution(* com.LibraryManagementSystem.demo.service.*.*(..))")
public void logBefore(JoinPoint joinPoint) {
  System.out.println("Executing method: " + joinPoint.getSignature().getName());
}

@After("execution(* com.LibraryManagementSystem.demo.service.*.*(..))")
public void logAfter(JoinPoint joinPoint) {
  System.out.println("Completed method: " + joinPoint.getSignature().getName());
}

@Around("execution(* com.LibraryManagementSystem.demo.service.*.*(..))")
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
  long start = System.currentTimeMillis();
  Object result = joinPoint.proceed();
  long elapsedTime = System.currentTimeMillis() - start;
  System.out.println("Method " + joinPoint.getSignature().getName() + " executed in " + elapsedTime + " ms");
  return result;
}
}
</code></pre>
<h2 id="running-tests">Running Tests</h2>
<p>
The project includes unit and integration tests. You can run them using Maven:
</p>
<pre><code>mvn test
</code></pre>
<p>Ensure that your test configuration (e.g., in-memory database, mock services) is correctly set up in the test resources.</p>

<h2 id="contribution">Contribution</h2>
<p>
Feel free to fork this repository and contribute by submitting pull requests. Make sure to follow the project's coding standards and include tests for new features.
</p>

<h2 id="contact">Contact</h2>
<p>
For any questions or support, please open an issue on GitHub or contact the repository owner at <a href="mailto:ahmadlotfygamersfield@gmail.com">ahmadlotfygamersfield@gmail.com</a>.
</p>

