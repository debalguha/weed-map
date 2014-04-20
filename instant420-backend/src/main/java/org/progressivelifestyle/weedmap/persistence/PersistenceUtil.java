package org.progressivelifestyle.weedmap.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.progressivelifestyle.weedmap.persistence.domain.Menu;

import com.google.common.collect.Sets;

public class PersistenceUtil {
	public static Map<Long, Menu> indexBaseEntityCollection(Collection<Menu> entities){
		Map<Long, Menu> entityMap = new HashMap<Long, Menu>();
		for(Menu entity : entities)
			entityMap.put(entity.getId(), entity);
		return entityMap;
	}
	public static Set<Menu> findUpdatedMenuItems(Collection<Menu> menuIemsLeft, Collection<Menu> menuIemsRight){
		Set<Menu> menuItems = Sets.newHashSet();
		Map<Long, Menu> entityMapLeft = PersistenceUtil.indexBaseEntityCollection(menuIemsLeft);
		Map<Long, Menu> entityMapRight = PersistenceUtil.indexBaseEntityCollection(menuIemsRight);
		Set<Long> intersection = Sets.intersection(entityMapLeft.keySet(), entityMapRight.keySet());
		for(Long id : intersection){
			Menu menuItemLeft = entityMapLeft.get(id);
			Menu menuItemRight = entityMapRight.get(id);
			if(!menuItemLeft.isLogicallyEquals(menuItemRight))
				menuItems.add(menuItemRight);
		}
		return menuItems;
	}
	
	public static Set<Menu> findNewlyAddedMenuItems(Set<Menu> menuIemsLeft, Set<Menu> menuIemsRight){
		return Sets.difference(menuIemsRight, menuIemsLeft);
	}
}
