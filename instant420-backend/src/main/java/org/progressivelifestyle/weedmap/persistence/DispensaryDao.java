package org.progressivelifestyle.weedmap.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemCategoryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
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

	@SuppressWarnings("unchecked")
	public void createOrUpdateScore(String searchText, Boolean hasFound) {
		Query query = entityManager.createNamedQuery("findSearchText", SearchQueryEntity.class);
		query.setParameter("queryStr", searchText);
		List<SearchQueryEntity> resultList = query.getResultList();
		SearchQueryEntity entity = null;
		if(!resultList.isEmpty()){
			entity = resultList.iterator().next();
			entity.setCount(entity.getCount()+1);
			updateEntity(entity);
		}else{
			entity = new SearchQueryEntity();
			entity.setQueryStr(searchText);
			entity.setCount(1);
			entity.setHasResult(hasFound.booleanValue());
			saveEntity(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SearchQueryEntity> findMostPopularSearchTerms() {
		Query query = entityManager.createNamedQuery("mostSearchedText", SearchQueryEntity.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SearchQueryEntity> findMostPopularSearchTerms(int numbers) {
		Query query = entityManager.createNamedQuery("mostSearchedText", SearchQueryEntity.class);
		query.setMaxResults(numbers);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuItemEntity> findMenuItemsForName(String name){
		Query query = entityManager.createNamedQuery("findMedicineByName", MenuItemEntity.class);
		query.setParameter("name", name);
		return query.getResultList();
	}

	public Long findMaxDispensaryId() {
		return entityManager.createNamedQuery("findMaxDispensaryId", Long.class).getSingleResult();
	}
	
	public Long findMaxmenuItemId() {
		return entityManager.createNamedQuery("findMaxMenuItemId", Long.class).getSingleResult();
	}

	public MenuItemCategoryEntity findMenuItemCategoryByName(String name) {
		return (MenuItemCategoryEntity)entityManager.createNamedQuery("findCategoryByName").setParameter("name", name).getSingleResult();
	}

}
