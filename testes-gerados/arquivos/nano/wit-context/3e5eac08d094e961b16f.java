/*
 * @test
 * @summary Tests for java.net.HttpCookie.setVersion(int v)
 * @run main HttpCookieWitupTest
 */

import java.net.HttpCookie;

public class HttpCookieWitupTest {

    public static void main(String[] args) {
        testSetVersionValidValues();
        testSetVersionInvalidValue();
    }

    private static void testSetVersionValidValues() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(0);
            assert cookie != null : "Cookie should be instantiated";
            // No exception expected for valid values
            cookie.setVersion(1);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid version values", e);
        }
    }

    private static void testSetVersionInvalidValue() {
        HttpCookie cookie = new HttpCookie("name", "value");
        boolean exceptionThrown = false;
        try {
            cookie.setVersion(2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for version 2";
        exceptionThrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative version";
    }
}
