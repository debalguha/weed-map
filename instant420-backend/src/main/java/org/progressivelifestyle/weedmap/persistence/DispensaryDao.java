package org.progressivelifestyle.weedmap.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getEntityByPrimaryKey(Object primaryKey, Class clazz) {
		return entityManager.find(clazz, primaryKey);
	}

	@SuppressWarnings("unchecked")
	public List<DispensaryEntity> loadAllDispensaries() {
		Query query = entityManager.createQuery("select d from DispensaryEntity d");
		return query.getResultList();
	}
}
