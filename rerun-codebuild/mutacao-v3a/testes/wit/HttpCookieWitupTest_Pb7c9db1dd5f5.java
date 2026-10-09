/*
 * @test
 * @summary Test for HttpCookie.setVersion(int v)
 * @run main HttpCookieWitupTest_Pb7c9db1dd5f5
 */

import java.net.HttpCookie;

public class HttpCookieWitupTest_Pb7c9db1dd5f5 {

    public static void main(String[] args) {
        testSetVersionValidValues();
        testSetVersionInvalidValue();
    }

    public static void testSetVersionValidValues() {
        HttpCookie cookie = new HttpCookie("name", "value");
        // Test with valid version 0
        cookie.setVersion(0);
        assert cookie.getVersion() == 0 : "Expected version 0";
        // Test with valid version 1
        cookie.setVersion(1);
        assert cookie.getVersion() == 1 : "Expected version 1";
    }

    public static void testSetVersionInvalidValue() {
        HttpCookie cookie = new HttpCookie("name", "value");
        // Test with invalid version 2, should throw IllegalArgumentException
        try {
            cookie.setVersion(2);
            assert false : "Expected IllegalArgumentException for version 2";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("cookie version should be 0 or 1") : "Unexpected exception message";
        }
        // Test with negative version, e.g., -1
        try {
            cookie.setVersion(-1);
            assert false : "Expected IllegalArgumentException for version -1";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("cookie version should be 0 or 1") : "Unexpected exception message";
        }
    }
}
