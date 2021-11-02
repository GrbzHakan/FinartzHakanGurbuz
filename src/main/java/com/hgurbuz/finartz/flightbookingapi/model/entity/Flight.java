package com.hgurbuz.finartz.flightbookingapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    private Company company;

    @ManyToOne(fetch=FetchType.LAZY)
    private Route route;

    @OneToMany(mappedBy = "flight")
    private List<Ticket> ticket;

    private int maxCapacity;
    private int remainCapacity = maxCapacity;
    private float price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date arriveTime;


}
