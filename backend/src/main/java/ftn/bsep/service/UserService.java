package ftn.bsep.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	boolean emailIsUsed(String email);

	UserDetails searchUserInAllRepositories(String username);

	
}
