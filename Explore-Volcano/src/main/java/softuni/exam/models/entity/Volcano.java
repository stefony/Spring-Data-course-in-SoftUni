package softuni.exam.models.entity;

import softuni.exam.models.enums.VolcanoType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity{

    //@Size(min = 2,max = 30)
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
//    @Positive
    private Integer elevation;

    @Enumerated(EnumType.STRING)
    @Column(name = "volcano_type")
    private VolcanoType volcanoType;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(name = "last_eruption")
    private LocalDate lastEruption;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "volcano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Volcanologist> volcanologists;

    public Volcano() {
    }

    public Volcano(String name, Integer elevation, VolcanoType volcanoType, Boolean isActive, LocalDate lastEruption, Country country, Set<Volcanologist> volcanologists) {
        this.name = name;
        this.elevation = elevation;
        this.volcanoType = volcanoType;
        this.isActive = isActive;
        this.lastEruption = lastEruption;
        this.country = country;
        this.volcanologists = volcanologists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Volcanologist> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(Set<Volcanologist> volcanologists) {
        this.volcanologists = volcanologists;
    }
}
