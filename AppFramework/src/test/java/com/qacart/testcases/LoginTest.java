package com.qacart.testcases;

import org.testng.annotations.Test;
import com.qacart.base.Base;
import com.qacart.screens.LoginScreen;

public class LoginTest extends Base{
	
	LoginScreen loginscreen ;
	
	@Test
	public void TestCase() throws InterruptedException {
		
		loginscreen = new LoginScreen();
		loginscreen.check("hatem.hatamln@qaczrt.com", "test@123");
		
		//Assert.fail(); if you want to make the test case failed
	
	}

}
