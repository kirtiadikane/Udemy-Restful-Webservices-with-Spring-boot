package com.kirti.restwebservices.restfulwebservices.user.jpa;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kirti.restwebservices.restfulwebservices.user.Post;
import com.kirti.restwebservices.restfulwebservices.user.User;
import com.kirti.restwebservices.restfulwebservices.user.UserDaoService;
import com.kirti.restwebservices.restfulwebservices.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {
	
	private UserRepository userRepository;
	
	//Constructor Injection
	public UserJpaResource(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/users")
	public List<User> retrievAllUsers(){
		return userRepository.findAll();
	}
	
	//HATEOAS - HyperMedia as the Engine of Application State
	//EntityModel and WebMvcLinkBuilder

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable Integer id){
		Optional<User> userById = userRepository.findById(id);
		if(userById.isEmpty()) {
			throw new UserNotFoundException("User not found with id = " + id);
		}
		EntityModel<User> entityModel = EntityModel.of(userById.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri() ;
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable Integer id){
		Optional<User> userById = userRepository.findById(id);
		if(userById.isEmpty()) {
			throw new UserNotFoundException("User not found with id = " + id);
		}
		List<Post> posts = userById.get().getPosts();
		return posts;
	}
}
