package ftn.bsep.dto;

import java.util.ArrayList;
import java.util.List;

import ftn.bsep.utilities.CertificateType;

//DTO KLASA ZA PRIMANJE PODATAKA SA FRONTENDA
public class CertificateDTO {

		private int entityId;
	    private int duration;
	    private String issuerSerialNumber;
	    private CertificateType certificateType;
	    private ArrayList<String> keyUsageList;
	    boolean basicConstraints;
	    
	    
	    
	    
		public CertificateDTO(  int entityId, int duration, String issuerSerialNumber, CertificateType certificateType,
								List<String> extendedKeyUsageList, boolean basicConstraints) {
			
				super();
				this.entityId = entityId;
				this.duration = duration;
				this.issuerSerialNumber = issuerSerialNumber;
				this.certificateType = certificateType;
				this.keyUsageList = new ArrayList<>();
				this.basicConstraints = basicConstraints;
		}
		
		
		public CertificateDTO() {
			super();
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


		public int getEntityId() {
			return entityId;
		}


		public void setEntityId(int entityId) {
			this.entityId = entityId;
		}


		@Override
		public String toString() {
			return "CertificateDTO [entityId=" + entityId + ", duration=" + duration + ", issuerSerialNumber="
					+ issuerSerialNumber + ", certificateType=" + certificateType + ", keyUsageList=" + keyUsageList
					+ ", basicConstraints=" + basicConstraints + "]";
		}


		
	    
	    
	    
}
