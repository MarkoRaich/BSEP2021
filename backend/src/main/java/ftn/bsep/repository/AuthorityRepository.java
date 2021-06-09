package ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByName(String name);
	
}
