package com.gds;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("GDS Test Suite")
@SelectPackages({"com.gds", "com.gds.user", "com.gds.session", "com.gds.choice"})
public class JUnitTestSuite {
}

