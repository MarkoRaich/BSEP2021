package ftn.bsep.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class ConfirmationToken {

	
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="token_id")
	    private long tokenid;

	    @Column(name="confirmation_token")
	    private String confirmationToken;

	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date expiresAt;

	    @OneToOne(targetEntity = DigEntity.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "entity_id")
	    private DigEntity entity;

	    @Transient
	    private boolean isExpired;
	    
	    
	    public ConfirmationToken() {
			super();
		}



		public ConfirmationToken(DigEntity entity) {
	        this.entity = entity;
	        createdDate = new Date();
	        Calendar c = Calendar.getInstance();
	        c.setTime(createdDate);
	        c.add(Calendar.DATE, 1);
	        expiresAt = c.getTime();
	        confirmationToken = UUID.randomUUID().toString();
	    }

		
	


		public Date getExpiresAt() {
			return expiresAt;
		}



		public void setExpiresAt(Date expiresAt) {
			this.expiresAt = expiresAt;
		}



		public boolean isExpired() {
			return getExpiresAt().before(new Date());
		}



		public void setExpired(boolean isExpired) {
			this.isExpired = isExpired;
		}



		public long getTokenid() {
			return tokenid;
		}



		public void setTokenid(long tokenid) {
			this.tokenid = tokenid;
		}



		public String getConfirmationToken() {
			return confirmationToken;
		}



		public void setConfirmationToken(String confirmationToken) {
			this.confirmationToken = confirmationToken;
		}



		public Date getCreatedDate() {
			return createdDate;
		}



		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}



		public DigEntity getEntity() {
			return entity;
		}



		public void setEntity(DigEntity entity) {
			this.entity = entity;
		}
 

	    

}

