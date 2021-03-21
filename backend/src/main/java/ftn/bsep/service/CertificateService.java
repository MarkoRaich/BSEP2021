package ftn.bsep.service;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.util.List;

import ftn.bsep.dto.CertificateBasicDTO;
import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.dto.IssuerDTO;
import ftn.bsep.model.CertificateDB;

public interface CertificateService {

	List<CertificateDB> getAllCertificates();

	List<IssuerDTO> getAllValidCA();

	CertificateDetailsDTO getCertificateDetails(String serialNumber) throws CertificateEncodingException, CertificateParsingException;

	boolean issueCertificate(CertificateDTO certificateDTO);

	boolean checkRevocationStatusOCSP(String serialNumber);


}
