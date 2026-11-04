/*
 * @test
 * @summary Verify AppConfigurationEntry.toString() combines the localized prefix with the control flag
 * @modules java.base/sun.security.util
 * @run main AppConfigurationEntryWitupTest
 */
import java.util.Collections;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginModuleControlFlag;
import sun.security.util.ResourcesMgr;

public class AppConfigurationEntryWitupTest {

    public static void main(String[] args) {
        LoginModuleControlFlag[] flags = {
            LoginModuleControlFlag.REQUIRED,
            LoginModuleControlFlag.REQUISITE,
            LoginModuleControlFlag.SUFFICIENT,
            LoginModuleControlFlag.OPTIONAL
        };
        String prefix = ResourcesMgr.getString("LoginModuleControlFlag.");
        Map<String, ?> options = Collections.emptyMap();

        for (LoginModuleControlFlag flag : flags) {
            AppConfigurationEntry entry =
                new AppConfigurationEntry("TestModule", flag, options);
            String first = entry.toString();
            check(first != null, "toString must not return null");
            check(first.equals(prefix + flag.toString()),
                  "unexpected toString for flag " + flag + ": " + first);
            check(first.equals(entry.toString()),
                  "toString must be deterministic for flag " + flag);
        }

        AppConfigurationEntry required =
            new AppConfigurationEntry("TestModule",
                                      LoginModuleControlFlag.REQUIRED,
                                      options);
        AppConfigurationEntry optional =
            new AppConfigurationEntry("TestModule",
                                      LoginModuleControlFlag.OPTIONAL,
                                      options);
        check(!required.toString().equals(optional.toString()),
              "toString must reflect different control flags");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
