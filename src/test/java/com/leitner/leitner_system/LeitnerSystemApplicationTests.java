package com.leitner.leitner_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = {

				"spring.autoconfigure.exclude=" +
						"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
		}
)
class LeitnerSystemApplicationTests {
	@Test
	void contextLoads() {
	}
}

