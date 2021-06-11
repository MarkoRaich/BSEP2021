package ftn.bsep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import ftn.bsep.model.DigEntity;
import ftn.bsep.service.EntityService;

@RestController
@RequestMapping(value = "/api/entities", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityContoller {
	
		@Autowired
		private EntityService entityService;

	
		//VRAĆA SVE ENTITETE BEZ AKTIVNOG SERTIFIKATA DA BI GA NAPRAVIO 
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@GetMapping(value="/getAllValidEntities", produces="application/json")
	    public List<DigEntity> getAllValidEnitities()  {

	        return entityService.getAllValidEntities();
	    }
}
