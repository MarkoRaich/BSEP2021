package ftn.bsep.controller;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.model.CertificateDB;
import ftn.bsep.model.DigEntity;
import ftn.bsep.service.CertificateService;
import ftn.bsep.service.EntityService;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private EntityService entityService;
	
	
	
	//ADMINU SE VRAĆAJU SVI SERTIFIKATI NA GLAVNU STRANU NA FRONTENDU
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value="/getAllCertificates", produces="application/json")	
    public List<CertificateDB> getAllCertificates() {
			
	        return certificateService.getAllCertificates();
	}

	
	//ADMIN ILI USER ČITAJU IZ KEYSTORE-A SVE DETALJE SERTIFIKATA
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value="/getCertificateDetails/{serialNumber}", produces = "application/json")
	public CertificateDetailsDTO getCertificateDetails(@PathVariable("serialNumber") String serialNumber) throws CertificateEncodingException, CertificateParsingException {

        return certificateService.getCertificateDetails(serialNumber);
	}
	
	
	//ADMINU SE VRAĆAJU SVI VALIDNI CA SERTIFIKATE DA BI NA FRONTENDU IZABRAO KO ĆE BITI ISSUER-POTPISNIK
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value="/getAllValidCA", produces="application/json")
    public List<CertificateDB> getAllValidCA() throws CertificateEncodingException {

        return certificateService.getAllValidCA();
    }
	
	//USERU SE VRAĆA SERTIFIKAT IZDAT ZA NJEGA DA GA POGLEDA
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value="/getMyCertificate", produces="application/json")
    public List<CertificateDB> getMyCertificate() throws CertificateEncodingException {

        DigEntity entity = entityService.getLoginEntity();
        if(entity == null) {
        	return null;
        }
		
        return this.certificateService.getMyCertificate(entity.getSerialNumberCertificate());
        
    }
	
	
    //ADMIN ILI USER PROVERAVAJU DA LI JE SERTIFIKAT POVUČEN
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value="/checkRevocationStatusOCSP/{serialNumber}")
    public ResponseEntity<Boolean> checkRevocationStatusOCSP(@PathVariable("serialNumber") String serialNumber){

        boolean revoked = this.certificateService.checkRevocationStatusOCSP(serialNumber);
        
        if(revoked) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    
    //ADMIN POVLAČI SERTIFIKAT I SVE KOJE ON POTPISUJE AKO IH IMA ITD...
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value="/revokeCertificate/{serialNumber}")
    public ResponseEntity<Boolean> revokeCertificate(@PathVariable("serialNumber") String serialNumber){
    	
    	boolean revocation = this.certificateService.revokeCertificate(serialNumber);
    	
    	if(revocation) {
    		return new ResponseEntity<>(true, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(false, HttpStatus.OK);
    	}
    }
    
    
    //ADMIN KREIRA SAMOPOTPISNI ROOT SERTIFIKAT
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/createRootCertificate", consumes="application/json")
    public ResponseEntity<?> createRootCertificate(@Valid @RequestBody CertificateDTO certificateDTO) throws CertificateEncodingException, CertificateParsingException {
    	
        boolean certCreated = certificateService.issueRootCertificate(certificateDTO);
        if(certCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    
    //ADMIN KREIRA CA ILI END SERTIFIKAT
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value="/createCAorEndCertificate", consumes="application/json")
    public ResponseEntity<?> CreateCAorEndCertificate(@Valid @RequestBody CertificateDTO certificateDTO) throws CertificateEncodingException,  CertificateParsingException {

        boolean certCreated = certificateService.issueCAorEndCertificate(certificateDTO);
        if(certCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

	//USER PREUZIMA SVOJ SERTIFIKAT
    @GetMapping(value="/downloadCertificate/{serialNumber}")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> downloadCertificate(@PathVariable("serialNumber") String serialNumber) throws CertificateEncodingException{
    	
    	 byte cert[] = certificateService.downloadCertificate(serialNumber);
         return new ResponseEntity<>(cert, HttpStatus.OK);
    }
    
    
    
}
