package com.example.tourservice.repository;


import com.example.tourservice.model.Tour;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends CrudRepository<Tour, String> {
    @Query(value = "{name: '?0'}")
    public Tour findByName (String name);

    @Query(value = "{province: '?0'}")
    public List<Tour> findByProvince (String province);

}
