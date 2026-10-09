/* @test    @summary Test HttpCookie.setVersion with valid and invalid inputs    @run main HttpCookieWitupTest_Pb7c9db1dd5f5 */

import java.net.HttpCookie;

public class HttpCookieWitupTest_Pb7c9db1dd5f5 {

    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setVersion(0);
        assert cookie != null : "Cookie should not be null after setVersion(0)";
        cookie.setVersion(1);
        assert cookie != null : "Cookie should not be null after setVersion(1)";
    }

    static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        boolean exceptionThrown = false;
        try {
            cookie.setVersion(2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for setVersion(2)";
        exceptionThrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for setVersion(-1)";
    }
}
