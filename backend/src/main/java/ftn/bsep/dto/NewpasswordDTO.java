package ftn.bsep.dto;

import javax.validation.constraints.NotEmpty;

public class NewpasswordDTO {
	
	@NotEmpty(message = "Token is empty.")
	private String token;
	
	@NotEmpty(message = "Password is empty.")
	private String password;

	
	
	
	public NewpasswordDTO() {
		super();
	}
	
	

	public NewpasswordDTO(@NotEmpty(message = "Token is empty.") String token,
			@NotEmpty(message = "Password is empty.") String password) {
		super();
		this.token = token;
		this.password = password;
	}



	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
