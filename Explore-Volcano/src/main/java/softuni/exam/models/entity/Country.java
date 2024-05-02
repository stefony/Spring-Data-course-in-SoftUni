package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{

    //@Size(min = 3,max = 30)
    @Column(name="name",nullable = false, unique = true)
    private String name;

    //@Size(min = 3,max = 30)
    @Column(name = "capital")
    private String capital;

    @OneToMany(mappedBy = "country")
    private Set<Volcano> volcanoes;

    public Country() {
    }

    public Country(String name, String capital, Set<Volcano> volcanoes) {
        this.name = name;
        this.capital = capital;
        this.volcanoes = volcanoes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Set<Volcano> getVolcanoes() {
        return volcanoes;
    }

    public void setVolcanoes(Set<Volcano> volcanoes) {
        this.volcanoes = volcanoes;
    }
}
