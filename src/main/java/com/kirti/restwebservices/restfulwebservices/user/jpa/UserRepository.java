package com.kirti.restwebservices.restfulwebservices.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirti.restwebservices.restfulwebservices.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
