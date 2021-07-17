package com.railiac.rest.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "filter_role_configs", schema = "public")
public class FilterRoleConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String urlPattern;

    private boolean excludes;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "filter_page_roles",
            joinColumns = @JoinColumn(
                    name = "filter_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
