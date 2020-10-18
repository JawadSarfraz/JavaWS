package com.test.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.repository.ResourceRepository;
import com.test.dto.PostDto;
import com.test.dto.ResourceDto;
import com.test.dto.UserDto;
import com.test.entities.Post;
import com.test.entities.Resource;
import com.test.entities.User;
import com.test.service.PostService;
import com.test.service.ResourceService;
import com.test.service.UserService;

@RestController
@RequestMapping("/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String index() {
		return "Meowwwww....";
	}

	// HERE BOOLEAN CAN BE RETURNED-->uSER CREATED CONFIRMATION
	@PostMapping("/create")
	public ResponseEntity<ResourceDto> createUser(
			@Valid @RequestBody Resource resource) {
		//fist FN get whether POST req coming, then check whether that POST EXIST via ID, if not return FALSE
		//THEN IN POST i need to check whether that USER EXIST OR NOT
		if(this.postService.getOne(resource.getPost().getId()) == null)
			return ResponseEntity.notFound().build();
		Resource res = this.resourceService.saveResource(resource);
		ResourceDto resourceDto = modelMapper.map(res, ResourceDto.class);
		return ResponseEntity.ok().body(resourceDto);
	}
	// TOTAL NUMBER OF RESOURCECS--> USEFUL IN BUSINESS LOGIC
	@GetMapping("/findAllResource")
	public ResponseEntity<List<ResourceDto>> getAllResource() {
		List<Resource> resource = this.resourceService.findAll();
		if (resource == null)
			return ResponseEntity.notFound().build();
		Type listType = new TypeToken<List<ResourceDto>>() {
		}.getType();
		List<ResourceDto> resourceDtoList = this.modelMapper.map(resource,
				listType);
		return ResponseEntity.ok().body(resourceDtoList);
	}
	//GET ALL RESOURCES BY POSTID
	@GetMapping("/findResourcesByPostId/{id}")
	public ResponseEntity<List<ResourceDto>> getResourcesByPostId(@PathVariable(value="id") int postId){
		if(this.postService.getOne(postId) == null)
			return ResponseEntity.notFound().build();
		Iterable<Resource> res = this.resourceService.findAllByPostId(postId);
		Type listType = new TypeToken<Iterable<ResourceDto>>() {
		}.getType();
		List<ResourceDto> resourceDtoList = this.modelMapper.map(res, listType);
		return ResponseEntity.ok().body(resourceDtoList);
	}
	@GetMapping("/findResource/{id}")
	public ResponseEntity<ResourceDto> getUse(@PathVariable(value = "id") int id) {
		Optional<Resource> resource = this.resourceService.getOne(id);
		if (resource == null)
			return ResponseEntity.notFound().build();
		ResourceDto resourceDto = this.modelMapper.map(resource,
				ResourceDto.class);
		return ResponseEntity.ok().body(resourceDto);
	}

	// RESOURCES DELETED WHEN POST DELETE-->PS: Soft Delele:)
	/*
	 * @PostMapping("/delete/{id}") // public ResponseEntity<UserDto>
	 * delete(@PathVariable(value = "id") int id, @Valid @RequestBody User user)
	 * { public Boolean delete(@PathVariable(value = "id") int id) {
	 * Optional<Resource> getResource = this.resourceService.getOne(id); if
	 * (getResource == null) return false; //Resource doesnot Exist...ALREADY
	 * DELETED //IMPLEMENT Resource
	 * if(getResource.get().getPostFlag().equals("0")) return false;
	 * Iterable<Resource> resources= resourceService.findAllByResourceId(id);
	 * for(Resource resource : resources){ resource.setPostFlag("0");
	 * resourceService.saveResource(resource); }
	 * getResource.get().setPostFlag("0");
	 * this.postService.savePost(getUser.get()); return true; }
	 * 
	 * @PostMapping("update/{id}") public ResponseEntity<Optional<User>>
	 * update(@PathVariable(value = "id") int id) {
	 * 
	 * Optional<User> getUser = this.userService.getOne(id); if (getUser ==
	 * null) return ResponseEntity.notFound().build(); User updateUser =
	 * this.userService.saveUser(getUser.get()); UserDto userDto =
	 * this.modelMapper.map(updateUser, UserDto.class); return
	 * ResponseEntity.ok().body(getUser); }
	 */// deleteResourceWhenPostDeleted by post Id
	@PostMapping("/deleteResourceWhenPostDeleted/{post_id}")
	public Boolean deleteResourceWhenPostDeleted(
			@PathVariable(value = "user_id") int postId) {

		Iterable<Resource> resources = this.resourceService
				.findAllByPostId(postId);
		if (resources.spliterator().getExactSizeIfKnown() == 0)
			return false;
		for (Resource resource : resources) {
			resource.setPostFlag("0");
			this.resourceService.saveResource(resource);
		}
		return true;
	}
}
