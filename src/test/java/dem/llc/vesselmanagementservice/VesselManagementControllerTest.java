package dem.llc.vesselmanagementservice;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.UpdateVesselRequestDto;
import dem.llc.vesselmanagementservice.model.Vessel;
import dem.llc.vesselmanagementservice.repository.VesselRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VesselManagementControllerTest {
    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    VesselRepository vesselRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        postgres.start();

        List<Vessel> vessels = List.of(
                new Vessel("Military", "Green"),
                new Vessel("Military", "Green"),
                new Vessel("Military", "Red"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black")
        );
        vesselRepository.deleteAll();
        vesselRepository.saveAll(vessels);

        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void testConnection() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    @DisplayName("Should get all 7 vessels")
    void shouldGetAllVessels() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/vessels")
                .then()
                .statusCode(200)
                .body(".", hasSize(7));
    }

    @Test
    @DisplayName("Should get all vessels with Black color. There are 4 of them")
    void shouldGetVesselsByColor() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/vessels?color=Black")
                .then()
                .statusCode(200)
                .body(".", hasSize(4));
    }

    @Test
    @DisplayName("Should get all vessels with Red color. There are 1 of them")
    void shouldGetVesselsByColor2() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/vessels?color=Red")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }

    @Test
    @DisplayName("Should save new vessel in database and after it get it by id")
    void shouldSaveNewVesselAndGetItById() {
        Vessel vessel = vesselRepository.save(new Vessel("Military", "Green"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/vessels/" + vessel.getId().toString())
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Should create new vessel by type and color")
    void shouldCreateNewVesselSuccessfully() {
        CreateVesselRequestDto requestDto = new CreateVesselRequestDto("Military", "White");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestDto)
                .post("api/v1/vessels")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Should fail creating new vessel because type is not valid")
    void shouldFailCreatingVessel1() {
        CreateVesselRequestDto requestDto = new CreateVesselRequestDto("normal", "White");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestDto)
                .post("api/v1/vessels")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should fail creating new vessel because type or color is empty")
    void shouldFailCreatingVessel2() {
        CreateVesselRequestDto requestDto = new CreateVesselRequestDto("", "White");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestDto)
                .post("api/v1/vessels")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should save new vessel in database, update it, and verify the changes")
    void shouldSaveNewVesselAndUpdateIt() {
        Vessel vessel = vesselRepository.save(new Vessel("Military", "Green"));

        UpdateVesselRequestDto updatedVessel = new UpdateVesselRequestDto("Cruise", "Blue");
        given()
                .contentType(ContentType.JSON)
                .body(updatedVessel)
                .when()
                .put("api/v1/vessels/" + vessel.getId())
                .then()
                .statusCode(200);

        Vessel retrievedVessel = vesselRepository.findById(vessel.getId())
                .orElseThrow(() -> new AssertionError("Vessel not found"));

        Assertions.assertEquals("Cruise", retrievedVessel.getType(),"Vessel type should be updated to 'Cruise'");
        Assertions.assertEquals("Blue", retrievedVessel.getColor(), "Vessel color should be updated to 'Blue'");
    }

    @Test
    @DisplayName("Should save new vessel in database and fail updating it because of invalid vessel type")
    void shouldSaveNewVesselAndFailUpdatingIt() {
        Vessel vessel = vesselRepository.save(new Vessel("Military", "Green"));

        UpdateVesselRequestDto updatedVessel = new UpdateVesselRequestDto("israeli", "Blue");
        given()
                .contentType(ContentType.JSON)
                .body(updatedVessel)
                .when()
                .put("api/v1/vessels/" + vessel.getId())
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should save new vessel in database and successfully delete it")
    void shouldSaveNewVesselAndDeleteIt() {
        Vessel vessel = vesselRepository.save(new Vessel("Military", "Green"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/v1/vessels/" + vessel.getId())
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Should save new vessel in database and fail to delete it because of invalid id")
    void shouldSaveNewVesselAndFailDeletingIt() {
        Vessel vessel = vesselRepository.save(new Vessel("Military", "Green"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/v1/vessels/" + UUID.randomUUID()/* random "invalid" id */)
                .then()
                .statusCode(404);
    }

}