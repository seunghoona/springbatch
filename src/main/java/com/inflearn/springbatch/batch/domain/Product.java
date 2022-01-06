package com.inflearn.springbatch.batch.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String type;

}
