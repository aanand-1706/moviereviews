package com.aanand.demo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"movie_id", "seat_no"}
        )
)
public class Seat extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //to specify primary key
    private UUID id;

    public Seat() {
    }

    public Seat(Integer movieId, Integer seatNo, boolean isBooked) {
        this.movieId = movieId;
        this.seatNo = seatNo;
        this.isBooked = isBooked;
    }

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "seat_no")
    private Integer seatNo;

    @Column(name = "is_booked")
    private boolean isBooked;

    public UUID getId() {
        return id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public boolean isBooked() {
        return isBooked;
    }
}
