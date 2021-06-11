package ftn.bsep.service;

import java.security.cert.CertificateEncodingException;

import java.security.cert.CertificateParsingException;
import java.util.List;

import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.model.CertificateDB;

public interface CertificateService {

	
	List<CertificateDB> getAllCertificates();

	List<CertificateDB> getAllValidCA();

	CertificateDetailsDTO getCertificateDetails(String serialNumber) throws CertificateEncodingException, CertificateParsingException;

	boolean checkRevocationStatusOCSP(String serialNumber);

	boolean issueRootCertificate(CertificateDTO certificateDTO)  throws CertificateEncodingException, CertificateParsingException;

	boolean issueCAorEndCertificate(CertificateDTO certificateDTO) throws CertificateEncodingException, CertificateParsingException;

	boolean revokeCertificate(String serialNumber);

	List<CertificateDB> getMyCertificate(String serialNumberCert);

	byte[] downloadCertificate(String serialNumber) throws CertificateEncodingException;


}
