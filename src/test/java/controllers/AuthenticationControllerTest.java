package controllers;

import com.google.common.collect.Maps;
import ninja.NinjaTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AuthenticationControllerTest extends NinjaTest {
    @Before
    public void setup() {
        Map<String, String> headers = Maps.newHashMap();

        Map<String, String> formParameters = Maps.newHashMap();
        formParameters.put("fullname", "gp");
        formParameters.put("username", "gp@gmail.com");
        formParameters.put("password", "111111");

        ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
                + "register", headers, formParameters);
    }

    @Test
    public void testLoginLogout() {

        Map<String, String> headers = Maps.newHashMap();

        // Login
        Map<String, String> formParameters = Maps.newHashMap();
        formParameters.put("username", "gp@gmail.com");
        formParameters.put("password", "111111");

        String result = ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress()
                + "login", headers, formParameters);

        // Logout
        ninjaTestBrowser.makeRequest(getServerAddress() + "logout", headers);

        assertTrue(result.contains("Guess The Word!"));
    }

}