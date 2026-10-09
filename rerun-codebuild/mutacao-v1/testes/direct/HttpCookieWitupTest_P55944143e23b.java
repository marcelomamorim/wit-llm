/* @test    @summary Test HttpCookie.setVersion with valid and invalid inputs    @run main HttpCookieWitupTest_P55944143e23b */

import java.net.HttpCookie;

public class HttpCookieWitupTest_P55944143e23b {
    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(0);
            if (cookie.getVersion() != 0) {
                throw new AssertionError("Expected version 0");
            }
            cookie.setVersion(1);
            if (cookie.getVersion() != 1) {
                throw new AssertionError("Expected version 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid versions", e);
        }
    }

    static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        boolean thrown = false;
        try {
            cookie.setVersion(2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for version 2");
        }
        thrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for version -1");
        }
    }
}
