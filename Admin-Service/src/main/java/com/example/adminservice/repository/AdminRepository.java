package com.example.adminservice.repository;

import com.example.adminservice.model.Admin;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {
    @Query(value = "{username: '?0'}")
    public Admin findByUsername(String username);
}
