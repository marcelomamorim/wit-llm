/*
 * @test
 * @summary Verifies observable behavior of Security.setProperty via Security.getProperty
 * @run main SecurityWitupTest
 */

import java.security.Security;

public class SecurityWitupTest {

    public static void main(String[] args) throws Exception {
        String key = "witup.setProperty.test";
        Security.setProperty(key, "value1");
        check(key, "value1");

        Security.setProperty(key, "value2");
        check(key, "value2");

        String otherKey = "witup.setProperty.other";
        Security.setProperty(otherKey, "x");
        check(otherKey, "x");
        check(key, "value2");

        String nullKey = "witup.setProperty.nulldatum";
        boolean npe = false;
        try {
            Security.setProperty(nullKey, null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for null datum");
        }
        if (Security.getProperty(nullKey) != null) {
            throw new RuntimeException("Property must not be set after failed call");
        }
    }

    private static void check(String key, String expected) {
        String actual = Security.getProperty(key);
        if (!expected.equals(actual)) {
            throw new RuntimeException("For key " + key + " expected " + expected + " but was " + actual);
        }
    }
}
