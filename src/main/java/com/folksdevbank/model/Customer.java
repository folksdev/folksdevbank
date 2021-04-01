package com.folksdevbank.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    private String id;

    private String name;
    private Integer dateOfBirth;
    private City city;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> address;
}
