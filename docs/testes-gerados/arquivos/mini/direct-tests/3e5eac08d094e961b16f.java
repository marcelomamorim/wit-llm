/*
 * @test
 * @summary Test java.net.HttpCookie.setVersion(int) method behavior
 * @run main HttpCookieWitupTest
 */

import java.net.HttpCookie;

public class HttpCookieWitupTest {
    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    private static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setVersion(0);
        if (cookie.version != 0) {
            throw new RuntimeException("Expected version 0 but got " + cookie.version);
        }
        cookie.setVersion(1);
        if (cookie.version != 1) {
            throw new RuntimeException("Expected version 1 but got " + cookie.version);
        }
    }

    private static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(2);
            throw new RuntimeException("Expected IllegalArgumentException for version 2");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("cookie version should be 0 or 1")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
        try {
            cookie.setVersion(-1);
            throw new RuntimeException("Expected IllegalArgumentException for version -1");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("cookie version should be 0 or 1")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
