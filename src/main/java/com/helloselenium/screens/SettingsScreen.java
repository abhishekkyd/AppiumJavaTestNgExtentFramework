package com.helloselenium.screens;

import com.helloselenium.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsScreen extends BaseTest{
		
	@AndroidFindBy (accessibility="test-LOGOUT") 
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private MobileElement logoutBtn;
	
	public LoginScreen pressLogoutBtn() {
		click(logoutBtn, "press Logout button");
		return new LoginScreen();
	}

}
