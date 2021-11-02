package com.hgurbuz.finartz.flightbookingapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creditCard;
    private int numOfSeats;
    private String passengerName;
    private String passengerSurname;
    private float totalPrice;
    @ManyToOne(fetch=FetchType.LAZY)
    private Flight flight;

}
