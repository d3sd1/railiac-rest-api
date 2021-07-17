package com.railiac.rest.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter()
@Setter()
@Builder
@ToString
@Entity
@Table(name = "users", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String surnames;

    private String photo;
    private String selector;
    private String validator;
    private String referrerCode;
    private boolean enabled;
    private boolean blocked;
    private boolean needsOnboard = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToOne
    private Language language;

    private String emailCode;

    private Long points = 0L;

}
