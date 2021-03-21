package ftn.bsep.dto;

public class IssuerDTO {

	
	 	private String serialNumber;
	    private String commonName;
	    private String surname;
	    private String givenName;
	    private String org;
	    private String orgUnit;
	    private String country;
	    private String email;
	    
	    
		public IssuerDTO() {
			super();
		}


		public IssuerDTO(String serialNumber, String commonName, String surname, String givenName, String org,
				String orgUnit, String country, String email) {
			super();
			this.serialNumber = serialNumber;
			this.commonName = commonName;
			this.surname = surname;
			this.givenName = givenName;
			this.org = org;
			this.orgUnit = orgUnit;
			this.country = country;
			this.email = email;
		}


		public String getSerialNumber() {
			return serialNumber;
		}


		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}


		public String getCommonName() {
			return commonName;
		}


		public void setCommonName(String commonName) {
			this.commonName = commonName;
		}


		public String getSurname() {
			return surname;
		}


		public void setSurname(String surname) {
			this.surname = surname;
		}


		public String getGivenName() {
			return givenName;
		}


		public void setGivenName(String givenName) {
			this.givenName = givenName;
		}


		public String getOrg() {
			return org;
		}


		public void setOrg(String org) {
			this.org = org;
		}


		public String getOrgUnit() {
			return orgUnit;
		}


		public void setOrgUnit(String orgUnit) {
			this.orgUnit = orgUnit;
		}


		public String getCountry() {
			return country;
		}


		public void setCountry(String country) {
			this.country = country;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}
	    
		
	    
}
