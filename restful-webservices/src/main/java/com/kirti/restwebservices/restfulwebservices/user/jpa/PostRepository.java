package com.kirti.restwebservices.restfulwebservices.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirti.restwebservices.restfulwebservices.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{


}
