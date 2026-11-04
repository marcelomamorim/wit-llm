/*
 * @test
 * @summary Witup-derived checks for AppConfigurationEntry.toString() observable behavior
 * @run main AppConfigurationEntryWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginModuleControlFlag;

public class AppConfigurationEntryWitupTest {

    public static void main(String[] args) {
        Map<String, Object> opts = new HashMap<>();
        opts.put("debug", "true");

        AppConfigurationEntry required = new AppConfigurationEntry(
                "com.example.Module", LoginModuleControlFlag.REQUIRED, opts);
        String s = required.toString();

        check(s != null && !s.isEmpty(), "toString must not be null or empty");
        check(s.endsWith(LoginModuleControlFlag.REQUIRED.toString()),
                "toString must end with the control flag representation");

        check(s.equals(required.toString()), "toString must be stable across calls");

        AppConfigurationEntry optional = new AppConfigurationEntry(
                "com.example.Module", LoginModuleControlFlag.OPTIONAL, opts);
        check(!s.equals(optional.toString()),
                "different control flags must produce different toString results");
        check(optional.toString().endsWith(LoginModuleControlFlag.OPTIONAL.toString()),
                "OPTIONAL toString must end with its control flag representation");

        AppConfigurationEntry noOptions = new AppConfigurationEntry(
                "com.example.Other", LoginModuleControlFlag.REQUIRED,
                Collections.<String, Object>emptyMap());
        check(s.equals(noOptions.toString()),
                "toString must depend on control flag, not on login module options");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
