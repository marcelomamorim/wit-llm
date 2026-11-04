/*
 * @test
 * @summary Checks type request property handling of FtpURLConnection through the public URLConnection API
 * @run main FtpURLConnectionWitupTest
 */
import java.net.URL;
import java.net.URLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URLConnection c = new URL("ftp://localhost/file.txt").openConnection();

        String[] valid = {"i", "a", "d", "I", "A", "D"};
        for (String v : valid) {
            c.setRequestProperty("type", v);
            String got = c.getRequestProperty("type");
            if (!v.equals(got)) {
                throw new RuntimeException("Expected stored type " + v + " but got " + got);
            }
        }

        expectIllegalArgument(c, "type", "x");
        expectIllegalArgument(c, "type", "");
        expectIllegalArgument(c, "type", null);

        c.setRequestProperty("foo", "x");
        if (!"x".equals(c.getRequestProperty("foo"))) {
            throw new RuntimeException("Non-type property was not stored");
        }

        expectNullPointer(c, null, "i");
    }

    private static void expectIllegalArgument(URLConnection c, String key, String value) {
        try {
            c.setRequestProperty(key, value);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for value: " + value);
    }

    private static void expectNullPointer(URLConnection c, String key, String value) {
        try {
            c.setRequestProperty(key, value);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null key");
    }
}
