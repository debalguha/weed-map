package org.instant420.web.http;

import java.io.IOException;

import org.instant420.web.APIResponse;
import org.instant420.web.domain.MenuItemSearchObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MedicineControllerTestWithHttp extends GenericTest{
	@Test
	public void testCreateMedicine() throws JsonParseException, JsonMappingException, IOException {
		String newMedicineJson = "{    \"name\": \"JENNYS DREAM Blue\",    \"priceEighth\": \"35\",    \"priceGram\": \"10\",    \"priceHalfGram\": \"5\",    \"priceHalfOunce\": \"4\",    \"priceOunce\": \"8\",    \"priceQuarter\": \"3\",    \"priceUnit\": \"50\",    \"pictureURL\": \"\",    \"numberOfDispensary\": \"1\",    \"strainId\": 8815883,    \"description\": \"Json Insert Test\",   \"category\": \"Indica\"    }";
		MenuItemSearchObject entity = new ObjectMapper().readValue(newMedicineJson, MenuItemSearchObject.class);
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/operation/medicine/create/678?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}
	
	@Test
	public void testUpdateMedicine() throws JsonParseException, JsonMappingException, IOException{
		String medicineStr = "{    \"id\": \"3803754\",    \"name\": \"JENNYS DREAM  **5G=45**\",    \"priceEighth\": \"35\",    \"priceGram\": \"10\",    \"priceHalfGram\": \"0\",    \"priceHalfOunce\": \"0\",    \"priceOunce\": \"0\",    \"priceQuarter\": \"0\",    \"priceUnit\": \"0\",    \"pictureURL\": \"\",    \"numberOfDispensary\": \"1\",    \"strainId\": 815883,    \"description\": \"Test Description\",	\"category\": \"Indica\"}";
		MenuItemSearchObject entity = new ObjectMapper().readValue(medicineStr, MenuItemSearchObject.class);
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/operation/medicine/update?key=instant420.rest.api";
		APIResponse response = template.postForObject(url, entity, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}

	@Test
	public void testDeleteMedicine(){
		RestTemplate template = new RestTemplate();
		String url = "http://"+getTargetHost()+":9080/instant420-web/rest/operation/medicine/delete/52782?key=instant420.rest.api";
		APIResponse response = template.getForObject(url, APIResponse.class);
		Assert.assertNotNull(response.getId());
		Assert.assertNull(response.getCause());
	}
}
