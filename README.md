# Game Application Installation and Usage Guide

This guide provides instructions on how to clone, set up, and use the Game Application using Docker Compose. 
It also includes details on how to interact with the API endpoints provided by the application.

## Prerequisites

Before installing the project, ensure you have the following installed on your machine:

- **Java 23**: Verify that you have Java 23 installed by running `java -version` in your terminal.
- **Docker**: Download and install Docker from [Docker's official website](https://www.docker.com/get-started).
- **Docker Compose**: This is typically included with Docker Desktop.

## Clone the Project from GitHub

1. Open your terminal and clone the project to your local machine:

   ```bash
   git clone https://github.com/Daymaare/JakEE2025
   cd JakEE2025
   ```

## Install and Run with Docker Compose

1. Ensure you are in the root directory of the project where the `docker-compose.yml`-file is located.

2. Start the application with Docker Compose:

   ```bash
   docker-compose --profile production up
   ```

   This command builds and starts all services defined in the `docker-compose.yml`.

3. Once all services have started, you can access the application by 
   opening your web browser and navigating to `http://localhost:8080`.

## Using the Application's Features

### API-Endpoints

The application offers several API endpoints to interact with game data. Here are the available endpoints:

- **Get all games**: 
  - Endpoint: `GET /api/games`
  - Description: Retrieves a list of all games.

- **Get a specific game**:
  - Endpoint: `GET /api/games/{id}`
  - Description: Retrieves details for a specific game based on its ID.

- **Create a new game**:
  - Endpoint: `POST /api/games`
  - Description: Creates a new game with the specified details.
  - Example JSON request:
    ```json
    {
      "title": "Nytt Spel",
      "developer": "Spelutvecklare",
      "description": "Beskrivning av spelet",
      "releaseDate": "2024-01-01",
      "upc": "123456789012"
    }
    ```

- **Update a game**:
  - Endpoint: `PATCH /api/games/{id}`
  - Description: Updates the details of an existing game.

- **Search for games by title**:
  - Endpoint: `GET /api/games/title/{title}`
  - Description: Searches for games based on the title.

- **Search for games by developer**:
  - Endpoint: `GET /api/games/developer/{developer}`
  - Description: Searches for games based on the developer.

## Troubleshooting

- Ensure that Docker and Docker Compose are running correctly.
- If you encounter problems, check the logs for more information:

  ```bash
  docker-compose logs
  ```

## Summary

You have now cloned and installed the project with Docker Compose and learned how to use its features. 
Ensure that all prerequisites are met to ensure a smooth installation and usage.
