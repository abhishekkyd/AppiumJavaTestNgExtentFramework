package com.helloselenium.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

public class JSONReader {

    private JSONObject loginUsers = null;
    private InputStream datais;
    private JSONTokener tokener = null;
    @SuppressWarnings("unused")
	private String path;

    public JSONReader(String path) throws IOException {
        try {
            this.path = path;
            datais = getClass().getClassLoader().getResourceAsStream(path);
            tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(datais != null) {
                datais.close();
            }
        }
    }

    public String getValue(String jsonObjName, String key){
        String value = null;
        value = loginUsers.getJSONObject(jsonObjName).getString(key);
        return value;
    }





}
