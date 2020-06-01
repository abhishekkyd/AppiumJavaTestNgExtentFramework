package com.helloselenium.screens;

import com.helloselenium.Base;
import com.helloselenium.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingsScreen extends Base{
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (accessibility="test-LOGOUT") 
	@iOSXCUITFindBy (id = "test-LOGOUT")
	private MobileElement logoutBtn;
	
	public LoginScreen pressLogoutBtn() {
		click(logoutBtn, "press Logout button");
		return new LoginScreen();
	}

}
