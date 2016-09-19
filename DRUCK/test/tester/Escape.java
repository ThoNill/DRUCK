package tester;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class Escape {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String text = " Das ist ( ein Test ) ";
		text = text.replaceAll("\\(", "\\\\(");
		text = text.replaceAll("\\)", "\\\\)");
		
		assertEquals(" Das ist \\( ein Test \\) ", text);
		
	}

}
