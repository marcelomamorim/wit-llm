/*
 * @test
 * @summary Verify HttpCookie.setVersion accepts 0 and 1 and rejects other values without changing the version
 * @run main HttpCookieWitupTest
 */
import java.net.HttpCookie;

public class HttpCookieWitupTest {
    public static void main(String[] args) {
        HttpCookie cookie = new HttpCookie("name", "value");

        cookie.setVersion(0);
        if (cookie.getVersion() != 0) {
            throw new RuntimeException("expected version 0 after setVersion(0), got " + cookie.getVersion());
        }

        cookie.setVersion(1);
        if (cookie.getVersion() != 1) {
            throw new RuntimeException("expected version 1 after setVersion(1), got " + cookie.getVersion());
        }

        int[] invalidVersions = {-1, 2, 3, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int v : invalidVersions) {
            HttpCookie c = new HttpCookie("name", "value");
            boolean thrown = false;
            try {
                c.setVersion(v);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IllegalArgumentException for version " + v);
            }
            if (c.getVersion() != 1) {
                throw new RuntimeException("version changed after rejected value " + v + ": " + c.getVersion());
            }
        }
    }
}
