package capston.server.trip.domain;

import capston.server.photo.domain.Photo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "TRIP")
@AllArgsConstructor
public class Trip {

    @Id
    @Column(name = "trip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String location;

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;

    @OneToMany(mappedBy = "trip")
    private List<Photo> photos= new ArrayList<>();
}
