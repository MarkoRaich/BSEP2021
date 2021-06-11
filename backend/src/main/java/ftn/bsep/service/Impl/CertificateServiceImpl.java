package ftn.bsep.service.Impl;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.model.CertificateDB;
import ftn.bsep.model.DigEntity;
import ftn.bsep.repository.CertificateRepository;
import ftn.bsep.repository.CertificateStore;
import ftn.bsep.repository.EntityRepository;
import ftn.bsep.service.CertificateService;
import ftn.bsep.utilities.BigIntGenerator;
import ftn.bsep.utilities.CertificateGenerator;
import ftn.bsep.utilities.CertificateType;
import ftn.bsep.utilities.IssuerData;
import ftn.bsep.utilities.SubjectData;

@Service
public class CertificateServiceImpl implements CertificateService {

	@Autowired
	private CertificateStore store;
	
	@Autowired
	private CertificateRepository certificateRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
    private CertificateGenerator certificateGenerator;
	
	@Autowired
    private BigIntGenerator bigIntGenerator;
	
	
	private KeyPair keyPairSubject = generateKeyPair();
	private X509Certificate cert;
	
    private String fileLocationCA = "src/main/resources/keyStores/ks-ca.jks";
    private String fileLocationEE = "src/main/resources/keyStores/ks-end.jks";
    private String passwordCA = "";
    private String passwordEE = "";
    
	
    
	@Override
	public List<CertificateDB> getAllCertificates()  {
		
		return certificateRepository.findAll();
	}
	
	
	@Override
	public List<CertificateDB> getMyCertificate(String serialNumberCert) {

		List<CertificateDB> certs = new ArrayList<CertificateDB>();
		
		CertificateDB cert =  certificateRepository.findBySerialNumberSubject(serialNumberCert);
		if(cert != null) {
			certs.add(cert);
		}
		
		return certs;
	}
	

	/** 
	 * Da bi sertifikat bio validan mora:
	 * -biti CA
	 * -da nije povucen
	 * -da nije istekao
	 * -ima dobar potpis
	 * */
	@Override
	public List<CertificateDB> getAllValidCA() {
		
		List<CertificateDB> returnList = new ArrayList<CertificateDB>();
		
		//vraca sve CA, nepovucene i aktivne sertifikate
		List<CertificateDB> certs = certificateRepository.findByCaAndRevokedAndEndDateGreaterThanEqual(true, false, new Date());
		
		//provera da li je potpis dobar, ako jeste dodaje se u listu
		for(CertificateDB cert: certs) {
			
			X509Certificate certX509 = (X509Certificate) store.findCertificateBySerialNumber(cert.getSerialNumberSubject(), fileLocationCA, passwordCA);
			
			
			X509Certificate issuerCertX509 = (X509Certificate) store.findCertificateBySerialNumber(cert.getSerialNumberIssuer(), fileLocationCA, passwordCA);
			
			try {
				certX509.verify(issuerCertX509.getPublicKey());
				//System.out.println("Validacija potpisa uspesna");
				returnList.add(cert);
				
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				System.out.println("\nValidacija potpisa neuspesna :(");
				e.printStackTrace();
				continue;
			}
			
			
		}
		
		return returnList;
	}
	
	
	@Override
	public boolean revokeCertificate(String serialNumber) {
		
		CertificateDB cert = certificateRepository.findBySerialNumberSubject(serialNumber);
		
		if(cert.isRevoked()) { //vec je povucen vrati ok
			return true;
		}
		
	    cert.setRevoked(true);
		
	    DigEntity entity = entityRepository.findBySerialNumberCertificate(serialNumber);
		entity.setHasActiveCertificate(false);
		//entity.setSerialNumberCertificate(null);
		//System.out.println(entity.toString());
		 
	    this.certificateRepository.save(cert);
	    System.out.println("Povučen sertifikat: "+ cert.getSerialNumberSubject());
	    
	    //ako je CA treba povuci i svu decu koje je on izdao
	    if(cert.isCa()) {
	    	
		   revokeChildren(serialNumber);
		   
	    }
	   
	    return true;
		
	}
		

