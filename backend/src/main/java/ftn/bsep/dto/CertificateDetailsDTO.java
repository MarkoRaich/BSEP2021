package ftn.bsep.dto;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import ftn.bsep.model.CertificateDB;

//DTO KLASA ZA SLANJE DETALJA O SERTIFIKATU NA FRONTEND
public class CertificateDetailsDTO {
	
	    private String serialNumber;
	    private int version;
	    private String signatureAlgorithm;
	    private String signatureHashAlgorithm;
	    private String publicKey;
	    private String startDate;
	    private String endDate;
	    private Boolean isRoot;

	    private String subject;
	    private String subjectGivenname;
	    private String subjectSurname;
	    private String subjectEmail;
	    private String subjectCommonName;

	    private String issuer;
	    private String issuerGivenname;
	    private String issuerSurname;
	    private String issuerEmail;
	    private String issuerCommonName;
	    private String issuerSerialNumber;

	    private String type;
	    private List<String> keyUsageList;

	    
	    
		public CertificateDetailsDTO() {
			super();
		}


		public CertificateDetailsDTO(JcaX509CertificateHolder certificateHolder, X509Certificate cert, CertificateDB certDB, Boolean isRoot) throws CertificateParsingException {
	        
			this.serialNumber = certificateHolder.getSerialNumber().toString();
	        this.version =certificateHolder.getVersionNumber();
	        this.signatureAlgorithm = "sha256RSA";
	        this.signatureHashAlgorithm = "sha256";
	        this.publicKey = cert.getPublicKey().toString();
	        this.isRoot = isRoot;

	        String pattern = "dd/MMM/yyyy";
	        DateFormat df = new SimpleDateFormat(pattern);
	        this.startDate = df.format(certificateHolder.getNotBefore());
	        this.endDate = df.format(certificateHolder.getNotAfter());

	        X500Name subject = certificateHolder.getSubject();
	        generateSubject(subject);

	        X500Name issuer = certificateHolder.getIssuer();
	        generateIssuer(issuer);
	        
	        this.issuerSerialNumber = certDB.getSerialNumberIssuer();

	        if(certDB.isCa())
	        {
	            this.type = "CA";
	        }
	        else
	        {
	            this.type = "END-ENTITY";
	        }


	        //key usage
	        this.keyUsageList = new ArrayList<String>();
	        if(cert.getKeyUsage()[0]){
	            keyUsageList.add("Digital Signature");
	        }

	        if(cert.getKeyUsage()[1]){
	            keyUsageList.add("Non Repudiation");
	        }

	        if(cert.getKeyUsage()[2]){
	            keyUsageList.add("Key Encipherment");
	        }

	        if(cert.getKeyUsage()[3]){
	            keyUsageList.add("Data Encipherment");
	        }

	        if(cert.getKeyUsage()[4]){
	            keyUsageList.add("Key Agreement");
	        }

	        if(cert.getKeyUsage()[5]){
	            keyUsageList.add("KeyCert Sign");
	        }

	        if(cert.getKeyUsage()[6]){
	            keyUsageList.add("CRL Sign");
	        }

	        if(cert.getKeyUsage()[7]){
	            keyUsageList.add("Encipher Only");
	        }

	        if(cert.getKeyUsage()[8]){
	            keyUsageList.add("Decipher Only");
	        }


	    }
		
		 private void generateSubject(X500Name subject)
		    {
		        RDN cn;
		        if(subject.getRDNs(BCStyle.E).length > 0) {
		            cn = subject.getRDNs(BCStyle.E)[0];
		            this.subjectEmail = IETFUtils.valueToString(cn.getFirst().getValue());
		        }

		        String temp;
		        if(subject.getRDNs(BCStyle.CN).length > 0) {
		            cn = subject.getRDNs(BCStyle.CN)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.subjectCommonName = temp;
		            this.subject = "CN=" + temp;
		        }
		        if(subject.getRDNs(BCStyle.O).length > 0) {
		            cn = subject.getRDNs(BCStyle.O)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.subject = this.subject + "; O=" + temp;

		        }
		        if(subject.getRDNs(BCStyle.OU).length > 0) {
		            cn = subject.getRDNs(BCStyle.OU)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.subject = this.subject + "; OU=" + temp;
		        }
		        if(subject.getRDNs(BCStyle.ST).length > 0) {
		            cn = subject.getRDNs(BCStyle.ST)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.subject = this.subject + "; ST=" + temp;
		        }
		        if(subject.getRDNs(BCStyle.C).length > 0) {
		            cn = subject.getRDNs(BCStyle.C)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.subject = this.subject + "; C=" + temp;
		        }
		        if(subject.getRDNs(BCStyle.GIVENNAME).length > 0) {
		            cn = subject.getRDNs(BCStyle.GIVENNAME)[0];
		            this.subjectGivenname = IETFUtils.valueToString(cn.getFirst().getValue());
		        }
		        if(subject.getRDNs(BCStyle.SURNAME).length > 0) {
		            cn = subject.getRDNs(BCStyle.SURNAME)[0];
		            this.subjectSurname = IETFUtils.valueToString(cn.getFirst().getValue());
		        }

		    }

