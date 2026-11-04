/*
 * @test
 * @summary Observable state of a Provider subclass created through the protected String-version constructor
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTest", "1.0", "witup info");
        }
    }

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        Provider p = new TestProvider();
        check("WitupTest".equals(p.getName()), "getName() should return the name passed to the constructor");
        check("1.0".equals(p.getVersionStr()), "getVersionStr() should return the version string passed to the constructor");
        check("witup info".equals(p.getInfo()), "getInfo() should return the info passed to the constructor");
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
