package net.rdrei.simplex.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(SimplexTableauTestCase.class);
		suite.addTestSuite(SimplexRestrictionTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
