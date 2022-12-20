package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(value = "{tour_name: '?0'}")
    public Iterable<User> findByNameTour(String name);

    @Query(value = "{tour_name: ?0}", count = true)
    public Integer countUser(String tour_name);


}
