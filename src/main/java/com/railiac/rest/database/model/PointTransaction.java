package com.railiac.rest.database.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "point_transaction", schema = "public")
public class PointTransaction {

    @Id
    @GeneratedValue
            (strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private Long amount;
    private String description;

}