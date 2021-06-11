package ftn.bsep.service;

import java.util.List;

import ftn.bsep.model.DigEntity;

public interface EntityService {

	List<DigEntity> getAllValidEntities();

	DigEntity getLoginEntity();

}
