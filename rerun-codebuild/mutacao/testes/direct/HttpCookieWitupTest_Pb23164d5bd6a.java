/* @test    @summary Test HttpCookie.setVersion with valid and invalid inputs    @run main HttpCookieWitupTest_Pb23164d5bd6a */

import java.net.HttpCookie;

public class HttpCookieWitupTest_Pb23164d5bd6a {

    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(0);
            assert cookie.getVersion() == 0 : "Expected version 0";
            cookie.setVersion(1);
            assert cookie.getVersion() == 1 : "Expected version 1";
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
        assert thrown : "Expected IllegalArgumentException for version 2";
        thrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert thrown : "Expected IllegalArgumentException for version -1";
    }
}
