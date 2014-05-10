package org.instant420.web.http;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Instant420SearchControllerTestWithHttp.class, DispensaryControllerTestWithHttp.class, MedicineControllerTestWithHttp.class })
public class APITestSuiteForDispensaryAndMenuItemCRUDS {
}
