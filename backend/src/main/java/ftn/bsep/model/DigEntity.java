package ftn.bsep.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DigEntity implements UserDetails {

	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Username cannot be null.") //username je email adresa
    @Column(nullable = false)
	private String username;

    @JsonIgnore
    @NotNull(message = "Password cannot be null.")
    @Column(nullable = false)
    private String password;
    
    @NotNull(message = "Common name cannot be null.")
    @Column(nullable = false)
    private String commonName;
    
    @NotNull(message = "First name cannot be null.")
    @Column(nullable = false)
    private String firstName;
    
    @NotNull(message = "Last name cannot be null.")
    @Column(nullable = false)
    private String lastName;
    
    
    @NotNull(message = "Organization cannot be null.")
    @Column(nullable = false)
    private String organization;
    
    @NotNull(message = "Organization unit cannot be null.")
    @Column(nullable = false)
    private String organizationUnit;
    
    @NotNull(message = "State cannot be null.")
    @Column(nullable = false)
    private String state;
    
    @NotNull(message = "Country cannot be null.")
    @Column(nullable = false)
    private String country;
    
    @NotNull(message = "Question cannot be null.")
    @Column(nullable = false)
    private String securityQuestion;
    
    @NotNull(message = "Answer cannot be null.")
    @Column(nullable = false)
    private String securityAnswer;
    
    @Column(nullable=false)
    private boolean hasActiveCertificate;
	
    @Column
    private String serialNumberCertificate;
    
    @Column(nullable=false)
    private boolean isEnabled;
    
	
  //vezano za prava pristupa spring security
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "entity_authority",
            joinColumns = @JoinColumn(name = "entity_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;
    
    
    
    public DigEntity() {
    	super();
    }
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
		
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return isEnabled;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}


	public boolean isHasActiveCertificate() {
		return hasActiveCertificate;
	}


	public void setHasActiveCertificate(boolean hasActiveCertificate) {
		this.hasActiveCertificate = hasActiveCertificate;
	}


	public String getSerialNumberCertificate() {
		return serialNumberCertificate;
	}


	public void setSerialNumberCertificate(String certNumber) {
		 this.serialNumberCertificate = certNumber;
	}


	public String getSecurityQuestion() {
		return securityQuestion;
	}


	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}


	public String getSecurityAnswer() {
		return securityAnswer;
	}


	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	@Override
	public String toString() {
		return "DigEntity [id=" + id + ", username=" + username + ", password=" + password + ", commonName="
				+ commonName + ", firstName=" + firstName + ", lastName=" + lastName + ", organization=" + organization
				+ ", organizationUnit=" + organizationUnit + ", state=" + state + ", country=" + country
				+ ", securityQuestion=" + securityQuestion + ", securityAnswer=" + securityAnswer
				+ ", hasActiveCertificate=" + hasActiveCertificate + ", serialNumberCertificate="
				+ serialNumberCertificate + ", authorities=" + authorities + "]";
	}


	
	
	
}
