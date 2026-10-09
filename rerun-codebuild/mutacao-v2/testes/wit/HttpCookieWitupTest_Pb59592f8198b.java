/*
 * @test
 * @summary Test HttpCookie.setVersion(int) for valid and invalid inputs
 * @run main HttpCookieWitupTest_Pb59592f8198b
 */

import java.net.HttpCookie;

public class HttpCookieWitupTest_Pb59592f8198b {
    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    private static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setVersion(0);
        if (cookie.version != 0) {
            throw new RuntimeException("Expected version 0 after setVersion(0)");
        }
        cookie.setVersion(1);
        if (cookie.version != 1) {
            throw new RuntimeException("Expected version 1 after setVersion(1)");
        }
    }

    private static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(2);
            throw new RuntimeException("Expected IllegalArgumentException for setVersion(2)");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            cookie.setVersion(-1);
            throw new RuntimeException("Expected IllegalArgumentException for setVersion(-1)");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            cookie.setVersion(100);
            throw new RuntimeException("Expected IllegalArgumentException for setVersion(100)");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
