# Vessel Management Service API

## API Overview
This API was created to optimize the process of vessel management, including creation, updating, deletion and retrieval with filters.

## Main API features
- Creating new vessel
- Updating vessel
- Deleting vessel
- Finding vessel by ID
- Retrieving all vessels by the following color filter

## Technical Specifies 
- Java 17
- Spring Boot 3.4.1
- PostgreSQL
- Docker Compose
- Test Containers test library
- Rest Assured test library

## How to use?
To build and run the Vessel Management Service perform the following stages:
  - Make sure that Java, Docker Engine and Docker Compose are installed on your machine.
  - Make sure that Docker Engine is running.
  - Add .env file in the root folder of the project with the following fields:
    - USERNAME = 
    - PASSWORD = 
    - DB_PORT = 
    - DB_NAME = 
    - LOCAL_PORT = 
    - DOCKER_EXIT_PORT = 
  - Enter the root folder of the project and run the file named "runProject.sh".
  - Wait for the project to build and run in docker composed containers (for backend app and database).
  - Enter [Swagger Generated Docs](http://localhost:8080/swagger-ui/index.html) to see endpoints spec.

## Project structure and explanation
- src
  - main
    - java/dem/llc/vesselmanagementservice
      - controller
        - VesselController.java - @RestController java class that implements endpoints for working with management service:
          - ResponseEntity<VesselDto> createVessel(@RequestBody CreateVesselRequestDto requestDto) - method for POST request to create new vessel.
          - ResponseEntity<VesselDto> updateVessel(@PathVariable UUID id, @RequestBody UpdateVesselRequestDto updatedVesselDto) - PUT endpoint method to update vessel.
          - void deleteVessel(@PathVariable UUID id) - DELETE endpoint method to delete vessel by id.
          - ResponseEntity<VesselDto> getVesselById(@PathVariable UUID id) - GET endpoint method to get vessel by ID.
          - List<VesselDto> getVessels(@RequestParam(required = false) String color) - GET endpoint method to get vessels by color filter.
      - dto
        - CreateVesselRequestDto.java - DTO class for receiving request for vessel creation. 
        - UpdateVesselRequestDto.java - DTO class for receiving request for vessel update.
        - VesselDto - DTO class representing existing vessel.
      - model
        - Vessel.java - @Entity model class that represents existing vessel in database.
      - repository
        - VesselRepository - interface that implements JpaRepository to work with database.
      - service
        - VesselService.java - @Service java class for VesselController to perform all logic and call database operations:
          - VesselDto createVessel(CreateVesselRequestDto requestDto) - create new vessel in database
          - VesselDto updateVessel(UUID id, UpdateVesselRequestDto updateRequestDto) - update existing vessel by id
          - boolean deleteById(UUID id) - delete vessel by id
          - VesselDto getVesselById(UUID vesselId) - get vessel by id from database
          - List<VesselDto> getVesselsByColor(String color) - get vessels filtered by color
          - List<VesselDto> getAllVessels() - get all vessels without any filters
    
  - test/java/dem/llc/vesselmanagementService
    - VesselManagementControllerTest.java - full REST API integration tests with Testcontainers and Rest Assured
    - VesselManagementRepositoryTest.java - database repository tests with TestContainers

- build.gradle.kts - Gradle Kotlin DSL build config file
- docker-compose.yml - Docker Compose configuration file to run containers and set up them.
- Dockerfile - Docker image build configuration file 
- init.sql - Database schema initializer file
- runProject.sh - Bash script file for rebuilding project and deploying to Docker Compose
