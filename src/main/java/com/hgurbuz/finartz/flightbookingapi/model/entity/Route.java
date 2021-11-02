package com.hgurbuz.finartz.flightbookingapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String arrivalCity;
    private String departureCity;

    @OneToMany(mappedBy = "route")
    private List<Flight> flightList;
}
