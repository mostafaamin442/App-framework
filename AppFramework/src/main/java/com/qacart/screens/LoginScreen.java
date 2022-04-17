package com.qacart.screens;


import com.qacart.base.Base;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginScreen extends Base{
	
	

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"Enter your Email\"]/XCUIElementTypeTextField")
	private  MobileElement email;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"Enter your Password\"]/XCUIElementTypeSecureTextField")
	private MobileElement password;
	
	@iOSXCUITFindBy(xpath= "//XCUIElementTypeOther[@name=\"Login\"]")
	private MobileElement LoginBtn;
	
	public void check(String mail , String pass ) throws InterruptedException {
		
		
		Thread.sleep(5000);
		email.sendKeys(mail);
		password.sendKeys(pass);
		LoginBtn.click();
	}
	
}
