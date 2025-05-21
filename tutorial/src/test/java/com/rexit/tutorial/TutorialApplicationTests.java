package com.rexit.tutorial;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TutorialApplication.class)
@ActiveProfiles("test")
class TutorialApplicationTests {

	@Test
	void contextLoads() {
	}

}
