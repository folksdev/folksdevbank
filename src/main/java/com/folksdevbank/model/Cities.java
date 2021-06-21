package com.folksdevbank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@EqualsAndHashCode
@RedisHash("cities")
@Entity
public class Cities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer plateCode;
    private String name;
}
