package com.rexit.tutorial;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = TutorialApplication.class)
class TutorialApplicationTests {

	@Test
	void contextLoads() {
	}

}
