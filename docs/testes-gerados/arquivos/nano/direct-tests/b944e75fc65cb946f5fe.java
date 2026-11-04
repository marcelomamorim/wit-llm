/* @test    @summary Test the toString() method of AppConfigurationEntry for different control flags.    @run main AppConfigurationEntryWitupTest */

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginModuleControlFlag;

public class AppConfigurationEntryWitupTest {

    public static void main(String[] args) {
        testToStringWithControlFlag(LoginModuleControlFlag.REQUIRED);
        testToStringWithControlFlag(LoginModuleControlFlag.REQUISITE);
        testToStringWithControlFlag(LoginModuleControlFlag.SUFFICIENT);
        testToStringWithControlFlag(LoginModuleControlFlag.OPTIONAL);
    }

    private static void testToStringWithControlFlag(LoginModuleControlFlag flag) {
        String moduleName = "com.example.LoginModule";
        java.util.Map<String, String> options = java.util.Collections.emptyMap();
        AppConfigurationEntry entry = new AppConfigurationEntry(moduleName, flag, options);
        String result = entry.toString();
        String expectedPrefix = "LoginModuleControlFlag.";
        if (!result.contains(expectedPrefix + flag.name())) {
            throw new AssertionError("toString() output does not contain expected control flag name: " + flag.name());
        }
        System.out.println("toString() for control flag " + flag.name() + ": " + result);
    }
}
