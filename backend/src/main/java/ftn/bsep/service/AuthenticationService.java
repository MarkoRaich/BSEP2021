package ftn.bsep.service;

import ftn.bsep.security.auth.JwtAuthenticationRequest;

import java.util.Set;

import ftn.bsep.dto.AdminDTO;
import ftn.bsep.dto.EntityDTO;
import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.model.Authority;

public interface AuthenticationService {

	LoggedInUserDTO login(JwtAuthenticationRequest authenticationRequest);

	AdminDTO registerAdmin(AdminDTO adminDTO);
	
	EntityDTO registerEnitity(EntityDTO entityDTO);
	
	Set<Authority> findByName(String name);
	

	

}
