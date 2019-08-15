package org.molgenis.api.tests.twofactor;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.molgenis.api.tests.utils.RestTestUtils.APPLICATION_JSON;
import static org.molgenis.api.tests.utils.RestTestUtils.DEFAULT_ADMIN_NAME;
import static org.molgenis.api.tests.utils.RestTestUtils.DEFAULT_ADMIN_PW;
import static org.molgenis.api.tests.utils.RestTestUtils.DEFAULT_HOST;
import static org.molgenis.api.tests.utils.RestTestUtils.OKE;
import static org.molgenis.api.tests.utils.RestTestUtils.UNAUTHORIZED;
import static org.molgenis.api.tests.utils.RestTestUtils.X_MOLGENIS_TOKEN;
import static org.molgenis.api.tests.utils.RestTestUtils.cleanupUserToken;
import static org.molgenis.api.tests.utils.RestTestUtils.createUser;
import static org.molgenis.api.tests.utils.RestTestUtils.login;
import static org.molgenis.api.tests.utils.RestTestUtils.removeRightsForUser;
import static org.springframework.http.HttpStatus.OK;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.molgenis.security.twofactor.auth.TwoFactorAuthenticationSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TwoFactorAuthenticationAPIIT {
  private static final Logger LOG = LoggerFactory.getLogger(TwoFactorAuthenticationAPIIT.class);

  // Request parameters
  private static final String PATH = "api/v1/";

  // User credentials
  private String testUsername;
  private static final String TWO_FA_AUTH_TEST_USER_PASSWORD = "two_fa_auth_test_user_password";

  private String adminToken;
  private String testUserToken;

  /**
   * Pass down system properties via the mvn commandline argument
   *
   * <p>example: mvn test -Dtest="TwoFactorAuthenticaitonAPIIT"
   * -DREST_TEST_HOST="https://molgenis01.gcc.rug.nl" -DREST_TEST_ADMIN_NAME="admin"
   * -DREST_TEST_ADMIN_PW="admin"
   */
  @BeforeClass
  public void beforeClass() {
    LOG.info("Read environment variables");
    String envHost = System.getProperty("REST_TEST_HOST");
    baseURI = Strings.isNullOrEmpty(envHost) ? DEFAULT_HOST : envHost;
    LOG.info("baseURI: " + baseURI);

    String envAdminName = System.getProperty("REST_TEST_ADMIN_NAME");
    String adminUserName = Strings.isNullOrEmpty(envAdminName) ? DEFAULT_ADMIN_NAME : envAdminName;
    LOG.info("adminUserName: " + adminUserName);

    String envAdminPW = System.getProperty("REST_TEST_ADMIN_PW");
    String adminPassword = Strings.isNullOrEmpty(envAdminPW) ? DEFAULT_ADMIN_PW : envAdminPW;
    LOG.info("adminPassword: " + adminPassword);

    adminToken = login(adminUserName, adminPassword);
    testUsername = "two_fa_auth_test_user" + System.currentTimeMillis();
    createUser(adminToken, testUsername, TWO_FA_AUTH_TEST_USER_PASSWORD);
  }

  @Test
  public void test2faEnforced() {
    toggle2fa(this.adminToken, TwoFactorAuthenticationSetting.ENFORCED);

    try {
      Gson gson = new Gson();
      Map<String, String> loginBody = new HashMap<>();
      loginBody.put("username", testUsername);
      loginBody.put("password", TWO_FA_AUTH_TEST_USER_PASSWORD);

      given()
          .contentType(APPLICATION_JSON)
          .body(gson.toJson(loginBody))
          .when()
          .post(PATH + "login")
          .then()
          .statusCode(UNAUTHORIZED)
          .body(
              "errors.message[0]",
              Matchers.equalTo(
                  "Login using /api/v1/login is disabled, two factor authentication is enabled"));
    } finally {
      // disable 2fa in finally clause instead of after each method due to
      // https://github.com/cbeust/testng/issues/952 which results in cross-test-class issues
      toggle2fa(this.adminToken, TwoFactorAuthenticationSetting.DISABLED);
    }
  }

  @Test
  public void test2faEnabled() {
    toggle2fa(this.adminToken, TwoFactorAuthenticationSetting.ENABLED);

    try {
      Gson gson = new Gson();
      Map<String, String> loginBody = new HashMap<>();
      loginBody.put("username", testUsername);
      loginBody.put("password", TWO_FA_AUTH_TEST_USER_PASSWORD);

      ValidatableResponse response =
          given()
              .contentType(APPLICATION_JSON)
              .body(gson.toJson(loginBody))
              .when()
              .post(PATH + "login")
              .then()
              .statusCode(OKE);

      testUserToken = response.extract().path("token");
    } finally {
      // disable 2fa in finally clause instead of after each method due to
      // https://github.com/cbeust/testng/issues/952 which results in cross-test-class issues
      toggle2fa(this.adminToken, TwoFactorAuthenticationSetting.DISABLED);
    }
  }

  @AfterClass(alwaysRun = true)
  public void afterClass() {
    // Clean up permissions
    removeRightsForUser(adminToken, testUsername);

    // Clean up Token for user
    cleanupUserToken(testUserToken);
  }

  /**
   * Enable or disable 2 factor authentication
   *
   * @param adminToken admin token for login in RESTAPI
   * @param state state of 2 factor authentication (can be Enforced, Enabled, Disabled)
   */
  private void toggle2fa(String adminToken, TwoFactorAuthenticationSetting state) {
    given()
        .header(X_MOLGENIS_TOKEN, adminToken)
        .contentType(APPLICATION_JSON)
        .body(state.getLabel())
        .when()
        .log()
        .ifValidationFails()
        .put("api/v1/sys_set_auth/auth/sign_in_2fa")
        .then()
        .statusCode(OK.value());
  }
}
