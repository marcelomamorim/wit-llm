/*
 * @test
 * @summary Test AppConfigurationEntry.toString() method behavior
 * @run main javax.security.auth.login.AppConfigurationEntryWitupTest
 */

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginModuleControlFlag;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppConfigurationEntryWitupTest {
    public static void main(String[] args) {
        testToStringReturnsExpectedPrefixPlusControlFlag();
    }

    private static void testToStringReturnsExpectedPrefixPlusControlFlag() {
        Map<String, String> options = new HashMap<>();
        options.put("key", "value");
        AppConfigurationEntry entry = new AppConfigurationEntry("com.example.LoginModule", LoginModuleControlFlag.REQUIRED, options);
        String result = entry.toString();

        // The toString() must start with "LoginModuleControlFlag." and end with the control flag name
        if (!result.startsWith("LoginModuleControlFlag.")) {
            throw new RuntimeException("toString() does not start with expected prefix: " + result);
        }
        if (!result.endsWith(LoginModuleControlFlag.REQUIRED.toString())) {
            throw new RuntimeException("toString() does not end with control flag name: " + result);
        }
    }
}
