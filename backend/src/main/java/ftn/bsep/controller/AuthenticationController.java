package ftn.bsep.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.security.auth.JwtAuthenticationRequest;
import ftn.bsep.dto.AdminDTO;
import ftn.bsep.dto.EntityDTO;
import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.service.AuthenticationService;

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

	
	
	@PostMapping(value = "/register-admin")
	public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminDTO adminDTO) {
	 
		AdminDTO admin = authService.registerAdmin(adminDTO);
	        if (admin == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        System.out.println("Registrovan novi admin: " + admin.getFirstName() + " " + admin.getLastName());
	        return new ResponseEntity<>(admin, HttpStatus.CREATED);
	}
	
	
	@PostMapping(value = "/register-entity")
	public ResponseEntity<EntityDTO> registerEntity(@RequestBody EntityDTO entityDTO) {
	 
		EntityDTO entity = authService.registerEnitity(entityDTO);
	        if (entity == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        
	        System.out.println("Registrovan novi entitet: " + entity.getCommonName());
	        return new ResponseEntity<>(entity, HttpStatus.CREATED);
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
