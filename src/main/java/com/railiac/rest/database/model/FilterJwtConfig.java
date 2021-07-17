package com.railiac.rest.database.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "filter_jwt_configs", schema = "public")
public class FilterJwtConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String urlPattern;

    private boolean excludes;

}
