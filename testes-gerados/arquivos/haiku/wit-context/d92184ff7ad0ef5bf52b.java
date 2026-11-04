/*
 * @test
 * @summary Witup-generated tests for InvalidityDateExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main InvalidityDateExtensionWitupTest
 */
import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Date d = new Date(1000000000000L);
        InvalidityDateExtension ext = new InvalidityDateExtension(false, d);

        Date got = ext.get(InvalidityDateExtension.DATE);
        check(got != null && got.getTime() == d.getTime(), "get(date) must return stored date");
        check(got != d, "get(date) must return a copy");

        got.setTime(5L);
        check(ext.get("date").getTime() == d.getTime(), "mutating returned date must not change extension");

        check(ext.get("DATE").getTime() == d.getTime(), "name lookup must be case-insensitive");

        InvalidityDateExtension shortCtor = new InvalidityDateExtension(d);
        check(shortCtor.get("date").getTime() == d.getTime(), "single-arg constructor must keep date");

        InvalidityDateExtension nullExt = new InvalidityDateExtension(false, null);
        check(nullExt.get("date") == null, "null date must be returned as null");

        expectIOException(ext, "other");
        expectIOException(ext, "");
        expectIOException(ext, "dat");
    }

    private static void expectIOException(InvalidityDateExtension ext, String name) {
        try {
            ext.get(name);
            throw new RuntimeException("expected IOException for name: " + name);
        } catch (IOException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
