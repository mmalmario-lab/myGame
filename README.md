# My Game

A Java game project built with Maven.

## Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher

## Building the Project

```bash
mvn clean compile
```

## Running the Project

```bash
mvn exec:java -Dexec.mainClass="com.example.Main"
```

Or compile and run the JAR:

```bash
mvn clean package
java -jar target/my-game-1.0.0.jar
```

## Running Tests

```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/example/
│           └── Main.java
└── test/
    └── java/
        └── com/example/
            └── MainTest.java
```

## License

This project is open source and available under the MIT License.
