package ftn.bsep.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.model.CertificateDB;

public interface CertificateRepository extends JpaRepository<CertificateDB, Long> {

	List<CertificateDB> findAll();

	CertificateDB findBySerialNumberSubject(String serialNumber);
}
