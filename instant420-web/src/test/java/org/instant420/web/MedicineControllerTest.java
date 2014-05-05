package org.instant420.web;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MedicineControllerTest extends BaseTestCase{
	
	@Test
	@Ignore
	public void testCreateMedicine() {
		DispensaryService service = childCtx.getBean(DispensaryService.class);
		MedicineController controller = childCtx.getBean(MedicineController.class);
		MenuItemEntity menuItem = service.findMenuItem(3421);
		MenuItemEntity entity = new MenuItemEntity();
		BeanUtils.copyProperties(menuItem, entity);
		entity.setId(null);
		entity.setDispensary(null);
		entity.setName("Test Medicine");
		System.out.println(controller.createMedicine(entity, 678));
	}
	
	@Test
	public void testUpdateMedicine() throws JsonParseException, JsonMappingException, IOException{
		String medicineStr = "{	\"id\": \"3803754\",	\"name\": \"JENNYS DREAM  **5G=45**\",	\"priceEighth\": \"35\",	\"priceGram\": \"10\",	\"priceHalfGram\": \"0\",	\"priceHalfOunce\": \"0\",	\"priceOunce\": \"0\",	\"priceQuarter\": \"0\",	\"priceUnit\": \"0\",	\"pictureURL\": \"\",	\"numberOfDispensary\": \"1\",	\"strainId\": 815883,	\"description\":\"Test Description\",	\"menuItemCategory\":{		\"categoryName\":\"Indica\"	}}";
		MenuItemEntity entity = new ObjectMapper().readValue(medicineStr, MenuItemEntity.class);
		MedicineController controller = childCtx.getBean(MedicineController.class);
		/*DispensaryService service = childCtx.getBean(DispensaryService.class);
		MedicineController controller = childCtx.getBean(MedicineController.class);
		MenuItemEntity menuItem = service.findMenuItem(3421);
		menuItem.setName("Test Menu_2");
		menuItem.setLang(new BigDecimal(9999));*/
		System.out.println(controller.updateMedicine(entity, 24517));
	}

}
