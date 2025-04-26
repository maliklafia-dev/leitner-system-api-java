package com.leitner.leitner_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driver-class-name=org.h2.Driver"
})
class LeitnerSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
