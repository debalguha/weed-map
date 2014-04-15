package org.progressivelifestyle.weedmaps.processor;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.progressivelifestyle.weedmaps.objects.ChangeIndicatingDispensaryObject;
import org.progressivelifestyle.weedmaps.objects.Dispensary;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.objects.MenuItem;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class WeedmapScraperCache {
	private static Set<Dispensary> dispensaryCache;
	private static WeedmapScraperCache me;
	protected WeedmapScraperCache(){
		dispensaryCache = new HashSet<Dispensary>();
		initCache();
	}
	private void initCache() {
		//new Thread(this).start();		
	}
	public static WeedmapScraperCache getInstance(Collection<DispensaryObject> dispensariesAlreadyObtainedFromSpreadsheet){
		if(me==null)
			me = new WeedmapScraperCache();
		dispensaryCache.addAll(dispensariesAlreadyObtainedFromSpreadsheet);
		return me;
	}
	public void addToCache(DispensaryObject obj){
		try{
			dispensaryCache.add(obj);
		}catch(Exception e){
			e.printStackTrace();
		} finally{
		}
	}
	public boolean isPresent(Dispensary obj) throws RuntimeException{
		return dispensaryCache.contains(obj);
	}
	public boolean hasChanged(Dispensary obj){
		return false;
	}
	public Set<Dispensary> findUpdatedDispensaries(Set<Dispensary> scrapedDispensaries){
		Set<Dispensary> intersection = Sets.intersection(dispensaryCache, scrapedDispensaries);
		Map<Long, Dispensary> indexedScrapedDispensaries = indexDispensaryObjects(scrapedDispensaries);
		Map<Long, Dispensary> indexedCachedDispensaries = indexDispensaryObjects(dispensaryCache);
		Set<Dispensary> updatedDispensaries = new HashSet<Dispensary>();
		Set<Dispensary> dispensariesToRemoveFromCache = new HashSet<Dispensary>();
		for(Dispensary dispensaryFromIntersection : intersection){
			Dispensary cachedDispensary = indexedCachedDispensaries.get(dispensaryFromIntersection.getDispensaryId());
			Dispensary scrapedDispensary = indexedScrapedDispensaries.get(dispensaryFromIntersection.getDispensaryId());
			ChangeIndicatingDispensaryObject changedDispensary = new ChangeIndicatingDispensaryObject(scrapedDispensary);
			if(!cachedDispensary.equals(scrapedDispensary)){
				changedDispensary.setChangeInDispensary(true);
				findChangesInAnyofTheMenuItems(cachedDispensary, scrapedDispensary, changedDispensary);
				updatedDispensaries.add(changedDispensary);
				dispensariesToRemoveFromCache.add(cachedDispensary);
			}
		}
		dispensaryCache.removeAll(dispensariesToRemoveFromCache);
		return updatedDispensaries;
	}
	private void findChangesInAnyofTheMenuItems(Dispensary cachedDispensary, Dispensary scrapedDispensary, ChangeIndicatingDispensaryObject changedDispensary) {
		Set<MenuItem> cachedMenuItems = cachedDispensary.getMenuItems();
		Set<MenuItem> scrapedMenuItems = scrapedDispensary.getMenuItems();
		
		Set<MenuItem> menuItemsToBeAdded = Sets.difference(scrapedMenuItems, cachedMenuItems);
		Set<MenuItem> menuItemsToBeRemoved = Sets.difference(cachedMenuItems, scrapedMenuItems);
		Set<MenuItem> menuItemstobeUpdated = findIfAnyOfTheMenuItemsChanged(Sets.intersection(cachedMenuItems, scrapedMenuItems), cachedMenuItems, scrapedMenuItems);
		changedDispensary.setMenuItemsToBeAdded(menuItemsToBeAdded);
		changedDispensary.setMenuItemsToBeRemoved(menuItemsToBeRemoved);
		changedDispensary.setMenuItemstobeUpdated(menuItemstobeUpdated);
	}
	private Set<MenuItem> findIfAnyOfTheMenuItemsChanged(SetView<MenuItem> intersection, Set<MenuItem> cachedMenuItems, Set<MenuItem> scrapedMenuItems) {
		Set<MenuItem> menuItemsChanged = new HashSet<MenuItem>();
		Map<Long, MenuItem> indexedCachedMenuItem = indexMenuItems(cachedMenuItems);
		Map<Long, MenuItem> indexedScrapedMenuItem = indexMenuItems(scrapedMenuItems);
		for(MenuItem menuItemFromIntersection : intersection){
			MenuItem cachedMenuItem = indexedCachedMenuItem.get(menuItemFromIntersection.getId());
			MenuItem scrapedMenuItem = indexedScrapedMenuItem.get(menuItemFromIntersection.getId());
			if(cachedMenuItem.equals(scrapedMenuItem))
				menuItemsChanged.add(scrapedMenuItem);
		}
		return menuItemsChanged;
	}
	public static Map<Long, Dispensary> indexDispensaryObjects(Collection<Dispensary> scrapedDispensaries) {
		Map<Long, Dispensary> index = new HashMap<Long, Dispensary>(scrapedDispensaries.size());
		for(Dispensary dispensary : scrapedDispensaries)
			index.put(dispensary.getDispensaryId(), dispensary);
		return index;
	}
	public static Map<Long, MenuItem> indexMenuItems(Collection<MenuItem> menuItems) {
		Map<Long, MenuItem> index = new HashMap<Long, MenuItem>(menuItems.size());
		for(MenuItem menuItem : menuItems)
			index.put(menuItem.getId(), menuItem);
		return index;
	}
	public Set<Dispensary> findDispensariesNoLongerAvailable(Set<Dispensary> scrapedDispensaries){
		return Sets.difference(dispensaryCache, scrapedDispensaries);
	}
	public Set<Dispensary> findNewlyAddedDispensaries(Set<Dispensary> scrapedDispensaries){
		return Sets.difference(scrapedDispensaries, dispensaryCache);
	}
	public static Set<Dispensary> getDispensaryCache() {
		return dispensaryCache;
	}
	
}