	private void revokeChildren(String serialNumber) {

		ArrayList<CertificateDB> certsDB = (ArrayList<CertificateDB>) certificateRepository.findAllBySerialNumberIssuer(serialNumber);
		
		if(!certsDB.isEmpty()) {
			
			for(CertificateDB certDB: certsDB) {
				
				if(!certDB.getSerialNumberIssuer().equals(certDB.getSerialNumberSubject())) {
				
					if(!certDB.isRevoked()) {
						
						certDB.setRevoked(true);
						
						DigEntity entity = entityRepository.findBySerialNumberCertificate(certDB.getSerialNumberSubject());
						entity.setHasActiveCertificate(false);
						//entity.setSerialNumberCertificate(null);
						//System.out.println(entity.toString());
						
						this.certificateRepository.save(certDB);
					    System.out.println("Povučen sertifikat: "+ certDB.getSerialNumberSubject());
					    
					    if(certDB.isCa()) {
					    	
					    	revokeChildren(certDB.getSerialNumberSubject());	
					    }	
					}
				}
			}
		}
	
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
	        	
	            cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber, fileLocationCA, passwordCA);
	            if(cert != null) {
	            	
	                JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
	        
	                Boolean isRoot;
          
	                if(certDB.getSerialNumberSubject().equals(certDB.getSerialNumberIssuer())) {
	                	isRoot=true;
	                }else {
	                	isRoot=false;
	                }

	                CertificateDetailsDTO cddto = new CertificateDetailsDTO(certHolder, cert, certDB, isRoot);
	               
	                return cddto;
	            }else{
	                return null;
	            }
	        
	            
	        //ako nije CA trazi u END key store-u
	        } else {
	            cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber, fileLocationEE, passwordEE);
	            if(cert != null) {
	            	
	                JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
	                boolean isRoot = false;
	                CertificateDetailsDTO cddto = new CertificateDetailsDTO(certHolder, cert, certDB, isRoot);
	                return cddto;
	            }else{
	                return null;
	            }

	        }
	}

	
	@Override
	public boolean issueRootCertificate(CertificateDTO certificateDTO) throws CertificateEncodingException {
		
		//generisanje privatnog i javnog kljuca za sertifikat digitalnog entiteta
		keyPairSubject = generateKeyPair();
		
		IssuerData issuerData = generateIssuerDataForSelfSigned(certificateDTO);
		
		SubjectData subjectData = generateSubjectData(certificateDTO);
		
		
				
	    //kreiranje sertifikata 
		cert = certificateGenerator.generateCertificate(subjectData, issuerData, certificateDTO);
		
		if (cert == null) {
	        return false;
	    }
		
		//flag za vracanje entiteta na frontend
		DigEntity entity = this.entityRepository.findOneById(Long.valueOf(certificateDTO.getEntityId()));

		entity.setHasActiveCertificate(true);
		entity.setSerialNumberCertificate(cert.getSerialNumber().toString());
		
		//cuvanje sertifikata u key store-u
		store.saveCertificate(new X509Certificate[]{cert}, keyPairSubject.getPrivate(), fileLocationCA, passwordCA);
		
		
		//cuvanje sertifikata u bazi
		JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
		
		CertificateDB certDB = new CertificateDB(cert.getSerialNumber().toString(),
												 cert.getSerialNumber().toString(),
												 certHolder.getNotBefore(),
												 certHolder.getNotAfter(),
												 true,
												 false);
       this.certificateRepository.save(certDB);
       
       System.out.println("izdat SELF-SIGNED sertifikat: " + cert.getSerialNumber());
		
		return true;
		
	}

	@Override
	public boolean issueCAorEndCertificate(CertificateDTO certificateDTO) throws CertificateEncodingException {
		
		//generisanje privatnog i javnog kljuca za sertifikat digitalnog entiteta
		keyPairSubject = generateKeyPair();
		
        SubjectData subjectData = generateSubjectData(certificateDTO);
		
        String serialNumber = certificateDTO.getIssuerSerialNumber();
        IssuerData issuerData = store.findIssuerBySerialNumber(serialNumber, fileLocationCA, passwordCA);
		
        //kreiranje sertifikata 
		cert = certificateGenerator.generateCertificate(subjectData, issuerData, certificateDTO);
		
		if (cert == null) {
	        return false;
	    }
		
		//flag za vracanje entiteta na frontend
		DigEntity entity = this.entityRepository.findOneById(Long.valueOf(certificateDTO.getEntityId()));
		
		entity.setHasActiveCertificate(true);
		entity.setSerialNumberCertificate(cert.getSerialNumber().toString());
		
		if (certificateDTO.getCertificateType().equals(CertificateType.END_ENTITY)) {
			
			//cuvanje sertifikata u key store-u
			store.saveCertificate(new X509Certificate[]{cert}, keyPairSubject.getPrivate(), fileLocationEE, passwordEE);
			
			//cuvanje sertifikata u bazi
			JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
			
			CertificateDB certDB = new CertificateDB(cert.getSerialNumber().toString(),
													 certificateDTO.getIssuerSerialNumber(),
													 certHolder.getNotBefore(),
													 certHolder.getNotAfter(),
													 false,
													 false);
			this.certificateRepository.save(certDB);
			System.out.println("izdat END-ENTITY sertifikat: " + cert.getSerialNumber());
				
			return true;
		
		}else if(certificateDTO.getCertificateType().equals(CertificateType.INTERMEDIATE)) {
		
			//cuvanje sertifikata u key store-u
			store.saveCertificate(new X509Certificate[]{cert}, keyPairSubject.getPrivate(), fileLocationCA, passwordCA);
			
			//cuvanje sertifikata u bazi
			JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) cert);
			
			CertificateDB certDB = new CertificateDB(cert.getSerialNumber().toString(),
													 certificateDTO.getIssuerSerialNumber(),
													 certHolder.getNotBefore(),
													 certHolder.getNotAfter(),
													 true,
													 false);
			this.certificateRepository.save(certDB);
			System.out.println("izdat INTERMEDIATE sertifikat: " + cert.getSerialNumber());
				
			return true;
		
		}
		
		return false;
	}

	@Override
	public byte[] downloadCertificate(String serialNumber) throws CertificateEncodingException {
		
		CertificateDB certDB = certificateRepository.findBySerialNumberSubject(serialNumber);
		X509Certificate x509Cert;
		if(certDB.isCa()) {
			x509Cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber, fileLocationCA, passwordCA);
		} else {
			x509Cert = (X509Certificate) store.findCertificateBySerialNumber(serialNumber,fileLocationEE, passwordEE);
		}
		
		return Base64.getEncoder().encode(x509Cert.getEncoded());
	}
	

	
