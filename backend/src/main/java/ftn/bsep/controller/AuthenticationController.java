package ftn.bsep.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.security.auth.JwtAuthenticationRequest;
import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.dto.UserDTO;
import ftn.bsep.service.AuthenticationService;
import ftn.bsep.service.UserService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
    private AuthenticationService authService;

	
	
	@PostMapping(value = "/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
	 
		UserDTO user = authService.registerAdmin(userDTO);
	        if (user == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@PostMapping(value= "/login")
	public ResponseEntity<LoggedInUserDTO> login(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, IOException {
		
		 try {
	        	
	            LoggedInUserDTO loggedInUserDTO = authService.login(authenticationRequest);

	            if (loggedInUserDTO == null) {
	                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	            } else {
	            	System.out.println("Ulogovan " + loggedInUserDTO.getRole() + " : " + loggedInUserDTO.getUsername());
	                return new ResponseEntity<>(loggedInUserDTO, HttpStatus.OK);
	            }
	        } catch (AuthenticationException e) {
	            e.printStackTrace();
	        }
	        
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
}
