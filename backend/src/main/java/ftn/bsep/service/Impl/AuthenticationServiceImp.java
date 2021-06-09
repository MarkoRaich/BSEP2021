package ftn.bsep.service.Impl;


import ftn.bsep.dto.LoggedInUserDTO;
import ftn.bsep.dto.UserDTO;
import ftn.bsep.dto.UserTokenState;
import ftn.bsep.model.Admin;
import ftn.bsep.model.Authority;
import ftn.bsep.repository.AdminRepository;
import ftn.bsep.repository.AuthorityRepository;
import ftn.bsep.security.TokenUtils;
import ftn.bsep.security.auth.JwtAuthenticationRequest;
import ftn.bsep.service.AuthenticationService;
import ftn.bsep.service.UserService;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

	 @Autowired
	 private UserService userService;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private AuthorityRepository authRepository;
	 
	 @Autowired
	 private AdminRepository adminRepository;
	 
	 @Autowired
	 private AuthenticationManager authManager;
	 
	 @Autowired
	 private TokenUtils tokenUtils;
	
	
	 @Override
	    public LoggedInUserDTO login(JwtAuthenticationRequest jwtAuthenticationRequest) {
	    	
	    	final Authentication authentication = authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                jwtAuthenticationRequest.getUsername(),
	                jwtAuthenticationRequest.getPassword()
	            )
	        );
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        //kreiranje tokena za korisnika
	        String username = returnUsername(authentication.getPrincipal());
	        if (username == null) {
	            return null;
	        }
	        	        
	        String jwtToken = tokenUtils.generateToken(username);
	        int expiresIn = tokenUtils.getExpiredIn();
	        
	        return returnLoggedInUser(
	            authentication.getPrincipal(),
	            new UserTokenState(jwtToken, expiresIn)
	        );
	    }

	@Override
	public UserDTO registerAdmin(UserDTO userDTO) {
		
			//provera da li postoji nalog sa tim email-om
			if(userService.emailIsUsed(userDTO.getEmail())) {
				
				System.out.println("nadjen isti email....");
				return null;
			}

			Admin admin = new Admin();
			admin.setUsername(userDTO.getEmail());
			admin.setFirstName(userDTO.getFirstName());
			admin.setLastName(userDTO.getLastName());
			
			admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			admin.setAuthorities(findByName("ROLE_PATIENT"));
			

	        return new UserDTO(adminRepository.save(admin));
	}

	@Override
    public Set<Authority> findByName(String name) {
        Authority auth = this.authRepository.findByName(name);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(auth);
        return authorities;
    }
	

    private String returnUsername(Object object) {
       
       if (object instanceof Admin) {
            return ((Admin) object).getUsername(); }
//        } else if (object instanceof Patient) {
//            return ((Patient) object).getUsername();
//        } 
       
        return null;
    }
    

    private LoggedInUserDTO returnLoggedInUser(Object object, UserTokenState userTokenState) {
        if (object instanceof Admin) {
        	Admin admin = (Admin) object;
            return new LoggedInUserDTO(admin.getId(), admin.getUsername(), "ADMIN", userTokenState); }
//        } else if (object instanceof User) {
//        	User user = (User) object;
//        	return new LoggedInUserDTO(user.getId(), user.getUsername(), "USER", userTokenState);
//        }

        return null;
 
    }

}
