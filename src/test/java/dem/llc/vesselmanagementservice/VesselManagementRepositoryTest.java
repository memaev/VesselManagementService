package dem.llc.vesselmanagementservice;

import dem.llc.vesselmanagementservice.model.Vessel;
import dem.llc.vesselmanagementservice.repository.VesselRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VesselManagementRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    @Autowired
    VesselRepository vesselRepository;

    @BeforeEach
    void setUp() {
        List<Vessel> vessels = List.of(
                new Vessel("Military", "Green"),
                new Vessel("Military", "Green"),
                new Vessel("Military", "Red"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black"),
                new Vessel("Military", "Black")
        );
        vesselRepository.saveAll(vessels);
    }

    @Test
    void databaseConnectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldReturnVesselsByColor() {
        List<Vessel> vessels = vesselRepository.findAllByColor("Green");
        assertThat(vessels).isNotNull();
    }
}
