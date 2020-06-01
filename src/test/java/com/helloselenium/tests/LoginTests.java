package com.helloselenium.tests;

import org.testng.annotations.Test;

import com.helloselenium.Base;
import com.helloselenium.constant.PathConstants;
import com.helloselenium.screens.LoginScreen;
import com.helloselenium.screens.ProductsScreen;
import com.helloselenium.utils.ExcelUtility;
import com.helloselenium.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;


public class LoginTests extends Base{
	LoginScreen loginScreen;
	ProductsScreen productsScreen;
	JSONObject loginUsers;
	ExcelUtility data;
	String sheet = "testData";
	TestUtils utils = new TestUtils();

	@BeforeClass
	public void beforeClass() throws Exception {
		data = new ExcelUtility(System.getProperty("user.dir")+File.separator+PathConstants.testData);
		InputStream datais = null;
		try {
			String dataFileName = "data/loginUsers.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(datais != null) {
				datais.close();
			}
		}
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
		loginScreen.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginScreen.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
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
		loginScreen.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		loginScreen.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		productsScreen = loginScreen.pressLoginBtn();

		String actualProductTitle = productsScreen.getTitle();		  
		String expectedProductTitle = getStrings().get("product_title");

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
}
