# DeliverYey API

DeliverYey is an API designed for making and managing online orders for Cebu Institute of Technology University's canteen. The API is built with Spring Boot and Dockerized for easy deployment. It supports JWT authentication and defines two user roles: **students** and **canteen staff**.

## Features

- **Online Ordering**: Allows students to place orders online.
- **Role-Based Access**: Supports two roles:
  - **Students**: Can place and view their orders.
  - **Canteen Staff**: Can manage orders and view order details.
- **JWT Authentication**: Secures endpoints and provides role-based access control.
- **Dockerized**: Easy deployment using Docker.

## Getting Started

### Prerequisites

- **Java 17**: Ensure you have Java 17 installed.
- **Docker**: For running the application using Docker.

### Running Locally

1. **Clone the Repository**:

    ```sh
    git clone https://github.com/jivstuban/DeliverYey.git
    cd DeliverYey
    ```

2. **Build the Application**:

    ```sh
    mvn clean install
    ```

3. **Run the Application**:

    ```sh
    mvn spring-boot:run
    ```

4. **Access the API**: Open your browser or use a tool like Postman to access `http://localhost:8080`.

### Running with Docker

1. **Build the Docker Image**:

    ```sh
    docker build -t deliver-yey .
    ```

2. **Run the Docker Container**:

    ```sh
    docker run -p 8080:8080 deliver-yey
    ```

3. **Access the API**: Open your browser or use a tool like Postman to access `http://localhost:8080`.

## Dependencies

- **Spring Boot**: Provides the core framework.
- **Spring Boot Data JPA**: For database interactions.
- **Spring Boot Web**: For building web applications.
- **Spring Boot Security**: For JWT authentication.
- **JJWT**: For JWT handling.
- **MySQL Connector/J**: MySQL database driver.
- **Lombok**: For reducing boilerplate code.
- **Spring Boot Validation**: For input validation.

## Configuration

- **Database**: Configure your MySQL database settings in `application.properties`.
- **JWT Secret**: Configure JWT secret in `application.properties`.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Feel free to fork the repository, make changes, and submit pull requests.

## Contact

For any questions, please contact [jivtuban14@gmail.com](mailto:jivtuban14@gmail.com).

