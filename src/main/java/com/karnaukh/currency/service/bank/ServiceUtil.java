package com.karnaukh.currency.service.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class ServiceUtil {

	public String getJsonTextFromURI(URL url) throws IOException {
		InputStream inputStream = url.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = reader.read()) != -1) {
			sb.append((char) cp);
		}
		inputStream.close();
		return sb.toString();
	}

	public boolean foundString(String fullString, String necessaryString) {
		String[] strings = fullString.split(" ");
		for (String string : strings) {
			if (string.equals(necessaryString)) {
				return true;
			}
		}
		return false;
	}
}
