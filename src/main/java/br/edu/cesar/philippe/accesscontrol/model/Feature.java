package br.edu.cesar.philippe.accesscontrol.model;

import br.edu.cesar.philippe.accesscontrol.model.enums.FeatureType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "FEATURE")
public class Feature {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feature_seq")
    @SequenceGenerator(name = "feature_seq", sequenceName = "feature_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "FEATURE_NAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;
    
    @Column(name = "FEATURE_TYPE", length = 50, unique = true)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private FeatureType featureType;
    
    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<System> systems;

    public Feature(String name, FeatureType featureType) {
        this.name = name;
        this.featureType = featureType;
    }
    
}
