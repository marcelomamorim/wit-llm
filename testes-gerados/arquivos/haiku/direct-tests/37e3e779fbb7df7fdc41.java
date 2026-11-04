/*
 * @test
 * @summary Verifies Provider.getProperty returns values set through the public Properties API and null for absent keys
 * @run main ProviderWitupTest
 */

import java.security.Provider;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "witup info");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        check(p.getProperty("Missing.Key") == null,
              "getProperty of absent key must return null");

        p.setProperty("Foo.Bar", "baz");
        check("baz".equals(p.getProperty("Foo.Bar")),
              "getProperty must return value set by setProperty");

        p.setProperty("Foo.Bar", "qux");
        check("qux".equals(p.getProperty("Foo.Bar")),
              "getProperty must return the most recently set value");

        check("witup info".equals(p.getInfo()),
              "getInfo must return the info passed to the constructor");
        check("1.0".equals(p.getVersionStr()),
              "getVersionStr must return the version passed to the constructor");
    }
}
