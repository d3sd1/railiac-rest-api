package com.railiac.rest.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter(onMethod_ = @JsonIgnore)
@Setter(onMethod_ = @JsonIgnore)
@ToString
@Entity
@Table(name = "tickets", schema = "public")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ticketName;
    //TODO <- add fields as needed

}
