package org.progressivelifestyle.weedmap.persistence;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DispensaryDao {
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void saveEntity(BaseEntity model){
		Date nowDate = new Date();
		model.setLastUpdateDate(nowDate);
		if(model.getCreationDate()==null)
			model.setCreationDate(nowDate);
		entityManager.persist(model);
	}
	
	public void updateEntity(BaseEntity model){
		Date nowDate = new Date();
		model.setLastUpdateDate(nowDate);
		entityManager.merge(model);
	}
}