//__________________________________________________________________________________________________________________________________________________-
	
	 private KeyPair generateKeyPair() {
	        try {
	            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
	            keyGen.initialize(2048, random);
	            return keyGen.generateKeyPair();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchProviderException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 private SubjectData generateSubjectData(CertificateDTO certificateDTO) {
	       
		 		DigEntity entity = this.entityRepository.findOneById(Long.valueOf(certificateDTO.getEntityId()));
		 
		 		//pocetni datum je datum kreiranja
	            Date startDate = new Date(); 
	            
	            Calendar c = Calendar.getInstance();
	            c.setTime(startDate);
	            c.add(Calendar.YEAR, certificateDTO.getDuration());
	            Date endDate = c.getTime();
	            
	            //nalazenje krajnjeg datuma od potpisnika da ne bi vazio duze od njega!
	            CertificateDB cert = certificateRepository.findBySerialNumberSubject(certificateDTO.getIssuerSerialNumber());
	            if(cert!=null) {
	            	
		            Date issuersEndDate = cert.getEndDate();          
		           
		            if(endDate.after(issuersEndDate)) {
		            	endDate = issuersEndDate;
		            } 
	            }

	            String serialNumber = bigIntGenerator.generateRandom().toString();

	            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	            if(!entity.getCommonName().equals("")) {
	                builder.addRDN(BCStyle.CN, entity.getCommonName());
	            }
	            if(!entity.getLastName().equals("")) {
	                builder.addRDN(BCStyle.SURNAME, entity.getLastName());
	            }
	            if(!entity.getFirstName().equals("")) {
	                builder.addRDN(BCStyle.GIVENNAME, entity.getFirstName());
	            }
	            if(!entity.getOrganization().equals("")) {
	                builder.addRDN(BCStyle.O, entity.getOrganization());
	            }
	            if(!entity.getOrganizationUnit().equals("")) {
	                builder.addRDN(BCStyle.OU, entity.getOrganizationUnit());
	            }
	            if(!entity.getState().equals("")){
	                builder.addRDN(BCStyle.ST, entity.getState());
	            }
	            if(!entity.getCountry().equals("")) {
	                builder.addRDN(BCStyle.C, entity.getCountry());
	            }
	            if(!entity.getUsername().equals("")) {
	                builder.addRDN(BCStyle.E, entity.getUsername());
	            }

	            return new SubjectData(keyPairSubject.getPublic(), builder.build(), serialNumber, startDate, endDate);
	       
	 }
	 
	 private IssuerData generateIssuerDataForSelfSigned(CertificateDTO certDTO) {
		 	
		 	DigEntity entity = this.entityRepository.findOneById(Long.valueOf(certDTO.getEntityId()));
		 
	        try {
	            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	            if(!entity.getCommonName().equals("")) {
	                builder.addRDN(BCStyle.CN, entity.getCommonName());
	            }
	            if(!entity.getLastName().equals("")) {
	                builder.addRDN(BCStyle.SURNAME, entity.getLastName());
	            }
	            if(!entity.getFirstName().equals("")) {
	                builder.addRDN(BCStyle.GIVENNAME, entity.getFirstName());
	            }
	            if(!entity.getOrganization().equals("")) {
	                builder.addRDN(BCStyle.O, entity.getOrganization());
	            }
	            if(!entity.getOrganizationUnit().equals("")) {
	                builder.addRDN(BCStyle.OU, entity.getOrganizationUnit());
	            }
	            if(!entity.getState().equals("")){
	                builder.addRDN(BCStyle.ST, entity.getState());
	            }
	            if(!entity.getCountry().equals("")) {
	                builder.addRDN(BCStyle.C, entity.getCountry());
	            }
	            if(!entity.getUsername().equals("")) {
	                builder.addRDN(BCStyle.E, entity.getUsername());
	            }

	            return new IssuerData(keyPairSubject.getPrivate(), builder.build());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }




}
