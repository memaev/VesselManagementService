package dem.llc.vesselmanagementservice.repository;

import dem.llc.vesselmanagementservice.model.VesselF;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface VesselRepository extends JpaRepository<VesselF, UUID> {
    List<VesselF> findAllByColor(String color);
}
