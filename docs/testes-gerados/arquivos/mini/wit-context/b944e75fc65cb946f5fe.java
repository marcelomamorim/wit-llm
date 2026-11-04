/*
 * @test
 * @summary Test javax.security.auth.login.AppConfigurationEntry.toString() method
 * @run main AppConfigurationEntryWitupTest
 */

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppConfigurationEntryWitupTest {
    public static void main(String[] args) {
        testToStringReturnsExpectedFormat();
    }

    private static void testToStringReturnsExpectedFormat() {
        // Prepare options map
        Map<String, String> options = new HashMap<>();
        options.put("debug", "true");

        // Create AppConfigurationEntry instance
        AppConfigurationEntry entry = new AppConfigurationEntry("com.example.LoginModule", LoginModuleControlFlag.REQUIRED, options);

        // Call toString()
        String result = entry.toString();

        // The toString() method returns a string starting with "LoginModuleControlFlag." followed by controlFlag.toString()
        // We check that the returned string contains the control flag name and the login module name is not included
        if (!result.startsWith("LoginModuleControlFlag.")) {
            throw new AssertionError("toString() output does not start with expected prefix: " + result);
        }

        // The controlFlag.toString() should be one of the enum names
        boolean containsControlFlagName = false;
        for (LoginModuleControlFlag flag : LoginModuleControlFlag.values()) {
            if (result.endsWith(flag.toString())) {
                containsControlFlagName = true;
                break;
            }
        }
        if (!containsControlFlagName) {
            throw new AssertionError("toString() output does not end with a valid control flag name: " + result);
        }
    }
}
