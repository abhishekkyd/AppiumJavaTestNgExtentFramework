package com.helloselenium.tests;

import com.helloselenium.utils.JSONReader;
import org.testng.annotations.Test;

import com.helloselenium.BaseTest;
import com.helloselenium.constant.PathConstants;
import com.helloselenium.screens.LoginScreen;
import com.helloselenium.screens.ProductsScreen;
import com.helloselenium.utils.ExcelUtility;
import com.helloselenium.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.Assert;


public class LoginTests extends BaseTest{
	LoginScreen loginScreen;
	ProductsScreen productsScreen;
	ExcelUtility data;
	JSONReader jsonReader;
	String sheet = "testData";
	TestUtils utils = new TestUtils();

	@BeforeClass
	public void beforeClass() throws Exception {
		data = new ExcelUtility(System.getProperty("user.dir")+File.separator+PathConstants.testData);
		jsonReader = new JSONReader(PathConstants.jsonPath);
		closeApp();
		launchApp();
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		loginScreen = new LoginScreen();
	}

	@Test
	public void invalidUserName() {
		loginScreen.enterUserName(jsonReader.getValue("invalidUser","username"));
		loginScreen.enterPassword(jsonReader.getValue("invalidUser","password"));
		loginScreen.pressLoginBtn();

		String actualErrTxt = loginScreen.getErrTxt() + "sdfdf";
		String expectedErrTxt = getStrings().get("err_invalid_username_or_password");

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void invalidPassword() {
		loginScreen.enterUserName(data.getCellData(sheet, "User Name", 2));
		loginScreen.enterPassword(data.getCellData(sheet, "Password", 2));
		loginScreen.pressLoginBtn();

		String actualErrTxt = loginScreen.getErrTxt();
		String expectedErrTxt = getStrings().get("err_invalid_username_or_password");

		Assert.assertEquals(actualErrTxt, expectedErrTxt);
	}

	@Test
	public void successfulLogin() {
		loginScreen.enterUserName(jsonReader.getValue("validUser","username"));
		loginScreen.enterPassword(jsonReader.getValue("validUser","password"));
		productsScreen = loginScreen.pressLoginBtn();

		String actualProductTitle = productsScreen.getTitle();		  
		String expectedProductTitle = getStrings().get("product_title");

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
}
