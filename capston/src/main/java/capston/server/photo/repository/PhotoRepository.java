package capston.server.photo.repository;

import capston.server.photo.domain.Photo;
import capston.server.trip.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findAllByTripId(Long tripId);
    Page<Photo> findAllByTrip(Trip trip, Pageable pageable);
}