		    private void generateIssuer(X500Name issuer)
		    {
		        RDN cn;
		        if(issuer.getRDNs(BCStyle.E).length > 0) {
		            cn = issuer.getRDNs(BCStyle.E)[0];
		            this.issuerEmail = IETFUtils.valueToString(cn.getFirst().getValue());
		        }

		        String temp;
		        if(issuer.getRDNs(BCStyle.CN).length > 0) {
		            cn = issuer.getRDNs(BCStyle.CN)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.issuerCommonName = temp;
		            this.issuer = "CN=" + temp;
		        }
		        if(issuer.getRDNs(BCStyle.O).length > 0) {
		            cn = issuer.getRDNs(BCStyle.O)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.issuer = this.issuer + "; O=" + temp;
		        }
		        if(issuer.getRDNs(BCStyle.OU).length > 0) {
		            cn = issuer.getRDNs(BCStyle.OU)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.issuer = this.issuer + "; OU=" + temp;
		        }
		        if(issuer.getRDNs(BCStyle.ST).length > 0) {
		            cn = issuer.getRDNs(BCStyle.ST)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.issuer = this.issuer + "; ST=" + temp;
		        }
		        if(issuer.getRDNs(BCStyle.C).length > 0) {
		            cn = issuer.getRDNs(BCStyle.C)[0];
		            temp = IETFUtils.valueToString(cn.getFirst().getValue());
		            this.issuer = this.issuer + "; C=" + temp;
		        }
		        if(issuer.getRDNs(BCStyle.GIVENNAME).length > 0) {
		            cn = issuer.getRDNs(BCStyle.GIVENNAME)[0];
		            this.issuerGivenname = IETFUtils.valueToString(cn.getFirst().getValue());
		        }
		        if(issuer.getRDNs(BCStyle.SURNAME).length > 0) {
		            cn = issuer.getRDNs(BCStyle.SURNAME)[0];
		            this.issuerSurname = IETFUtils.valueToString(cn.getFirst().getValue());
		        }
		    }




		public String getSerialNumber() {
			return serialNumber;
		}


		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}


		public int getVersion() {
			return version;
		}


		public void setVersion(int version) {
			this.version = version;
		}


		public String getSignatureAlgorithm() {
			return signatureAlgorithm;
		}


		public void setSignatureAlgorithm(String signatureAlgorithm) {
			this.signatureAlgorithm = signatureAlgorithm;
		}


		public String getSignatureHashAlgorithm() {
			return signatureHashAlgorithm;
		}


		public void setSignatureHashAlgorithm(String signatureHashAlgorithm) {
			this.signatureHashAlgorithm = signatureHashAlgorithm;
		}


		public String getPublicKey() {
			return publicKey;
		}


		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}


		public String getStartDate() {
			return startDate;
		}


		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}


		public String getEndDate() {
			return endDate;
		}


		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}


		public Boolean getIsRoot() {
			return isRoot;
		}


		public void setIsRoot(Boolean isRoot) {
			this.isRoot = isRoot;
		}


		public String getSubject() {
			return subject;
		}


		public void setSubject(String subject) {
			this.subject = subject;
		}


		public String getSubjectGivenname() {
			return subjectGivenname;
		}


		public void setSubjectGivenname(String subjectGivenname) {
			this.subjectGivenname = subjectGivenname;
		}


		public String getSubjectSurname() {
			return subjectSurname;
		}


		public void setSubjectSurname(String subjectSurname) {
			this.subjectSurname = subjectSurname;
		}


		public String getSubjectEmail() {
			return subjectEmail;
		}


		public void setSubjectEmail(String subjectEmail) {
			this.subjectEmail = subjectEmail;
		}


		public String getSubjectCommonName() {
			return subjectCommonName;
		}


		public void setSubjectCommonName(String subjectCommonName) {
			this.subjectCommonName = subjectCommonName;
		}


		public String getIssuer() {
			return issuer;
		}


		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}


		public String getIssuerGivenname() {
			return issuerGivenname;
		}


		public void setIssuerGivenname(String issuerGivenname) {
			this.issuerGivenname = issuerGivenname;
		}


		public String getIssuerSurname() {
			return issuerSurname;
		}


		public void setIssuerSurname(String issuerSurname) {
			this.issuerSurname = issuerSurname;
		}


		public String getIssuerEmail() {
			return issuerEmail;
		}


		public void setIssuerEmail(String issuerEmail) {
			this.issuerEmail = issuerEmail;
		}


		public String getIssuerCommonName() {
			return issuerCommonName;
		}


		public void setIssuerCommonName(String issuerCommonName) {
			this.issuerCommonName = issuerCommonName;
		}


		public String getIssuerSerialNumber() {
			return issuerSerialNumber;
		}


		public void setIssuerSerialNumber(String issuerSerialNumber) {
			this.issuerSerialNumber = issuerSerialNumber;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public List<String> getKeyUsageList() {
			return keyUsageList;
		}


		public void setKeyUsageList(List<String> keyUsageList) {
			this.keyUsageList = keyUsageList;
		}



}
