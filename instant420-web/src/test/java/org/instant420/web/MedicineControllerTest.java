package org.instant420.web;

import java.io.IOException;

import org.instant420.web.domain.MenuItemSearchObject;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MedicineControllerTest extends BaseTestCase{
	
	@Test
	public void testCreateMedicine() throws JsonParseException, JsonMappingException, IOException {
		String newMedicineJson = "{    \"name\": \"JENNYS DREAM Blue\",    \"priceEighth\": \"35\",    \"priceGram\": \"10\",    \"priceHalfGram\": \"5\",    \"priceHalfOunce\": \"4\",    \"priceOunce\": \"8\",    \"priceQuarter\": \"3\",    \"priceUnit\": \"50\",    \"pictureURL\": \"\",    \"numberOfDispensary\": \"1\",    \"strainId\": 8815883,    \"description\": \"Json Insert Test\",   \"category\": \"Indica\"    }";
		MedicineController controller = childCtx.getBean(MedicineController.class);
		System.out.println(controller.createMedicine(new ObjectMapper().readValue(newMedicineJson, MenuItemSearchObject.class), 678));
	}
	
	@Test
	@Ignore
	public void testUpdateMedicine() throws JsonParseException, JsonMappingException, IOException{
		String medicineStr = "{    \"id\": \"3803754\",    \"name\": \"JENNYS DREAM  **5G=45**\",    \"priceEighth\": \"35\",    \"priceGram\": \"10\",    \"priceHalfGram\": \"0\",    \"priceHalfOunce\": \"0\",    \"priceOunce\": \"0\",    \"priceQuarter\": \"0\",    \"priceUnit\": \"0\",    \"pictureURL\": \"\",    \"numberOfDispensary\": \"1\",    \"strainId\": 815883,    \"description\": \"Test Description\",	\"category\": \"Indica\"}";
		MenuItemSearchObject menu = new ObjectMapper().readValue(medicineStr, MenuItemSearchObject.class);
		MedicineController controller = childCtx.getBean(MedicineController.class);
		System.out.println(controller.updateMedicine(menu));
	}

	@Test
	@Ignore
	public void testDeleteMedicine(){
		Long medicineId = 3887907L;
		MedicineController controller = childCtx.getBean(MedicineController.class);
		System.out.println(controller.deleteMedicine(medicineId));
	}
}
