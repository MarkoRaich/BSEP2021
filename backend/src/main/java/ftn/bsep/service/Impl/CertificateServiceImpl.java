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
import ftn.bsep.repository.CertificateRepository;
import ftn.bsep.repository.CertificateStore;
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
			System.out.println("izdat INTEMEDIATE sertifikat: " + cert.getSerialNumber());
				
			return true;
		
		}
		
		return false;
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
	            if(!certificateDTO.getSubjectCommonName().equals("")) {
	                builder.addRDN(BCStyle.CN, certificateDTO.getSubjectCommonName());
	            }
	            if(!certificateDTO.getSubjectLastName().equals("")) {
	                builder.addRDN(BCStyle.SURNAME, certificateDTO.getSubjectLastName());
	            }
	            if(!certificateDTO.getSubjectFirstName().equals("")) {
	                builder.addRDN(BCStyle.GIVENNAME, certificateDTO.getSubjectFirstName());
	            }
	            if(!certificateDTO.getSubjectOrganization().equals("")) {
	                builder.addRDN(BCStyle.O, certificateDTO.getSubjectOrganization());
	            }
	            if(!certificateDTO.getSubjectOrganizationUnit().equals("")) {
	                builder.addRDN(BCStyle.OU, certificateDTO.getSubjectOrganizationUnit());
	            }
	            if(!certificateDTO.getSubjectState().equals("")){
	                builder.addRDN(BCStyle.ST, certificateDTO.getSubjectState());
	            }
	            if(!certificateDTO.getSubjectCountry().equals("")) {
	                builder.addRDN(BCStyle.C, certificateDTO.getSubjectCountry());
	            }
	            if(!certificateDTO.getSubjectEmail().equals("")) {
	                builder.addRDN(BCStyle.E, certificateDTO.getSubjectEmail());
	            }

	            return new SubjectData(keyPairSubject.getPublic(), builder.build(), serialNumber, startDate, endDate);
	       
	 }
	 
	 private IssuerData generateIssuerDataForSelfSigned(CertificateDTO certificateDTO) {
		 	
		 	
		 
	        try {
	            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	            if(!certificateDTO.getSubjectCommonName().equals("")) {
	                builder.addRDN(BCStyle.CN, certificateDTO.getSubjectCommonName());
	            }
	            if(!certificateDTO.getSubjectLastName().equals("")) {
	                builder.addRDN(BCStyle.SURNAME, certificateDTO.getSubjectLastName());
	            }
	            if(!certificateDTO.getSubjectFirstName().equals("")) {
	                builder.addRDN(BCStyle.GIVENNAME, certificateDTO.getSubjectFirstName());
	            }
	            if(!certificateDTO.getSubjectOrganization().equals("")) {
	                builder.addRDN(BCStyle.O, certificateDTO.getSubjectOrganization());
	            }
	            if(!certificateDTO.getSubjectOrganizationUnit().equals("")) {
	                builder.addRDN(BCStyle.OU, certificateDTO.getSubjectOrganizationUnit());
	            }
	            if(!certificateDTO.getSubjectState().equals("")){
	                builder.addRDN(BCStyle.ST, certificateDTO.getSubjectState());
	            }
	            if(!certificateDTO.getSubjectCountry().equals("")) {
	                builder.addRDN(BCStyle.C, certificateDTO.getSubjectCountry());
	            }
	            if(!certificateDTO.getSubjectEmail().equals("")) {
	                builder.addRDN(BCStyle.E, certificateDTO.getSubjectEmail());
	            }

	            return new IssuerData(keyPairSubject.getPrivate(), builder.build());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
