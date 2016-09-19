package tester;

import org.junit.Assert;
import org.junit.Test;

public class Formatierung {

	
	@Test 
	public void first() {
		Assert.assertEquals(241920,Math.round(2419.20 * 100.00));
	}
	
}
