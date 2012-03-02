package org.jquant;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jquant-test-config.xml"})
public class PortfolioTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
