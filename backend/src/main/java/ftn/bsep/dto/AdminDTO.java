package ftn.bsep.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import ftn.bsep.model.Admin;

public class AdminDTO {

	@NotEmpty(message = "Email is empty.")
	@Email(message = "Email is invalid.")
	private String email;
	
	@NotEmpty(message = "Password is empty.")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")
	private String password;
	
	@NotEmpty(message = "First name is empty.")
	private String firstName;
	
	@NotEmpty(message = "Last name is empty.")
	private String lastName;

	
	
	
	public AdminDTO() {
		super();
	}

	public AdminDTO(@NotEmpty(message = "Email is empty.") @Email(message = "Email is invalid.") String email,
					@NotEmpty(message = "Password is empty.") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$") String password,
					@NotEmpty(message = "First name is empty.") String firstName,
					@NotEmpty(message = "Last name is empty.") String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public AdminDTO(Admin admin) {
		this.email = admin.getUsername();
		this.firstName=admin.getFirstName();
		this.lastName = admin.getLastName();		
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
	 
	 
	
	
	 
	
}
