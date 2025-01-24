package dem.llc.vesselmanagementservice;

import org.springframework.boot.SpringApplication;

public class TestVesselManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(VesselManagementServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
