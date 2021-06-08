package ftn.bsep.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class CertificateDB {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "serialNumberSubject", nullable = false, unique = true)
    private String serialNumberSubject;

    @Column(name = "serialNumberIssuer", nullable = false)
    private String serialNumberIssuer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "ca", nullable = false)
    private boolean ca;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    

    public CertificateDB(){

    }



	public CertificateDB(String serialNumberSubject, String serialNumberIssuer, Date startDate, Date endDate,
			boolean ca, boolean revoked) {
		super();
		this.serialNumberSubject = serialNumberSubject;
		this.serialNumberIssuer = serialNumberIssuer;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ca = ca;
		this.revoked = revoked;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getSerialNumberSubject() {
		return serialNumberSubject;
	}



	public void setSerialNumberSubject(String serialNumberSubject) {
		this.serialNumberSubject = serialNumberSubject;
	}



	public String getSerialNumberIssuer() {
		return serialNumberIssuer;
	}



	public void setSerialNumberIssuer(String serialNumberIssuer) {
		this.serialNumberIssuer = serialNumberIssuer;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public boolean isCa() {
		return ca;
	}



	public void setCa(boolean ca) {
		this.ca = ca;
	}



	public boolean isRevoked() {
		return revoked;
	}



	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

   

   

}
