/*
 * @test
 * @summary Verifies HttpCookie.setVersion accepts 0 and 1 and rejects other values while preserving the previous version
 * @run main HttpCookieWitupTest
 */

import java.net.HttpCookie;

public class HttpCookieWitupTest {
    public static void main(String[] args) {
        HttpCookie cookie = new HttpCookie("sample", "value");

        cookie.setVersion(0);
        if (cookie.getVersion() != 0) {
            throw new AssertionError("expected version 0 but was " + cookie.getVersion());
        }

        cookie.setVersion(1);
        if (cookie.getVersion() != 1) {
            throw new AssertionError("expected version 1 but was " + cookie.getVersion());
        }

        checkRejected(cookie, 2);
        checkRejected(cookie, -1);
        checkRejected(cookie, Integer.MIN_VALUE);
        checkRejected(cookie, Integer.MAX_VALUE);

        if (cookie.getVersion() != 1) {
            throw new AssertionError("version changed after rejected value: " + cookie.getVersion());
        }
    }

    private static void checkRejected(HttpCookie cookie, int invalid) {
        try {
            cookie.setVersion(invalid);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for version " + invalid);
    }
}
