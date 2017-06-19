package org.molgenis.data.rest.client;

import com.google.gson.GsonBuilder;
import org.molgenis.data.rest.client.bean.LoginResponse;
import org.molgenis.util.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;

public class MolgenisClientTest
{
	private MolgenisClient molgenis;

	@BeforeTest
	public void beforeTest()
	{
		RestTemplate template = new RestTemplate();
		template.setMessageConverters(of(new GsonHttpMessageConverter(new GsonBuilder().setPrettyPrinting().create())));
		molgenis = new MolgenisClient(template, "http://localhost:8080/api/v1");
	}

	@Test
	public void testLoginAndUpdate()
	{
		LoginResponse loginResponse = molgenis.login("admin", "admin");
		String token = loginResponse.getToken();
	}
}
