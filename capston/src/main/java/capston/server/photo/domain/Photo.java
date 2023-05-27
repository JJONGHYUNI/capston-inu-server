package capston.server.photo.domain;


import capston.server.common.BaseEntity;
import capston.server.trip.domain.Trip;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;
    private String photoUrl;

    private boolean isMainPhoto;

    public void updateMain(){
        this.isMainPhoto=true;
    }
}
