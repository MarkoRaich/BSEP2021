package ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{

	ConfirmationToken findByConfirmationToken(String confirmationToken);

	
}
