package ftn.bsep.controller;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.dto.CertificateBasicDTO;
import ftn.bsep.dto.CertificateDTO;
import ftn.bsep.dto.CertificateDetailsDTO;
import ftn.bsep.dto.IssuerDTO;
import ftn.bsep.model.CertificateDB;
import ftn.bsep.service.CertificateService;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
	
	@Autowired
	private CertificateService certificateService;
	
	
	
	
	@GetMapping(value="/getAllCertificates", produces="application/json")	
    public List<CertificateDB> getAllCertificates() {
			
	        return certificateService.getAllCertificates();
	    }

	
	@GetMapping(value="/getAllValidCA", produces="application/json")
    public List<IssuerDTO> getAllValidCA() throws CertificateEncodingException {

        return certificateService.getAllValidCA();
    }
	
	@GetMapping(value="/getCertificateDetails/{serialNumber}", produces = "application/json")
	public CertificateDetailsDTO getCertificateDetails(@PathVariable("serialNumber") String serialNumber) throws CertificateEncodingException, CertificateParsingException {

        return certificateService.getCertificateDetails(serialNumber);
	}
	
	
    @PostMapping(value="/issueCertificate", consumes="application/json")
    public ResponseEntity<?> issueCertificate(@RequestBody CertificateDTO certificateDTO) throws CertificateEncodingException {

        boolean certCreated = certificateService.issueCertificate(certificateDTO);
        if(certCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @GetMapping(value="/checkRevocationStatusOCSP/{serialNumber}")
    public ResponseEntity<Boolean> checkRevocationStatusOCSP(@PathVariable("serialNumber") String serialNumber){

        boolean revoked = this.certificateService.checkRevocationStatusOCSP(serialNumber);
        if(revoked) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
