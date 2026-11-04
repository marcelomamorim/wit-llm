/*
 * @test
 * @summary Test HttpTimestamper constructor for valid and invalid URI schemes
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import java.net.URISyntaxException;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {
    public static void main(String[] args) throws Exception {
        testValidHttpScheme();
        testValidHttpsScheme();
        testInvalidSchemeThrows();
    }

    private static void testValidHttpScheme() throws Exception {
        URI uri = new URI("http://example.com");
        HttpTimestamper ht = new HttpTimestamper(uri);
        if (ht == null) {
            throw new RuntimeException("HttpTimestamper constructor returned null for http URI");
        }
    }

    private static void testValidHttpsScheme() throws Exception {
        URI uri = new URI("https://example.com");
        HttpTimestamper ht = new HttpTimestamper(uri);
        if (ht == null) {
            throw new RuntimeException("HttpTimestamper constructor returned null for https URI");
        }
    }

    private static void testInvalidSchemeThrows() throws Exception {
        URI uri = new URI("ftp://example.com");
        boolean thrown = false;
        try {
            new HttpTimestamper(uri);
        } catch (IllegalArgumentException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("HTTP or HTTPS URI")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for non-http(s) URI scheme");
        }
    }
}
