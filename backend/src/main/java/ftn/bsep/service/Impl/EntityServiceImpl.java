package ftn.bsep.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ftn.bsep.model.DigEntity;
import ftn.bsep.repository.EntityRepository;
import ftn.bsep.service.EntityService;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	private EntityRepository entityRepository;
	
	@Override
	public List<DigEntity> getAllValidEntities() {
		
		return entityRepository.findByHasActiveCertificate(false);
	}

	@Override
	public DigEntity getLoginEntity() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        try {
        	//System.out.println(currentUser.getName());
        	DigEntity entity = entityRepository.findOneByUsername(currentUser.getName());
            if (entity != null) {
                return entity;
            }
        } catch (UsernameNotFoundException ex) {

        }
        return null;
	}

}
