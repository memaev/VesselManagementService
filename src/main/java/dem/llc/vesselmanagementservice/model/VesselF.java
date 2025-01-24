package dem.llc.vesselmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "vessel")
@NoArgsConstructor
public class VesselF {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;
}