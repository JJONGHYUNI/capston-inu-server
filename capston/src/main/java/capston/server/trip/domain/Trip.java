package capston.server.trip.domain;

import capston.server.photo.domain.Photo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private int code;

    private LocalDate departureDate;
    private LocalDate arrivalDate;

    @OneToMany(mappedBy = "trip")
    private List<Photo> photos= new ArrayList<>();

    public int updateCode(){
        int code = new Random().nextInt(9000)+1000;
        this.code=code;
        return code;
    }

    public void updateTitle(String title){
        this.title=title;
    }
    public void updateLocation(String Location){
        this.location=location;
    }
}
