# Installationsguide för Projektet

Denna guide beskriver hur du klonar projektet från GitHub och installerar det med Docker Compose. 

## Förutsättningar

Innan du installerar projektet, se till att du har följande installerat på din maskin:

- **Java 23**: Kontrollera att du har Java 23 installerat genom att köra `java -version` i terminalen.
- **Docker**: Ladda ner och installera Docker från Docker's officiella webbplats https://www.docker.com/get-started.
- **Docker Compose**: Detta ingår vanligtvis med Docker Desktop.

## Klona Projektet från GitHub

1. Öppna terminalen och klona projektet till din lokala maskin:

   ```bash
   git clone https://github.com/Daymaare/JakEE2025
   cd JakEE2025
   ```

## Installera och Köra med Docker Compose

1. Se till att du befinner dig i projektets rotkatalog där `docker-compose.yml`-filen finns.

2. Starta applikationen med Docker Compose:

   ```bash
   docker-compose up --build
   ```

   Detta kommando bygger och startar alla tjänster som definieras i `docker-compose.yml`.

3. När alla tjänster har startats, kan du komma åt applikationen genom att 
   öppna din webbläsare och navigera till `http://localhost:8080`.

## Använda Applikationens Funktioner

### API-Åtkomst

Applikationen erbjuder flera API-endpoints för att interagera med speldata. Här är några exempel:

- **Hämta alla spel**: 
  - Endpoint: `GET /api/games`
  - Beskrivning: Hämtar en lista över alla spel.

- **Hämta ett specifikt spel**:
  - Endpoint: `GET /api/games/{id}`
  - Beskrivning: Hämtar detaljer för ett specifikt spel baserat på dess ID.

- **Skapa ett nytt spel**:
  - Endpoint: `POST /api/games`
  - Beskrivning: Skapar ett nytt spel med de angivna detaljerna.
  - Exempel på JSON-begäran:
    ```json
    {
      "title": "Nytt Spel",
      "developer": "Spelutvecklare",
      "description": "Beskrivning av spelet",
      "release_date": "2024-01-01",
      "upc": "123456789012"
    }
    ```

- **Uppdatera ett spel**:
  - Endpoint: `PATCH /api/games/{id}`
  - Beskrivning: Uppdaterar detaljerna för ett befintligt spel.

## Felsökning

- Kontrollera att Docker och Docker Compose körs korrekt.
- Om du stöter på problem, kontrollera loggarna för mer information:

  ```bash
  docker-compose logs
  ```

## Sammanfattning

Du har nu klonat och installerat projektet med Docker Compose och lärt dig hur du använder dess funktioner. 
Se till att alla förutsättningar är uppfyllda för att säkerställa en smidig installation och användning.
