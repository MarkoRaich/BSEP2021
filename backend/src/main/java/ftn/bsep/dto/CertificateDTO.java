package ftn.bsep.dto;

import java.util.ArrayList;
import java.util.List;

import ftn.bsep.utilities.CertificateType;

//DTO KLASA ZA PRIMANJE PODATAKA SA FRONTENDA
public class CertificateDTO {

		private String subjectCommonName;
	    private String subjectFirstName;
	    private String subjectLastName;
	    private String subjectEmail;
	    private String subjectOrganization;
	    private String subjectOrganizationUnit;
	    private String subjectState;
	    private String subjectCountry;
	    private int duration;
	    private String issuerSerialNumber;
	    private CertificateType certificateType;
	    private ArrayList<String> keyUsageList;
	    boolean basicConstraints;
	    
	    
	    
	    
		public CertificateDTO(  String subjectCommonName, String subjectFirstName, String subjectLastName,
								String subjectEmail, String subjectOrganization, String subjectOrganizationUnit, String subjectState,
								String subjectCountry, String startDate, String endDate, int duration, String issuerSerialNumber,
								CertificateType certificateType, List<String> extendedKeyUsageList,
								int typeSAN, String valueSAN, boolean basicConstraints) {
			
				super();
				this.subjectCommonName = subjectCommonName;
				this.subjectFirstName = subjectFirstName;
				this.subjectLastName = subjectLastName;
				this.subjectEmail = subjectEmail;
				this.subjectOrganization = subjectOrganization;
				this.subjectOrganizationUnit = subjectOrganizationUnit;
				this.subjectState = subjectState;
				this.subjectCountry = subjectCountry;
				this.duration = duration;
				this.issuerSerialNumber = issuerSerialNumber;
				this.certificateType = certificateType;
				this.keyUsageList = new ArrayList<>();
				this.basicConstraints = basicConstraints;
		}
		
		
		public CertificateDTO() {
			super();
		}
		
		
		public String getSubjectCommonName() {
			return subjectCommonName;
		}
		public void setSubjectCommonName(String subjectCommonName) {
			this.subjectCommonName = subjectCommonName;
		}
		public String getSubjectFirstName() {
			return subjectFirstName;
		}
		public void setSubjectFirstName(String subjectFirstName) {
			this.subjectFirstName = subjectFirstName;
		}
		public String getSubjectLastName() {
			return subjectLastName;
		}
		public void setSubjectLastName(String subjectLastName) {
			this.subjectLastName = subjectLastName;
		}
		public String getSubjectEmail() {
			return subjectEmail;
		}
		public void setSubjectEmail(String subjectEmail) {
			this.subjectEmail = subjectEmail;
		}
		public String getSubjectOrganization() {
			return subjectOrganization;
		}
		public void setSubjectOrganization(String subjectOrganization) {
			this.subjectOrganization = subjectOrganization;
		}
		public String getSubjectOrganizationUnit() {
			return subjectOrganizationUnit;
		}
		public void setSubjectOrganizationUnit(String subjectOrganizationUnit) {
			this.subjectOrganizationUnit = subjectOrganizationUnit;
		}
		public String getSubjectState() {
			return subjectState;
		}
		public void setSubjectState(String subjectState) {
			this.subjectState = subjectState;
		}
		public String getSubjectCountry() {
			return subjectCountry;
		}
		public void setSubjectCountry(String subjectCountry) {
			this.subjectCountry = subjectCountry;
		}
		public String getIssuerSerialNumber() {
			return issuerSerialNumber;
		}
		public void setIssuerSerialNumber(String issuerSerialNumber) {
			this.issuerSerialNumber = issuerSerialNumber;
		}
		public CertificateType getCertificateType() {
			return certificateType;
		}
		public void setCertificateType(CertificateType certificateType) {
			this.certificateType = certificateType;
		}
		public ArrayList<String> getKeyUsageList() {
			return keyUsageList;
		}
		public void setKeyUsageList(ArrayList<String> keyUsageList) {
			this.keyUsageList = keyUsageList;
		}
		public boolean isBasicConstraints() {
			return basicConstraints;
		}
		public void setBasicConstraints(boolean basicConstraints) {
			this.basicConstraints = basicConstraints;
		}


		public int getDuration() {
			return duration;
		}


		public void setDuration(int duration) {
			this.duration = duration;
		}


		@Override
		public String toString() {
			return "CertificateDTO [subjectCommonName=" + subjectCommonName + ", subjectFirstName=" + subjectFirstName
					+ ", subjectLastName=" + subjectLastName + ", subjectEmail=" + subjectEmail
					+ ", subjectOrganization=" + subjectOrganization + ", subjectOrganizationUnit="
					+ subjectOrganizationUnit + ", subjectState=" + subjectState + ", subjectCountry=" + subjectCountry
					+ ", duration=" + duration + ", issuerSerialNumber=" + issuerSerialNumber + ", certificateType="
					+ certificateType + ", keyUsageList=" + keyUsageList + ", basicConstraints=" + basicConstraints
					+ "]";
		}
	    
	    
	    
}
