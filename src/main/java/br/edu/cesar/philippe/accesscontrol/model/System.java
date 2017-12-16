package br.edu.cesar.philippe.accesscontrol.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "SYSTEM")
public class System {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_seq")
    @SequenceGenerator(name = "system_seq", sequenceName = "system_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "SYS_NAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SYSTEM_FEATURE",
            joinColumns = {@JoinColumn(name = "SYSTEM_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "FEATURE_ID", referencedColumnName = "ID")})
    private List<Feature> features;
    
}
