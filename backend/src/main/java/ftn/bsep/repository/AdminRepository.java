package ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String email);

	Admin findOneByUsername(String username);


}
