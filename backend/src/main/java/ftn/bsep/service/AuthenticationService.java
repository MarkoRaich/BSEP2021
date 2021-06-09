package ftn.bsep.service;

import ftn.bsep.security.auth.JwtAuthenticationRequest;

import java.util.Set;

import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.dto.UserDTO;
import ftn.bsep.model.Authority;

public interface AuthenticationService {

	LoggedInUserDTO login(JwtAuthenticationRequest authenticationRequest);

	UserDTO registerAdmin(UserDTO userDTO);

	Set<Authority> findByName(String name);

}
