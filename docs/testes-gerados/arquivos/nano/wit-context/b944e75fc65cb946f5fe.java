/* @test    @summary Test for AppConfigurationEntry.toString() method to verify correct string output and exception safety.    @run main AppConfigurationEntryWitupTest */

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginModuleControlFlag;

public class AppConfigurationEntryWitupTest {

    public static void main(String[] args) {
        testToStringWithValidControlFlag();
        testToStringWithNullControlFlag();
    }

    private static void testToStringWithValidControlFlag() {
        try {
            // Create an options map (empty, as options are not used in toString)
            java.util.Map<String, String> options = java.util.Collections.emptyMap();
            // Instantiate with a valid control flag
            AppConfigurationEntry entry = new AppConfigurationEntry(
                "com.example.LoginModule",
                LoginModuleControlFlag.REQUIRED,
                options
            );
            String result = entry.toString();
            if (result == null || !result.contains("REQUIRED")) {
                throw new AssertionError("toString() did not include controlFlag: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testToStringWithValidControlFlag", e);
        }
    }

    private static void testToStringWithNullControlFlag() {
        try {
            // Create an options map (empty)
            java.util.Map<String, String> options = java.util.Collections.emptyMap();
            // Attempt to instantiate with null controlFlag, expecting IllegalArgumentException
            AppConfigurationEntry entry = new AppConfigurationEntry(
                "com.example.LoginModule",
                null,
                options
            );
            throw new AssertionError("Expected IllegalArgumentException for null controlFlag, but got: " + entry);
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testToStringWithNullControlFlag", e);
        }
    }
}
