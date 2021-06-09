

-- ROLE I PRAVA PRISTUPA

INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

/*
	SIFRA ZA ADMIN1 : admin1$123
		  ZA ADMIN2 : admin2NS021
		  
 *  KADA PRODJE 10X KROZ BCRYPT ENKODER POSTANE NPR:
 * '$2y$10$nFBgXdogpVob62c1sBhSfu89WSZ91IiX3YooxyDHSWRqIDUxRniim ' sadrzi verziju, salt i hash! Salt se ne cuva posebno!!
 	 
**/


INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin1@email.com', '$2a$10$MrXWL9QD3QPePmqSXe2Y5uJzsuWOxg6P27/GG4RKaVOskOIM.FyM.', 'Djanluka', 'Paljuka');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (1,1);

INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin2@email.com', '$2a$10$Kv7izDIEqrrCRI/W54sJguXF4qzoclDM2enl0rR1jbRj.bc.vAfE6', 'Fabio', 'Materaci');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (2,1);