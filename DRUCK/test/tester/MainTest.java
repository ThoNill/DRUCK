package tester;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.main.Main;

public class MainTest {
    private static final Logger LOG = Logger.getLogger(MainTest.class.getName());


	
	@Test
	public void probedruck() {
		try {
			Main.probedruck("testdaten/biglist","results/","testdaten/");
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void probedruck2() {
		try {
			Main.probedruck("testdaten/probedruck","results/","testdaten/");
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
