package ftn.bsep.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import ftn.bsep.model.DigEntity;

public class EntityDTO {

	@NotEmpty(message = "Email is empty.")
	@Email(message = "Email is invalid.")
	private String email;
	
	@NotEmpty(message = "Password is empty.")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")
	private String password;
	
	@NotEmpty(message = "Common name is empty.")
    private String commonName;
    
	@NotEmpty(message = "First Name is empty.")
    private String firstName;
    
	@NotEmpty(message = "Last Name is empty.")
    private String lastName;
    
	@NotEmpty(message = "Organization is empty.")
    private String organization;
    
	@NotEmpty(message = "Organization unit is empty.")
    private String organizationUnit;
    
	@NotEmpty(message = "State is empty.")
    private String state;
    
	@NotEmpty(message = "Country is empty.")
    private String country;
	
	
	public EntityDTO() {
		super();
	}


	public EntityDTO(@NotEmpty(message = "Email is empty.") @Email(message = "Email is invalid.") String email,
			@NotEmpty(message = "Password is empty.") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$") String password,
			@NotEmpty(message = "Common name is empty.") String commonName,
			@NotEmpty(message = "First Name is empty.") String firstName,
			@NotEmpty(message = "Last Name is empty.") String lastName,
			@NotEmpty(message = "Organization is empty.") String organization,
			@NotEmpty(message = "Organization unit is empty.") String organizationUnit,
			@NotEmpty(message = "State is empty.") String state,
			@NotEmpty(message = "Country is empty.") String country) {
		super();
		this.email = email;
		this.password = password;
		this.commonName = commonName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.state = state;
		this.country = country;
	}
	
	public EntityDTO(DigEntity entity) {
		this.email = entity.getUsername();
		this.commonName = entity.getCommonName();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.organization = entity.getOrganization();
		this.organizationUnit = entity.getOrganizationUnit();
		this.state = entity.getState();
		this.country = entity.getCountry();
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCommonName() {
		return commonName;
	}


	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getOrganizationUnit() {
		return organizationUnit;
	}


	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
