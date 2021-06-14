package ftn.bsep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.security.auth.JwtAuthenticationRequest;
import ftn.bsep.dto.AdminDTO;
import ftn.bsep.dto.EntityDTO;
import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.dto.NewpasswordDTO;
import ftn.bsep.service.AuthenticationService;

import java.io.IOException;

import javax.validation.Valid;

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
	
	
	
	
	@PostMapping(value = "/register-admin", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registerAdmin(@Valid @RequestBody AdminDTO adminDTO) {
	 
		AdminDTO admin = authService.registerAdmin(adminDTO);
	        if (admin == null) {
	            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
	        }

	        System.out.println("Registrovan novi admin: " + admin.getFirstName() + " " + admin.getLastName());
	        return new ResponseEntity<>(true, HttpStatus.CREATED);
	}
	
	
	@PostMapping(value = "/register-entity", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registerEntity(@Valid @RequestBody EntityDTO entityDTO) {

		EntityDTO entity = authService.registerEnitity(entityDTO);
	        if (entity == null) {
	            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
	        }
	        
	        System.out.println("Registrovan novi entitet: " + entity.getCommonName());
	        return new ResponseEntity<>(true, HttpStatus.CREATED);
	}
	
	
	@PostMapping(value= "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoggedInUserDTO> login(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, IOException {
		 
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
	
	@GetMapping(value="/reset-password")
	public ResponseEntity<Boolean> resetPassword(@RequestParam("emailAddress") String emailAddress){
		
		Boolean mailSent = this.authService.resetPasswordSendMail(emailAddress);
		

		
		if(!mailSent) {
			
			System.out.println("nema naloga sa ovim emailom");
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
			 
		} else {
			
			System.out.println("mejl je poslat");
			return new ResponseEntity<>(true, HttpStatus.OK); 
			 
		}
		
	}
	
	
	@GetMapping(value="/confirm-account")
	public ResponseEntity<Boolean> confirmAccount(@RequestParam("token")String confirmationToken){
		
		System.out.println("poslao si token: " + confirmationToken);
		
		boolean confirmed = authService.confirmAccount(confirmationToken);
		if(confirmed) {
			return new ResponseEntity<>(confirmed, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(confirmed, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(value="/new-password", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> newPassword(@Valid @RequestBody NewpasswordDTO newPasswordDTO){
		
		System.out.println("poslao si token: " + newPasswordDTO.getToken());
		
		boolean confirmed = authService.newPassword(newPasswordDTO);
		if(confirmed) {
			return new ResponseEntity<>(confirmed, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(confirmed, HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
