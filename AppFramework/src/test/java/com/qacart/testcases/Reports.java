package com.qacart.testcases;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {
	
	public ExtentReports extent;
	public ExtentTest	logger;
	
	@BeforeSuite
	public void beforeClass() {
		
		extent = new ExtentReports("Reports/index.html");
		extent.addSystemInfo("framework type", "appium page object");
		extent.addSystemInfo("envitonment", "mac");
		extent.addSystemInfo("author", "mostafa");
		extent.addSystemInfo("app", "qa cart");
	}
	
	@AfterSuite
	public void afterClass() {
		extent.flush();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		
		logger = extent.startTest(method.getName());
	}
	
	@Test
	public void test1() {
		
		System.out.println("test pass");
	}
	
	@Test
	public void test2() {
		
		System.out.println("test failed cause i want it failed");
	}
	
	@AfterMethod
	public void afterMethod(Method method, ITestResult result) {
		
		if(result.getStatus() == ITestResult.SUCCESS) {
			
		logger.log(LogStatus.PASS, "test is passed cause there is no error");
			
		}
		
		else if(result.getStatus() == ITestResult.FAILURE){
		logger.log(LogStatus.FAIL, "test is failed");
		logger.log(LogStatus.FAIL, result.getThrowable());
		
		}
		
		else {
			logger.log(LogStatus.SKIP, "test is skipped");
		}
	}

	

}
