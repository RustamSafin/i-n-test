package com.test.numbergenerator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Number")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarNumber {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer serialNumber;

    private Integer registryNumber;

}
