package org.progressivelifestyle.weedmap.persistence.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.DispensaryDao;
import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemCategoryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DispensaryService {
	@Autowired
	private DispensaryDao dispensaryDao;
	private static final Log logger = LogFactory.getLog(DispensaryService.class);
	public void setDispensaryDao(DispensaryDao dispensaryDao) {
		this.dispensaryDao = dispensaryDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void storeDispensaries(Collection<DispensaryEntity> dispensaries){
		for(DispensaryEntity entity : dispensaries)
			createDispensary(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createDispensary(DispensaryEntity entity){
		dispensaryDao.saveEntity(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEntity(BaseEntity entity){
		dispensaryDao.updateEntity(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public MenuItemCategoryEntity findMenuItemCategory(long categoryId){
		return (MenuItemCategoryEntity)dispensaryDao.getEntityByPrimaryKey(new Long(categoryId), MenuItemCategoryEntity.class);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public DispensaryEntity findDispensary(long dispensaryId){
		return (DispensaryEntity)dispensaryDao.getEntityByPrimaryKey(new Long(dispensaryId), DispensaryEntity.class);
	}
	
	public void createDispensaryAndMenuItemSeperately(Dispensary dispensary) {
		Set<Menu> menuItems = dispensary.getMenuItems();
		dispensary.setMenuItems(null);
		for(Menu menu : menuItems){
			try{
				menu.setDispensary((DispensaryEntity)dispensary);
				createEntityWithIndependentTransaction((MenuItemEntity)menu);
			}catch(Exception e){
				logger.error("Unable to store menu with id: "+menu.getId(), e);
			}
		}
		try{
			createEntityWithIndependentTransaction((DispensaryEntity)dispensary);
		}catch(Exception e){
			logger.error("Unable to store menu with id: "+dispensary.getDispensaryId(), e);
		}
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createEntityWithIndependentTransaction(BaseEntity entity){
		dispensaryDao.saveEntity(entity);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<DispensaryEntity> loadAllDispensaryForCache(){
		return dispensaryDao.loadAllDispensaries();
	}
	
}
