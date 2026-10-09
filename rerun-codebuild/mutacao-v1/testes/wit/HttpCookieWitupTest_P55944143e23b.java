/* @test    @summary Test for HttpCookie.setVersion(int) to verify behavior with valid and invalid inputs    @run main HttpCookieWitupTest_P55944143e23b */

import java.net.HttpCookie;

public class HttpCookieWitupTest_P55944143e23b {

    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    private static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(0);
            if (cookie.getVersion() != 0) {
                throw new AssertionError("Expected version to be 0");
            }
            cookie.setVersion(1);
            if (cookie.getVersion() != 1) {
                throw new AssertionError("Expected version to be 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid versions", e);
        }
    }

    private static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(2);
            throw new AssertionError("Expected IllegalArgumentException for version 2");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        try {
            cookie.setVersion(-1);
            throw new AssertionError("Expected IllegalArgumentException for version -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
