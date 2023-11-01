package capston.server.trip.domain;

import capston.server.photo.domain.Photo;
import capston.server.review.domain.Review;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String mainPhoto;

    private boolean completed = false;

    @Column(name = "latitude", precision = 13, scale = 10)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 13, scale = 10)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "trip")
    private List<Photo> photos= new ArrayList<>();

    @OneToMany(mappedBy = "trip")
    private List<Review> reviews = new ArrayList<>();

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
    public void updateMainPhoto(String url){this.mainPhoto=url;}

    public void setCompleted(){
        this.completed = true;
    }
}
