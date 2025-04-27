package com.leitner.leitner_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("Test")
class LeitnerSystemApplicationTests {
	@Test
	void contextLoads() {
	}
}

