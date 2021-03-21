package ftn.bsep.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;




public class CertificateBasicDTO {

	private String serialNumber;
	private String startDate;
	private String endDate;
	private String subject;
	
	public CertificateBasicDTO() {
		super();
	}

	public CertificateBasicDTO(String serialNumber, String startDate, String endDate, String subject) {
		super();
		this.serialNumber = serialNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subject = subject;
	}

	public CertificateBasicDTO(JcaX509CertificateHolder certificateHolder) {
		
		X500Name subject = certificateHolder.getSubject();
        String temp;
        RDN cn;
        if(subject.getRDNs(BCStyle.CN).length > 0) {
            cn = subject.getRDNs(BCStyle.CN)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
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
        if(subject.getRDNs(BCStyle.C).length > 0) {
            cn = subject.getRDNs(BCStyle.C)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            this.subject = this.subject + "; C=" + temp;
        }

        String pattern = "dd/MMM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        this.startDate = df.format(certificateHolder.getNotBefore());
        this.endDate = df.format(certificateHolder.getNotAfter());
        this.serialNumber=certificateHolder.getSerialNumber().toString();
        
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
