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
import org.progressivelifestyle.weedmap.persistence.domain.SearchQueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

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
		DispensaryEntity entity = (DispensaryEntity)dispensaryDao.getEntityByPrimaryKey(new Long(dispensaryId), DispensaryEntity.class);
		/*Set<Menu> menuItems = entity.getMenuItems();
		for(Menu menu : menuItems)
			System.out.println("Category:: "+((MenuItemEntity)menu).getMenuItemCategory());
		System.out.println("Menus ::"+menuItems.size());*/
		return entity;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public MenuItemEntity findMenuItem(long menuItemId){
		return (MenuItemEntity)dispensaryDao.getEntityByPrimaryKey(new Long(menuItemId), MenuItemEntity.class);
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

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void createOrUpdateScore(String searchText, Boolean hasFound) {
		dispensaryDao.createOrUpdateScore(searchText, hasFound);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<SearchQueryEntity> findMostPopularSearchTerms(){
		return dispensaryDao.findMostPopularSearchTerms();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<SearchQueryEntity> findMostPopularSearchTerms(int numbers){
		return dispensaryDao.findMostPopularSearchTerms(numbers);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<String> findDispensariesForMedicine(String name){
		List<MenuItemEntity> menuItemsForName = dispensaryDao.findMenuItemsForName(name);
		List<String> dispensaryIds = Lists.newArrayList();
		for(MenuItemEntity entity : menuItemsForName){
			dispensaryIds.add(entity.getDispensary().getId().toString());
			System.out.println(entity.getDispensary().getRegion());
		}
		return dispensaryIds;
	}
}
