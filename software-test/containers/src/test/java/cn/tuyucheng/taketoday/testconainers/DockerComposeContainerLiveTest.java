package cn.tuyucheng.taketoday.testconainers;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class DockerComposeContainerLiveTest {
	@ClassRule
	public static DockerComposeContainer compose = new DockerComposeContainer(new File("src/test/resources/test-compose.yml"))
					.withExposedService("simpleWebServer_1", 80);

	@Test
	public void givenSimpleWebServerContainer_whenGetRequest_thenReturnsResponse() throws Exception {
		String address = "http://" + compose.getServiceHost("simpleWebServer_1", 80)
				+ ":" + compose.getServicePort("simpleWebServer_1", 80);
		String response = simpleGetRequest(address);

		assertEquals("Hello World!", response);
	}

	private String simpleGetRequest(String address) throws Exception {
		URL url = new URL(address);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		return content.toString();
	}
}