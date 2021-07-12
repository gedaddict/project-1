package com.example.project1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.project1.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {

}
