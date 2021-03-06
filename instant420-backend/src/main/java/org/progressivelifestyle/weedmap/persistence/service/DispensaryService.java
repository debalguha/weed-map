package org.progressivelifestyle.weedmap.persistence.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

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

import com.google.common.collect.Maps;

@Service
@Transactional
public class DispensaryService {
	@Autowired
	private DispensaryDao dispensaryDao;

	private AtomicLong lastDispensaryId;
	private AtomicLong lastMenuItemId;

	private static final Log logger = LogFactory.getLog(DispensaryService.class);

	public void setDispensaryDao(DispensaryDao dispensaryDao) {
		this.dispensaryDao = dispensaryDao;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void storeDispensaries(Collection<DispensaryEntity> dispensaries) {
		for (DispensaryEntity entity : dispensaries)
			createDispensary(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<? extends BaseEntity> findAll(Class<? extends BaseEntity> _clazz){
		return dispensaryDao.findAll(_clazz);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void createDispensary(DispensaryEntity entity) {
		if (entity.getId() == null) {
			if (lastMenuItemId == null)
				afterPropertiesSet();
			entity.setId(lastDispensaryId.incrementAndGet());
		}
		if(entity.getMenus()!=null && !entity.getMenus().isEmpty()){
			populateMenuItemsWithIdAndAssociateDispensary(entity.getMenus(), entity);
		}
		dispensaryDao.saveEntity(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDispensary(DispensaryEntity entity){
		if(entity.getMenus()!=null && !entity.getMenus().isEmpty()){
			populateMenuItemsWithIdAndAssociateDispensary(entity.getMenus(), entity);
		}
		dispensaryDao.updateEntity(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDispensary(Long dispensaryId){
		DispensaryEntity dispensary = findDispensary(dispensaryId);
		Set<MenuItemEntity> menus = dispensary.getMenus();
		if(menus!=null){
			for(MenuItemEntity menu : menus){
				menu.setDispensary(null);
				dispensaryDao.deleteEntity(menu);
			}
		}
		dispensary.setMenus(null);
		dispensaryDao.deleteEntity(dispensary);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createMenuItem(MenuItemEntity entity) {
		if (entity.getId() == null) {
			if (lastMenuItemId == null)
				afterPropertiesSet();
			entity.setId(lastMenuItemId.incrementAndGet());
		}
		dispensaryDao.saveEntity(entity);
		/*for(Menu menu : entity.getMenuItems())
			dispensaryDao.saveEntity((MenuItemEntity)menu);*/
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEntity(BaseEntity entity) {
		dispensaryDao.updateEntity(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MenuItemCategoryEntity findMenuItemCategory(long categoryId) {
		return (MenuItemCategoryEntity) dispensaryDao.getEntityByPrimaryKey(new Long(categoryId), MenuItemCategoryEntity.class);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public DispensaryEntity findDispensary(long dispensaryId) {
		DispensaryEntity entity = (DispensaryEntity) dispensaryDao.getEntityByPrimaryKey(new Long(dispensaryId), DispensaryEntity.class);
		return entity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MenuItemEntity findMenuItem(long menuItemId) {
		MenuItemEntity menuItem = (MenuItemEntity) dispensaryDao.getEntityByPrimaryKey(new Long(menuItemId), MenuItemEntity.class);
		menuItem.getDispensary();
		return menuItem;
	}

	public void createDispensaryAndMenuItemSeperately(Dispensary dispensary) {
		Set<Menu> menuItems = dispensary.getMenuItems();
		dispensary.setMenuItems(null);
		for (Menu menu : menuItems) {
			try {
				menu.setDispensary((DispensaryEntity) dispensary);
				createEntityWithIndependentTransaction((MenuItemEntity) menu);
			} catch (Exception e) {
				logger.error("Unable to store menu with id: " + menu.getId(), e);
			}
		}
		try {
			createEntityWithIndependentTransaction((DispensaryEntity) dispensary);
		} catch (Exception e) {
			logger.error("Unable to store menu with id: " + dispensary.getDispensaryId(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createEntityWithIndependentTransaction(BaseEntity entity) {
		dispensaryDao.saveEntity(entity);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DispensaryEntity> loadAllDispensaryForCache() {
		return dispensaryDao.loadAllDispensaries();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createOrUpdateScore(String searchText, Boolean hasFound) {
		dispensaryDao.createOrUpdateScore(searchText, hasFound);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<SearchQueryEntity> findMostPopularSearchTerms() {
		return findMostPopularSearchTerms(10, null);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<SearchQueryEntity> findMostPopularSearchTerms(int numbers, String searchText) {
		return dispensaryDao.findMostPopularSearchTerms(numbers, searchText);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public List<MenuItemEntity> findMenuItemsByName(String menuItemName){
		return dispensaryDao.findMenuItemsForName(menuItemName);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Map<String, MenuItemEntity> findDispensariesForMedicine(String name) {
		List<MenuItemEntity> menuItemsForName = findMenuItemsByName(name);
		Map<String, MenuItemEntity> dispensaryMenuItem = Maps.newHashMap();
		for (MenuItemEntity entity : menuItemsForName) 
			dispensaryMenuItem.put(entity.getDispensary().getId().toString(), entity);
		
		return dispensaryMenuItem;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MenuItemCategoryEntity findMenuItemCategoryByName(String name) {
		return dispensaryDao.findMenuItemCategoryByName(name);
	}

	public MenuItemCategoryEntity findMenuItemSubCategoryByName(String subCategoryName) {
		return dispensaryDao.findMenuItemSubCategoryByName(subCategoryName);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Long findMaxDispensaryId() {
		return dispensaryDao.findMaxDispensaryId();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Long findMaxMenuItemId() {
		return dispensaryDao.findMaxmenuItemId();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMenuItem(MenuItemEntity menu) {
		dispensaryDao.updateEntity(menu);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void removeMenuItem(Long id) {
		MenuItemEntity menuItem = findMenuItem(id);
		DispensaryEntity dispensary = menuItem.getDispensary();
		menuItem.setDispensary(null);
		dispensary.getMenuItems().remove(menuItem);
		dispensaryDao.updateEntity(dispensary);
		dispensaryDao.deleteEntity(menuItem);
	}
	
	@PostConstruct
	public void afterPropertiesSet() {
		lastDispensaryId = new AtomicLong(findMaxDispensaryId());
		lastMenuItemId = new AtomicLong(findMaxMenuItemId());
	}

	public AtomicLong getLastMenuItemId() {
		return lastMenuItemId;
	}
	
	private void populateMenuItemsWithIdAndAssociateDispensary(Set<MenuItemEntity> menuItems, DispensaryEntity dispensary){
		for(MenuItemEntity menu : menuItems){
			if(menu.getId() == null)
				menu.setId(lastMenuItemId.incrementAndGet());
			menu.setDispensary(dispensary);
		}
		dispensary.setMenus(menuItems);
	}

}
