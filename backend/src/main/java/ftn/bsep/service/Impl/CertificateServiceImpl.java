package ftn.bsep.service.Impl;

import java.security.cert.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.bsep.dto.CertificateBasicDTO;
import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.dto.IssuerDTO;
import ftn.bsep.model.CertificateDB;
import ftn.bsep.repository.CertificateRepository;
import ftn.bsep.repository.CertificateStore;
import ftn.bsep.service.CertificateService;

@Service
public class CertificateServiceImpl implements CertificateService {

	@Autowired
	private CertificateStore store;
	
	@Autowired
	private CertificateRepository certificateRepository;
	
	@Override
	public List<CertificateDB> getAllCertificates()  {
		
		return certificateRepository.findAll();
		
	}

	@Override
	public List<IssuerDTO> getAllValidCA() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean issueCertificate(CertificateDTO certificateDTO) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean checkRevocationStatusOCSP(String serialNumber) {
		
		CertificateDB cert = certificateRepository.findBySerialNumberSubject(serialNumber);
		
		if(cert.isRevoked()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public CertificateDetailsDTO getCertificateDetails(String serialNumber) throws CertificateEncodingException, CertificateParsingException {

	        CertificateDB certDB = certificateRepository.findBySerialNumberSubject(serialNumber);
	        
	        X509Certificate cert;
	        
	        //ako je CA trazi u CA key store-u
	        if(certDB.isCa()) {
	        	
	            cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber, "src/main/resources/keyStores/ks-ca.jks", "");
	            if(cert != null) {
	            	
	                JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
	                Certificate[] chain = store.findCertificateChainBySerialNumber(serialNumber, "src/main/resources/keyStores/ks-ca.jks", "");
	                X509Certificate x509Cert;
	                Boolean isRoot;
	                if (chain.length == 1) { //ROOT sertifikat i samopotpisni je
	                    x509Cert = (X509Certificate) chain[0];
	                    isRoot = true;
	                } else {
	                    x509Cert = (X509Certificate) chain[1];
	                    isRoot = false;
	                }

	                String issuerSerialNumber = x509Cert.getSerialNumber().toString();

	                CertificateDetailsDTO cddto = new CertificateDetailsDTO(certHolder, cert, issuerSerialNumber, isRoot);
	               
	                return cddto;
	            }else{
	                return null;
	            }
	        
	        //ako nije CA trazi u END key store-u
	        } else {
	            cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber, "src/main/resources/keyStores/ks-end.jks", "");
	            if(cert != null) {
	                JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
	                String issuerSerialNumber = certDB.getSerialNumberIssuer();
	                boolean isRoot = false;
	                CertificateDetailsDTO cddto = new CertificateDetailsDTO(certHolder, cert, issuerSerialNumber, isRoot);
	                return cddto;
	            }else{
	                return null;
	            }

	        }
	}

}
