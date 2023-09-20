package capston.server.trip.dto;

import capston.server.common.DateUtils;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.sql.Time;
import java.time.format.DateTimeFormatter;

@Data
public class TripListResponseDto {
    private String title;
    private String arrivalDate;
    private String departureDate;
    private String mainImgUrl;
    private Long tripId;

    public TripListResponseDto(Trip trip){
        this.title=trip.getTitle();
        this.arrivalDate= DateUtils.formatMonthDay(trip.getArrivalDate());
        this.departureDate=DateUtils.formatMonthDay(trip.getDepartureDate());
        this.mainImgUrl=trip.getMainPhoto();
        this.tripId=trip.getId();
    }
}
