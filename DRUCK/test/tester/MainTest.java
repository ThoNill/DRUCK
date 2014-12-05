package tester;

import org.junit.Assert;
import org.junit.Test;
import toni.druck.main.Main;

public class MainTest {

	
	@Test
	public void probedruck() {
		try {
			Main.probedruck("testdaten/biglist","results/","testdaten/");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void probedruck2() {
		try {
			Main.probedruck("testdaten/probedruck","results/","testdaten/");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
