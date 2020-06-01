package com.helloselenium.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.helloselenium.Base;
import com.helloselenium.screens.LoginScreen;
import com.helloselenium.screens.ProductDetailsScreen;
import com.helloselenium.screens.ProductsScreen;
import com.helloselenium.screens.SettingsScreen;
import com.helloselenium.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ProductTests extends Base{
	LoginScreen loginScreen;
	ProductsScreen productsScreen;
	SettingsScreen settingsScreen;
	ProductDetailsScreen productDetailsScreen;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();

	@BeforeClass
	public void beforeClass() throws Exception {
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
		productsScreen = loginScreen.login(loginUsers.getJSONObject("validUser").getString("username"), 
				loginUsers.getJSONObject("validUser").getString("password"));
	}

	@AfterMethod
	public void afterMethod() {
		settingsScreen = productsScreen.pressSettingsBtn();
		loginScreen = settingsScreen.pressLogoutBtn();
	}

	@Test
	public void validateProductOnproductsScreen() {
		SoftAssert sa = new SoftAssert();

		String SLBTitle = productsScreen.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));

		String SLBPrice = productsScreen.getSLBPrice();
		sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));

		sa.assertAll();
	}

	@Test
	public void validateProductOnproductDetailsScreen() {
		SoftAssert sa = new SoftAssert();

		productDetailsScreen = productsScreen.pressSLBTitle();

		String SLBTitle = productDetailsScreen.getSLBTitle();
		sa.assertEquals(SLBTitle, getStrings().get("product_details_page_slb_title"));

		if(getPlatform().equalsIgnoreCase("Android")) {
			String SLBPrice = productDetailsScreen.scrollToSLBPriceAndGetSLBPrice();
			sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));  
		}
		if(getPlatform().equalsIgnoreCase("iOS")) {			  
			String SLBTxt = productDetailsScreen.getSLBTxt();
			sa.assertEquals(SLBTxt, getStrings().get("product_details_page_slb_txt"));

			productDetailsScreen.scrollPage();
			sa.assertTrue(productDetailsScreen.isAddToCartBtnDisplayed());  
		}		  		  
		productsScreen = productDetailsScreen.pressBackToProductsBtn();

		sa.assertAll();
	}
}
