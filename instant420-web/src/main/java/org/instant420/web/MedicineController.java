package org.instant420.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/operation")
public class MedicineController {
	private static final Log logger = LogFactory.getLog(MedicineController.class);
	@Autowired
	private DispensaryService service;

	public void setService(DispensaryService service) {
		this.service = service;
	}
	
	@RequestMapping(value = "/create/medicine/{dispensaryId}", method = RequestMethod.POST)
	public @ResponseBody APIResponse createMedicine(@RequestBody MenuItemEntity menu, @PathVariable long dispensaryId){
		try {
			logger.info("Menu item create request");
			DispensaryEntity dispensary = service.findDispensary(dispensaryId);
			logger.info("Dispensary Obtained: "+dispensary==null?null:dispensary.getName());
			menu.setDispensary(dispensary);
			logger.info("Proceeding to store using service");
			service.createMenuItem(menu);
			logger.info("Successfully stored!");
			return new APIResponse("SUCCESS", null);
		} catch (Exception e) {
			logger.error("Unable to create menu item.", e);
			return new APIResponse("Unable to create medicine", e);
		}
	}
	
	
}
