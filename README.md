# JournalApp - A Spring Boot Application

Welcome to **JournalApp**, a comprehensive journaling application built with the Spring ecosystem. This project demonstrates a modern, robust backend architecture featuring secure user authentication, asynchronous event handling with Apache Kafka, caching with Redis, and detailed API documentation with OpenAPI.

This application is designed not just for functionality but also as a showcase of best practices in building scalable and maintainable enterprise-level Java applications.

---

# Key Features :

-   **Secure Authentication**: End-to-end security with **Spring Security** and **JSON Web Tokens (JWT)** for stateless, secure API access.
-   **User & Role Management**: Clear separation of roles (USER, ADMIN) with distinct permissions. Admins can manage user roles.
-   **Full CRUD Functionality**: Authenticated users can create, read, update, and delete their personal journal entries.
-   **Asynchronous Processing**: Integrated with **Apache Kafka** to process events (like new journal entries) asynchronously, making the system more resilient and scalable.
-   **High-Performance Caching**: Utilizes **Redis** for caching frequently accessed data, reducing database load and improving response times.
-   **Scheduled Sentiment Analysis**: A scheduled task runs periodically to analyze the sentiment of new journal entries and updates user profiles accordingly.
-   **Email Notifications**: Users receive periodic email summaries or notifications powered by **Spring Mail**.
-   **Inspirational Quotes**: Fetches random inspirational quotes from an external API to display to users.
-   **Interactive API Documentation**: Comes with a built-in **Swagger UI** for easy exploration and testing of all available API endpoints.

---

# Tech Stack & Tools

This project is built with a modern, production-ready technology stack:

| Category | Technologies |
| :--- | :--- |
| **Language** | ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) |
| **Framework** | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![Spring Data MongoDB](https://img.shields.io/badge/Spring_Data-6DB33F?style=for-the-badge&logo=spring&logoColor=white) |
| **Database** | ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white) |
| **Caching** | ![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white) |
| **Messaging** | ![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white) |
| **API & Docs** | ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white) ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) |
| **Build Tool** | ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) |

---

# Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

Make sure you have the following installed on your machine:
*   Java (JDK 17 or newer)
*   Maven
*   MongoDB
*   Redis
*   Apache Kafka & Zookeeper

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/anshul9982/journalapp.git
    cd journalapp
    ```

2.  **Configure the application:**
    Open `src/main/resources/application.properties` and update the connection details for your local environment (MongoDB, Redis, Kafka, JWT Secret).

    ```properties
    # MongoDB Configuration
    spring.data.mongodb.uri=mongodb://localhost:27017/journal-db

    # Redis Configuration
    spring.data.redis.host=localhost
    spring.data.redis.port=6379

    # Kafka Configuration
    spring.kafka.bootstrap-servers=localhost:9092
    spring.kafka.consumer.group-id=journal-group
    spring.kafka.template.default-topic=journal-entries

    # JWT Secret Key
    jwt.secret=YOUR_SUPER_SECRET_KEY_FOR_JWT_GENERATION_WHICH_IS_LONG_ENOUGH

    # External Quote API
    quote.api.url=https://api.quotable.io/random
    ```

3.  **Build and run the application:** 
    ```sh
    mvn spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

---

## 📖 API Documentation :

The API is documented using **OpenAPI 3**. Once the application is running, you can access the interactive Swagger UI to explore and test all the endpoints.

-   **Swagger UI URL**: http://localhost:8080/swagger-ui/index.html

### Main API Endpoints :

-   `POST /public/sign-up`: Register a new user.
-   `POST /public/login`: Authenticate and receive a JWT.
-   `GET /journal`: Get all journal entries for the authenticated user.
-   `POST /journal`: Create a new journal entry.
-   `GET /journal/id/{myId}`: Get a specific journal entry by its ID.
-   `PUT /journal/id/{myId}`: Update an existing journal entry.
-   `DELETE /journal/id/{myId}`: Delete a journal entry.
-   `PUT /user/`: Update the authenticated user's profile.
-   `DELETE /user/`: Delete the authenticated user's account.
-   `GET /admin/all-users`: (Admin only) Get a list of all users.
-   `PUT /admin/make-admin`: (Admin only) Grant admin privileges to a user.

---

## Contributing :

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---

Project Link: https://github.com/anshul9982/journalapp
