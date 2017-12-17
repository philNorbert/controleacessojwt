package br.edu.cesar.philippe.accesscontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ToString(exclude = "roles")
@NoArgsConstructor
@Table(name = "AUTHORITY")
public class Authority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50, unique = true)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "AUTHORITY_FEATURES",
            joinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "FEATURE_ID", referencedColumnName = "ID")})
    private List<Feature> features;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Role> roles;
    
    public Authority(String name) {
        this.name = name;
    }
}