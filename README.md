# Pokémon API Application

## Description
This Spring Boot application provides:

1. **Basic Pokémon Information**
2. **Translated Pokémon Description** (Yoda or Shakespeare style) depending on the Pokémon

The application uses the following external APIs:
- [PokeAPI](https://pokeapi.co/) for retrieving Pokémon data
- [Fun Translations API](https://funtranslations.com/) for description translations

---

## Requirements
- Java 17+
- Gradle 8+

---

## Configuration
Main configuration is located in `src/main/resources/application.yml`

---

## Build and Run

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
Build the project:
`./gradlew clean build`
Run the application:
`java -jar build/libs/<project-name>-0.0.1-SNAPSHOT.jar`
The application will start on port 8080 by default (can be configured in application.yml).

Available Endpoints
1. Basic Pokémon Information
   `GET /pokemon/{name}`
   Parameters:
   * name: Pokémon name (e.g., mewtwo)
   Example with HTTPie:
   `http GET http://localhost:8080/pokemon/mewtwo`
   Response:
   ```
   {
      "name": "mewtwo",
      "isLegendary": true,
      "habitat": "cave",
      "description": "A powerful psychic Pokémon..."
   }
   ```
2. Pokémon with Translated Description
   `GET /pokemon/translated/{name}`
   Parameters:
   * name: Pokémon name
   Example with HTTPie:
   `http GET http://localhost:8080/pokemon/translated/mewtwo`
   Response:
   ```
   {
      "name": "mewtwo",
      "isLegendary": true,
      "habitat": "cave",
      "description": "Powerful psychic Pokémon, it is..."
   }
   ```
   The application chooses Yoda or Shakespeare translation based on the Pokémon’s habitat or isLegendary property.
