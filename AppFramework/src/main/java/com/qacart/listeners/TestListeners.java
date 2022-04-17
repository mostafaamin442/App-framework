package com.qacart.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

public class TestListeners implements ITestNGListener {
	
	public void onTestFailure(ITestResult result) {
		
		if(result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}
		
	}

}
