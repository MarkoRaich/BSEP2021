package ftn.bsep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.DigEntity;

public interface EntityRepository extends JpaRepository<DigEntity, Long> {

	DigEntity findByUsername(String email);

	DigEntity findOneByUsername(String username);

	List<DigEntity> findByHasActiveCertificate(boolean b);

	DigEntity findOneById(Long id);

	DigEntity findBySerialNumberCertificate(String serialNumber);

}
