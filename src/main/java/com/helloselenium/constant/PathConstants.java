package com.helloselenium.constant;

import java.io.File;

public interface PathConstants {
	
	String image = "artifacts" + File.separator + "Screenshots";
	String reports = "artifacts" + File.separator + "html"+ File.separator +"Extent";
	String video = "artifacts" + File.separator + "Videos";
	String severlogs = "artifacts" + File.separator + "Severlogs";
	String logs = "artifacts" + File.separator + "logs";
	String testData = "src/test/resources/data/TestData.xlsx";
	String xmlPath = "strings/strings.xml";
	String jsonPath = "data/loginUsers.json";

}
