package com.example.crudpostgres.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudpostgres.model.Tutorial;
import com.example.crudpostgres.repository.TutorialRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	// getting all tutorials
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title)
	{
		try {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			
			if(title == null)
				tutorialRepository.findAll().forEach(tutorials::add);
			else
				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
			
			if(tutorials.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
			
		}catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// get all Ends
	
	
	// get single tutorial
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id)
	{
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		
		if(tutorialData.isPresent())
		{
			return new ResponseEntity<>(tutorialData.get(),HttpStatus.OK); 
		}
		else {
			System.out.printf("%d id not found",id);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// get signle tutorial ends
	
	
	//Adding new tutorials
	
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial)
	{
		try {
			
			Tutorial _tutorial = tutorialRepository.save(
				new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false));
				return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);	
			
		}catch(Exception e)
		{
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Adding new tutorials ends
	
	
	// update tutorial
	
	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial)
	{
		Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
		
		if(tutorialData.isPresent())
		{
			Tutorial _tutorial = tutorialData.get();
			_tutorial.setTitle(tutorial.getTitle());
			_tutorial.setDescription(tutorial.getDescription());
			_tutorial.setPublished(tutorial.isPublished());
			
			return new ResponseEntity<> (tutorialRepository.save(_tutorial), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// update tutorial ends
	
	// delete by id
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id")long id)
	{
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// delete by id ends
	
	// delete all
	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials()
	{
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e)
		{
			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// delete all ends
	
	// get published tutorials
	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished()
	{
		try {
			List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
			
			if(tutorials.isEmpty())
			{
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<> (tutorials, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// get published tutorials ends
	
	
	

}
