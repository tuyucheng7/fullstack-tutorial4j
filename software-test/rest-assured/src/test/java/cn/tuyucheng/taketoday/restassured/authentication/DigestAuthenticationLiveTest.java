package cn.tuyucheng.taketoday.restassured.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * For this Live Test we need:
 * * a running instance of the service located in the spring-security-mvc-digest-auth module.
 *
 * @see <a href="https://github.com/eugenp/tutorials/tree/master/spring-security-mvc-digest-auth">spring-security-mvc-digest-auth module</a>
 */
class DigestAuthenticationLiveTest {

	private static final String USER = "user1";
	private static final String PASSWORD = "user1Pass";
	private static final String SVC_URL = "http://localhost:8080/spring-security-mvc-digest-auth/homepage.html";

	@Test
	void givenNoAuthentication_whenRequestSecuredResource_thenUnauthorizedResponse() {
		get(SVC_URL).then()
				.assertThat()
				.statusCode(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	void givenFormAuthentication_whenRequestSecuredResource_thenResourceRetrieved() {
		given().auth()
				.digest(USER, PASSWORD)
				.when()
				.get(SVC_URL)
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body(containsString("This is the body of the sample view"));
	}
}