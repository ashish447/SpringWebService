package com.patient.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patient.springboot.entity.Patient;
import com.patient.springboot.exception.ResourceNotFoundException;
import com.patient.springboot.repository.PatientRepository;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	// get all users
	@GetMapping
	public List<Patient> getAllUsers() {
		return this.patientRepository.findAll();
	}

	// get user by id
	@GetMapping("/{id}")
	public Patient getUserById(@PathVariable (value = "id") long userId) {
		return this.patientRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("patient not found with id :" + userId));
	}

	// create user
	@PostMapping
	public Patient createUser(@RequestBody Patient user) {
		return this.patientRepository.save(user);
	}
	
	// update user
	@PutMapping("/{id}")
	public Patient updateUser(@RequestBody Patient user, @PathVariable ("id") long userId) {
		 Patient existingUser = this.patientRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("patient not found with id :" + userId));
		 existingUser.setFirstName(user.getFirstName());
		 existingUser.setLastName(user.getLastName());
		 existingUser.setEmail(user.getEmail());
		 return this.patientRepository.save(existingUser);
	}
	
	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Patient> deleteUser(@PathVariable ("id") long userId){
		 Patient existingUser = this.patientRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("patient not found with id :" + userId));
		 this.patientRepository.delete(existingUser);
		 return ResponseEntity.ok().build();
	}
}
