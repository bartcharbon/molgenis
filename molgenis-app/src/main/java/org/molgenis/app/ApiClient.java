package org.molgenis.app;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ApiClient
{
	public static void post(String url, String payload, String token) throws IOException
	{

		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

		try
		{

			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(payload, "UTF-8");
			request.addHeader("content-type", "application/json");
			request.addHeader("x-molgenis-token", token);
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
		}
		catch (Exception ex)
		{

			//handle exception here

		}
	}
}
