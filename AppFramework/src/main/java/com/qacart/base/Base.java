package com.qacart.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class Base {

	protected static  AppiumDriver<MobileElement> driver;
	protected FileInputStream inputStream;
	protected Properties prop;
	public static ExtentReports extent;
	public static ExtentTest	logger;

	public Base() {
		
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);

	}
	
	@BeforeSuite
	public void beforeSuite() {
		
		extent = new ExtentReports("Reports/index.html");
		extent.addSystemInfo("framework type", "appium page object");
		extent.addSystemInfo("envitonment", "mac");
		extent.addSystemInfo("author", "mostafa");
		extent.addSystemInfo("app", "qa cart");
	}
	
	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		
		logger = extent.startTest(method.getName());
	}
	
	
	@AfterMethod
	public void afterMethod(Method method, ITestResult result) throws IOException {
		
		
		File image = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(image, new File("snapshots/" + method.getName() + ".jpg"));
		
		String fullPath = System.getProperty("user.dir") + File.separator + "snapshots/" + method.getName() + ".jpg";
		if(result.getStatus() == ITestResult.SUCCESS) {
			
		logger.log(LogStatus.PASS, "test is passed cause there is no error");
		logger.log(LogStatus.PASS, logger.addBase64ScreenShot(fullPath));


		}
		
		else if(result.getStatus() == ITestResult.FAILURE){
		logger.log(LogStatus.FAIL, "test is failed");
		logger.log(LogStatus.FAIL, result.getThrowable());
		logger.log(LogStatus.FAIL, logger.addBase64ScreenShot(fullPath));
		
		}
		
		else {
			logger.log(LogStatus.SKIP, "test is skipped");
		}
	}
	
	
	
	
	@Parameters({"deviceName","platformName","platformVersion"})
	@BeforeClass
	public void beforeClass(String deviceName , String platformName , String platformVersion) throws IOException {

		//read common data from configuration file	
		File porpFile = new File("/Users/ahmednagy/eclipse-workspace/AppFramework/src/main/resources/config/config.properties");
		inputStream = new FileInputStream(porpFile);
		prop = new Properties();
		prop.load(inputStream);

		if (platformName.equalsIgnoreCase("Android")) {
			File androidApp = new File(prop.getProperty("AndroidAppPath"));
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.APP, androidApp.getAbsolutePath());
			driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("appiumServerLink")), caps);
		}
		else if (platformName.equalsIgnoreCase("ios")) {
			File iosApp = new File(prop.getProperty("iosAppPath"));
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.APP, iosApp.getAbsolutePath());
			driver = new IOSDriver<MobileElement>(new URL(prop.getProperty("appiumServerLink")), caps);

		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		
	}

	@AfterClass
	public void close() {

		driver.quit();
	}

}
