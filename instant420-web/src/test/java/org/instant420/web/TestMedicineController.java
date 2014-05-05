package org.instant420.web;

import org.junit.Test;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.BeanUtils;

public class TestMedicineController extends BaseTestCase{


	@Test
	public void testCreateMedicine() {
		DispensaryService service = childCtx.getBean(DispensaryService.class);
		MedicineController controller = childCtx.getBean(MedicineController.class);
		MenuItemEntity menuItem = service.findMenuItem(3421);
		MenuItemEntity entity = new MenuItemEntity();
		BeanUtils.copyProperties(menuItem, entity);
		entity.setId(null);
		entity.setDispensary(null);
		entity.setName("Test Medicine");
		System.out.println(controller.createMedicine(entity, 449));
	}

}
