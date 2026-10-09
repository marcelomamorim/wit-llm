/* @test
   @summary Test setting valid and invalid versions in HttpCookie.
   @run main HttpCookieWitupTest_P78f026e0fcfe
 */
import java.net.HttpCookie;
public class HttpCookieWitupTest_P78f026e0fcfe {
    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        cookie.setVersion(0);
        assert cookie != null : "Cookie should not be null after setting version 0";
        cookie.setVersion(1);
        assert cookie != null : "Cookie should not be null after setting version 1";
    }

    static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        boolean exceptionThrown = false;
        try {
            cookie.setVersion(2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting version 2 should throw IllegalArgumentException";
        exceptionThrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting version -1 should throw IllegalArgumentException";
    }
}
