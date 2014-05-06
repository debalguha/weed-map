package org.instant420.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/operation/dispensary")
public class DispensaryController {
	private static final Log logger = LogFactory.getLog(MedicineController.class);
	@Autowired
	private DispensaryService service;

	public void setService(DispensaryService service) {
		this.service = service;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody APIResponse createDispensary(@RequestBody DispensaryEntity dispensary){
		try {
			logger.info("Dispensary create request");
			service.createDispensary(dispensary);
			logger.info("Successfully stored!");
			return new APIResponse(dispensary.getId(), "SUCCESS", null);
		} catch (Exception e) {
			logger.error("Unable to create menu item.", e);
			return new APIResponse(null, "Unable to create medicine", e);
		}
	}
	
	/*@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody APIResponse updateMedicine(@RequestBody MenuItemSearchObject menuSearch){
		try {
			logger.info("Menu item update request, proceeding to transform");
			MenuItemEntity menu = buildEntityFromSearchObject(menuSearch);
			MenuItemEntity retrievedMenu = service.findMenuItem(menu.getId());
			logger.info("Menu item Obtained: "+(retrievedMenu==null?null:retrievedMenu.getName()));
			BeanUtils.copyProperties(menu, retrievedMenu, "dispensary", "lat", "lang", "region");
			logger.info("Proceeding to store using service");
			service.updateMenuItem(retrievedMenu);
			logger.info("Successfully stored!");
			return new APIResponse(menu.getId(), "SUCCESS", null);
		} catch (Exception e) {
			logger.error("Unable to update menu item.", e);
			return new APIResponse(null, "Unable to update medicine", e);
		}
	}*/
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody APIResponse deleteDispensary(@PathVariable Long id){
		try {
			logger.info("Menu item delete request: "+id);
			service.removeDispensary(id);
			logger.info("Successfully removed!");
			return new APIResponse(id, "SUCCESS", null);
		} catch (Exception e) {
			logger.error("Unable to delete menu item.", e);
			return new APIResponse(null, "Unable to delete medicine", e);
		}
	}
}
