package com.kirti.restwebservices.restfulwebservices.user;

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

import com.kirti.restwebservices.restfulwebservices.user.jpa.PostRepository;
import com.kirti.restwebservices.restfulwebservices.user.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	//Constructor Injection
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
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

	@GetMapping("/users/{userId}/posts")
	public List<Post> retrievePostsForUser(@PathVariable Integer userId){
		Optional<User> userById = userRepository.findById(userId);
		if(userById.isEmpty()) {
			throw new UserNotFoundException("User not found with id = " + userId);
		}
		List<Post> posts = userById.get().getPosts();
		return posts;
	}
	
	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Post> createPostforUser(@PathVariable Integer userId, @Valid @RequestBody Post post) {
		Optional<User> userById = userRepository.findById(userId);
		if(userById.isEmpty()) {
			throw new UserNotFoundException("User not found with User Id = " + userId);
		}
	    post.setUser(userById.get());
	    Post savedPost = postRepository.save(post);
	    URI postLocation = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{postId}")
				.buildAndExpand(savedPost.getId())
				.toUri() ;
		return ResponseEntity.created(postLocation).build();		
	}
	
	@GetMapping("/users/{userId}/posts/{postId}")
	public Post retrievePostForUserByPostId(@PathVariable Integer userId, @PathVariable Integer postId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new UserNotFoundException("User not found with User Id = " + userId);
		}
		Optional<Post> post = postRepository.findById(postId);
		if(post.isEmpty()) {
			throw new UserNotFoundException("Post not found for the user " + user.get().getName() + " with post Id = " + postId);
		}
		return post.get();	
	}
}
