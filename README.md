# Pro Network Endorsement System

This project aims to improve endorsement relevance on professional networking platforms by analyzing user profiles, skills, and relationships. The system evaluates the relevance of endorsements by considering factors such as years of experience, expertise, and coworker relationships.

## Cloning the Project

To clone the project repository, run the following command:

```bash
git clone <repository-url>
```

## Running the Project

1. Ensure you have Java JDK 17 and Maven installed on your system.
2. Navigate to the project directory.
3. Run the following Maven command to build the project:

```bash
mvn clean install
```

4. Once the build is successful, run the following command to start the application:

```bash
 mvn spring-boot:run
```

## Testing with Postman

1. Ensure the application is running.
2. Open Postman.
3. Use the provided APIs to register a user, post an endorsement, and retrieve endorsements for a user.
4. Sample cURL for registering two users, posting an endorsement and getting the endorsement is provided below:
 
   # Add first user
curl --location 'http://localhost:8080/api/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "harry.brown@example.com",
    "name": "Harry Brown",
    "skills": [
        {
            "name": "Java",
            "experience": 4
        },
        {
            "name": "Spring Boot",
            "experience": 3
        }
    ]
}
'

# Add second user
curl --location 'http://localhost:8080/api/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "john.doe@example.com",
    "name": "John Doe",
    "skills": [
        {
            "name": "Java",
            "experience": 8
        },
        {
            "name": "Spring Boot",
            "experience": 6
        }
    ]
}
'

# Post endorsement
curl --location 'http://localhost:8080/api/user/postEndorsement' \
--header 'Content-Type: application/json' \
--data '{
  "reviewee": {
    "id": 2
  },
  "reviewer": {
    "id": 1
  },
  "skill": "Marketing",
  "actualScore": 8,
  "coworker": 0
}
'

# Get endorsements
curl --location 'http://localhost:8080/api/user/getEndorsement'

   

## H2 Database

This project uses the H2 in-memory database for data storage. H2 is a lightweight, fast, and embeddable database that allows easy setup and testing. It's ideal for development and testing purposes, providing an efficient way to manage relational data without the need for a separate database server. The in-memory nature of H2 allows for quick data access and manipulation, making it suitable for this project's requirements.
Please note that H2 database can only retain data until the session is active. If you stop the application or restart it, you will have to re-register users and post endorsements, as the previous data will be lost.

## To access H2 console:

1. URL-> http://localhost:8080/h2-console
2. Driver class: org.h2.Driver
3. JDBC URL: org.h2.Driver
4. Username: sa
5. Password: 

## Screenshots
![endorsementTable](https://github.com/KPrasad-30/Endorsement/assets/37859147/77f7621c-f58b-4ada-839d-bc734567e590)
![getEndorsement](https://github.com/KPrasad-30/Endorsement/assets/37859147/b8cd58db-b6f3-42a0-b635-c8f8a1183834)
![h2screen](https://github.com/KPrasad-30/Endorsement/assets/37859147/b99659b4-37ed-4a3b-967d-64f9a4b9fc51)
![postEndorsement](https://github.com/KPrasad-30/Endorsement/assets/37859147/2b795ed8-3bfc-4758-9614-a723dbc255f4)
![registerUser](https://github.com/KPrasad-30/Endorsement/assets/37859147/30998266-8c06-4b49-8a6e-93fa631c9b55)
![skillTable](https://github.com/KPrasad-30/Endorsement/assets/37859147/15e3d2f0-1e26-4bc8-9769-329312e9a2b4)
![usertable](https://github.com/KPrasad-30/Endorsement/assets/37859147/1a704791-38da-4bbe-b1da-55e86b896638)
