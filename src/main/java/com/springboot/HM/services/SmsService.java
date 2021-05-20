package com.springboot.HM.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SmsService {

	public static void main(String[] args) {
		SmsService sms = new SmsService();
		try {
			sms.sendSms("asdasd", "Aasd", "QQ", "03003091615", "Hello Saqi Ali Qureshi");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendSms(String username, String password, String masking, String mobile, String message)
			throws UnsupportedEncodingException {
		String request = "https://sendpk.com/api/sms.php?api_key=923033409109-503ec3b9-ba23-42c1-8a6d-484ddad347f4&mobile="
				+ mobile + "&sender=" + masking + "&message=" + URLEncoder.encode(message, "UTF-8") + "";
		try

		{

			URL url = new URL(request);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {

				connection = (HttpURLConnection) urlConnection;
			}

			else

			{

				System.out.println("Please enter an HTTP URL.");

				return;
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Response Code " + connection.getResponseCode());
			String urlString = "";
			String current;

			while ((current = in.readLine()) != null)

			{

				urlString += current;
			}
			System.out.println(urlString);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
