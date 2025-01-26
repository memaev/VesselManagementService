package dem.llc.vesselmanagementservice.repository;

import dem.llc.vesselmanagementservice.model.Vessel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, UUID> {
    List<Vessel> findAllByColor(String color);
}
