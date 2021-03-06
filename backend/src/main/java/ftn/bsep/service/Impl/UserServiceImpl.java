package ftn.bsep.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ftn.bsep.model.Admin;
import ftn.bsep.model.DigEntity;
import ftn.bsep.repository.AdminRepository;
import ftn.bsep.repository.EntityRepository;
import ftn.bsep.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	
	
	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = searchUserInAllRepositories(username);
		if (userDetails != null) {
            return userDetails;
        }
        throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
	}

	//trazi Usera u svim repozitorijumima
	@Override
	public UserDetails searchUserInAllRepositories(String username) {
		
		 try {
	            Admin admin = adminRepository.findOneByUsername(username);
	            if (admin != null) {
	                return admin;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }
		 try {
	            DigEntity entity = entityRepository.findOneByUsername(username);
	            if (entity != null) {
	                return entity;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }
		return null;
	}


	
	@Override
	public boolean emailIsUsed(String email) {
		 
		try {
	            Admin admin = adminRepository.findByUsername(email);
	            if (admin != null) {
	                return true;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }

	        try {
	            DigEntity entity = entityRepository.findByUsername(email);
	            if (entity != null) {
	                return true;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }

	   return false;

	}
}
