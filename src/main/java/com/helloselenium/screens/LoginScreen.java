package com.helloselenium.screens;

import com.helloselenium.BaseTest;
import com.helloselenium.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginScreen extends BaseTest {
	TestUtils utils = new TestUtils();
	@AndroidFindBy (accessibility = "test-Username") 
	@iOSXCUITFindBy (id = "test-Username")
	private MobileElement usernameTxtFld;

	@AndroidFindBy (accessibility = "test-Password") 
	@iOSXCUITFindBy (id = "test-Password")
	private MobileElement passwordTxtFld;

	@AndroidFindBy (accessibility = "test-LOGIN") 
	@iOSXCUITFindBy (id = "test-LOGIN")
	private MobileElement loginBtn;

	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
	private MobileElement errTxt;

	public LoginScreen enterUserName(String username) {
		clear(usernameTxtFld);	
		sendKeys(usernameTxtFld, username, "login with " + username);
		return this;
	}

	public LoginScreen enterPassword(String password) {
		clear(passwordTxtFld);
		sendKeys(passwordTxtFld, password, "password is " + password);
		return this;
	}

	public ProductsScreen pressLoginBtn() {
		click(loginBtn, "press login button");
		return new ProductsScreen();
	}

	public ProductsScreen login(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		return pressLoginBtn();
	}

	public String getErrTxt() {
		String err = getText(errTxt, "error text is - ");
		return err;
	}

}
