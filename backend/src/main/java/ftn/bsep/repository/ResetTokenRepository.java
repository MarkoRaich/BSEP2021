package ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long>{

	ResetToken findByResetToken(String resetToken);
}
