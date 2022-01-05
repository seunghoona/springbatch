package com.inflearn.springbatch.batch.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Prodcut {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String type;
}
