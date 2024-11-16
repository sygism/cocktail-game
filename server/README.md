# Cocktail Game
<div align="center">
    <img src="../site/src/assets/icons/cocktail.svg" width="150" height="150" alt="Cocktail img">
</div>

## Technologies Used
- **Java 21**
- **Spring Boot 3.3.1**
- **Gradle 7.5**

## Setup Instructions

### Prerequisites
- Java \>=21
- Gradle 7.5 or newer

### Building the Project
1. Clone the repository:
    ```sh
    git clone https://github.com/sygism/cocktail-game.git
    ```
2. Assemble the project:
    ```sh
    ./gradlew assemble
    ```

### Running the Application
#### Before running
Make sure that the [`application.properties`](/src/main/resources/application.properties) file is properly configured.
___
Required parameters:
- server.port (default 5)
- env.constants.max_guesses (default 5)
- env.constants.max_random_cocktail_retries (default 100)
- external.cocktails_api.base_path (default https://www.thecocktaildb.com/)
- external.frontend.url (default http://localhost:9001) Note: frontend URL port is set to 9001 by default in <i>dev</i> task
___
1. Start application:
    ```sh
    ./gradlew bootRun
    ```
### Running Tests
To run the tests:
```sh
./gradlew test
```

### API Usage
The game uses the public API from <a href="https://www.thecocktaildb.com/">TheCocktailDB</a> to fetch random cocktails.