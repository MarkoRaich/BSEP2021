

-- ROLE I PRAVA PRISTUPA

INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

/*
	ADMINISTRATORI
	SIFRA ZA ADMINE : 123
		  
 *  KADA PRODJE 10X KROZ BCRYPT ENKODER POSTANE NPR:
 * '$2y$10$nFBgXdogpVob62c1sBhSfu89WSZ91IiX3YooxyDHSWRqIDUxRniim ' sadrzi verziju, salt i hash! Salt se ne cuva posebno!!
 	 
**/
INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin1@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Djanluka', 'Paljuka');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (1,1);

INSERT INTO admin (username, password, first_name, last_name) VALUES ('admin2@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Fabio', 'Materaci');
INSERT INTO admin_authority(admin_id, authority_id) VALUES (2,1);

/*
	ENTITETI
	SIFRA ZA ENTITETE : 123
 	 
**/
INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('markoraich@gmail.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'JovanJovanovic','Jovan','Jovanovic','Company Inc','Sales','Oklahoma','USA', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (1,2);

INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('entitet2@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'MarkoMarkovic','Marko','Markovic','Company Inc','Finance','Paris','France', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (2,2);

INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('entitet3@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'MilanaMilanovic','Milana','Milanovic','Company Inc', 'HR','California','USA', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (3,2);

INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('entitet4@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'PetarPetrovic','Petar','Petrovic','Company Inc','Operation','Oklahoma','USA', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (4,2);

INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('entitet5@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'Zoran Zoranovic','Zoran','Zoranovic','Company Inc','Finance','Paris','France', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (5,2);

INSERT INTO dig_entity(username, password, common_name, first_name, last_name, organization, organization_unit, state, country, security_question, security_answer, has_active_certificate, serial_number_certificate, is_enabled) 
VALUES ('entitet6@email.com', '$2a$10$B4v5S4SzMqwosun/E6d4Ouf..gi3uXhdJ5i99Yl6CumNdNL8XFJD.', 'StefaStefanovic','Milana','Stefanovic','Company Inc', 'Marketing','California','USA', 'omiljena boja', 'plava', false, null, true);
INSERT INTO entity_authority(entity_id, authority_id) VALUES (6,2);










