package com.helloselenium.screens;

import com.helloselenium.Base;
import com.helloselenium.screens.SettingsScreen;
import com.helloselenium.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BaseScreen extends Base{
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath="//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView\n" + 
			"") 
	@iOSXCUITFindBy (xpath="//XCUIElementTypeOther[@name=\"test-Menu\"]/XCUIElementTypeOther")
	private MobileElement settingsBtn;
	
	public SettingsScreen pressSettingsBtn() {
		click(settingsBtn, "press Settings button");
		return new SettingsScreen();
	}

}
