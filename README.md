# Local Disaster Reporting System

##  Group Members
* Ernest Joaquin Abella
* Earl Abzel Abesia
* Vence Delos Reyes
* Omar Macapaso
* John Kurt Realista

##  Project Description
Many communities experience disasters such as floods, fires, and landslides but currently lack a centralized system for reporting and tracking these events. Reliance on social media or word-of-mouth is often unorganized, unverified, and slow.

This desktop application provides a local community disaster monitoring platform. It empowers citizens to report incidents with precise geographic coordinates and photo evidence, equips emergency responders with real-time tracking tools, and gives administrators the ability to manage user roles and data. Built with an **offline-first architecture**, the system ensures that incident reporting and map viewing remain functional even during network outages.

##  Key Features
* **Interactive Map Integration:** Uses Leaflet.js embedded in a JavaFX WebView to display incident pins. Includes a custom `OfflineTileServer` to cache map tiles and render maps without an internet connection.
* **Smart Incident Reporting:** Users can drop pins on the map to auto-fill geographic coordinates via a `HybridGeocoder` (which falls back to a local SQLite cache if offline). Supports image uploads with automatic Base64 compression.
* **Role-Based Workflows:** * **Reporters:** Can submit incidents and build a "Trust Score."
    * **Responders:** Tied to specific agencies; can update incident statuses (Active, Monitoring, Resolved).
    * **Admins:** Can approve/reject role upgrade requests, reset passwords, and manage all users.
* **Offline-First Synchronization:** The `SyncManager` utilizes a background daemon thread to queue reports locally (`offline_cache.dat`) when offline, automatically pushing them to the MySQL database once the connection is restored.
* **Dynamic Dashboard & Analytics:** View all incidents in a responsive, modern, filterable table. Displays real-time summary statistics for active versus resolved incidents.

## 🛠 Technologies Used
* **Backend:** Java 21
* **Frontend:** JavaFX (FXML, CSS) + `ModernDialog` custom UI utility
* **Database:** MySQL (Primary Database) & SQLite (Offline Geocoding Cache)
* **Build Tool:** Maven
* **Mapping:** Leaflet.js, OpenStreetMap API, CartoDB (Tiles)

---

## 
Capstone Evaluation Criteria Mapping

This section outlines how the Local Disaster Reporting System implements the required CSIT228 Capstone Evaluation Criteria:

### 1. Object-Oriented Programming Principles (15%)
* **Abstraction & Inheritance:** The system uses a centralized abstract `User` class containing base properties (userID, username, password). This is extended by specific role classes: `Admin`, `Responder`, and `Reporter`.
* **Polymorphism:** The subclasses override abstract methods such as `getRoleName()`, `canUpdateIncidentStatus()`, and `canManageUsers()`. This allows the UI controllers (like `MainController`) to dynamically adjust permissions and visibility without hard-coded string checks, treating all logged-in accounts polymorphically as `User` objects.
* **Encapsulation:** All model classes (`Incident`, `Request`, `User`) use private fields accessed strictly through public getters and setters to protect data integrity.

### 2. Java Generics (10%)
* Generics are utilized extensively throughout the application, particularly with Java Collections (`List<Incident>`, `List<User>`, `Map<String, Long>`) for robust data handling.
* JavaFX UI components heavily rely on generics for type safety, such as `TableView<Incident>`, `TableColumn<User, String>`, `ComboBox<String>`, and `ObservableList<Request>`, ensuring that the UI only accepts and displays the correct object types.

### 3. Multithreading and Concurrency (10%)
* **Background Processing:** The application uses Java `Thread` implementations to prevent the JavaFX Main Application Thread from freezing during heavy operations.
    * The `HybridGeocoder` utilizes a background thread to fetch location data from the OpenStreetMap API.
    * The `SyncManager` runs a continuous background daemon thread (`backgroundSyncThread`) that wakes up every 15 seconds to check for internet connectivity and sync locally cached reports to the cloud.
* **Concurrency Handling:** `Platform.runLater()` is strictly used when background threads need to update the UI (e.g., displaying the fetched address or showing a success dialog), preventing `IllegalStateException` and race conditions.

### 4. Graphical User Interface (15%)
* **JavaFX & FXML:** The GUI is entirely built using JavaFX, with layouts defined in FXML for strict separation of design and logic.
* **Event-Driven Programming:** Action events, mouse clicks, and property listeners are implemented across all controllers (e.g., handling logins, double-click listeners on table rows to trigger map flyovers).
* **Modern Design:** The interface uses custom CSS for a clean, modern aesthetic. A custom `ModernDialog` utility class was built from scratch to replace native, outdated JavaFX Alerts with sleek, transparent, drop-shadowed modal windows.

### 5. Database Connectivity (15%)
* **Dual-Database System (JDBC):** The system connects to a primary **MySQL** database (`disasterreport_db`) for central data storage and an embedded **SQLite** database (`geocache.db`) for offline geographic coordinate caching.
* **CRUD Operations:** The `DatabaseManager` executes full Create, Read, Update, and Delete operations using `PreparedStatement` to prevent SQL injection. It handles saving incidents, updating statuses, managing users, and approving role requests.

### 6. Unified Modeling Language (UML) (10%)
* Comprehensive UML diagrams, including a **Class Diagram** (detailing attributes, methods, and relationships) and a **Use Case Diagram** (mapping actor interactions with the system), are included in the `/DIAGRAMS/` directory of the repository.

### 7. Design Patterns (10%)
* **Singleton Pattern:** Applied to both the `DatabaseManager` and the `SyncManager`.
    * *Justification:* Ensures that only a single, shared database connection instance and a single background synchronization thread exist throughout the application's lifecycle, optimizing memory and preventing connection leaks.
* **Factory Method Pattern (Behavioral):** Implemented within `DatabaseManager.validateUser()`. Based on the database query result, it dynamically instantiates and returns the correct subclass (`Admin`, `Reporter`, or `Responder`), abstracting the instantiation logic away from the Login controller.

### 8. Code Quality and Documentation (15%)
* **MVC Architecture:** The codebase is strictly organized into `model`, `controller`, and `util` packages, ensuring modularity and scalability.
* **Coding Standards:** Variables and methods follow standard Java camelCase naming conventions. Complex logic (like the image Base64 compression engine and the offline Leaflet.js tile server) is documented with inline comments explaining the methodology.

