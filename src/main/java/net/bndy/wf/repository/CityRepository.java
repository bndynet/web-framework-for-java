package net.bndy.wf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.domain.*;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value="select c.* from city c where c.country_id=:countryId", nativeQuery=true)
    List<City> findByCountryId(@Param("countryId") int countryId);
}