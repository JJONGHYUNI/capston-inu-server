package capston.server.photo.repository;

import capston.server.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findAllByTripId(Long tripId);
}
