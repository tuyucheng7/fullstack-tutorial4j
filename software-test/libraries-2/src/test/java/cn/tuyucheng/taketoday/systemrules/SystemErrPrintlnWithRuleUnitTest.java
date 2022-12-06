package cn.tuyucheng.taketoday.systemrules;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;

public class SystemErrPrintlnWithRuleUnitTest {

	@Rule
	public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

	@Test
	public void givenSystemErrRule_whenInvokePrintln_thenLogSuccess() {
		printError("An Error occurred Tuyucheng Readers!!");

		Assert.assertEquals("An Error occurred Tuyucheng Readers!!", systemErrRule.getLog().trim());
	}

	private void printError(String output) {
		System.err.println(output);
	}
}