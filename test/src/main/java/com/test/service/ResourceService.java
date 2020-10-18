package com.test.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.service.ResourceService;
import com.test.entities.Resource;
import com.test.repository.ResourceRepository;

@Service
public class ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

	// save res
	public Resource saveResource(Resource resource) {
		return (Resource) this.resourceRepository.save(resource);
	}

	// find All res
	public List<Resource> findAll() {
		return (List<Resource>) this.resourceRepository.findAll();
	}

	// get res by id
	public Optional<Resource> getOne(int id) {
		return this.resourceRepository.findById(id);
	}

	// delete res / or maybe Boolean type
	public void delete(Resource resource) {
		this.resourceRepository.delete(resource);
	}
	/*public Iterable<Resource> findAllByResourceId(int id) {
		// TODO Auto-generated method stub
		return this.resourceRepository.findAllByPostId(id);
	}
	*/public Iterable<Resource> findAllByPostId(int postId) {
		// TODO Auto-generated method stub
		return this.resourceRepository.findAllByPostId(postId);
	}
}
