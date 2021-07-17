package com.railiac.rest.database.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@Table(name = "roles", schema = "public")
public class Role {

    @Id
    @GeneratedValue
            (strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}