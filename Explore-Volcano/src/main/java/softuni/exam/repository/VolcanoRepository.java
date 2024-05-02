package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano,Long> {

    Optional<Volcano> findByName(String name);



        @Query("SELECT v FROM Volcano v WHERE v.isActive = true AND v.elevation > 3000 AND v.lastEruption IS NOT NULL ORDER BY v.elevation DESC")
        List<Volcano> findActiveVolcanoesWithElevationAbove3000AndLastEruption();

}
