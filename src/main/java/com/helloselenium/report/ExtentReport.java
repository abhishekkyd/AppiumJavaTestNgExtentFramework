package com.helloselenium.report;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.helloselenium.Base;
import com.helloselenium.constant.PathConstants;

public class ExtentReport {
	static ExtentReports extent;
    final static String filePath = PathConstants.reports;
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    
    public synchronized static ExtentReports getReporter() {
    	Base base = new Base();
        if (extent == null) {
        	ExtentHtmlReporter html = new ExtentHtmlReporter(filePath+"_"+base.getDateTime()+".html");
        	html.config().setDocumentTitle("Appium Framework");
        	html.config().setReportName("MyApp");
        	html.config().setTheme(Theme.DARK);
            extent = new ExtentReports();
            extent.attachReporter(html);
        }
        
        return extent;
    }
    
    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
