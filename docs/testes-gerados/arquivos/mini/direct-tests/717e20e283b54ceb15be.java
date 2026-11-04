/*
 * @test
 * @summary Test HttpTimestamper constructor with valid and invalid URIs
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {
    public static void main(String[] args) throws Exception {
        testValidHttpUri();
        testValidHttpsUri();
        testInvalidSchemeUri();
    }

    private static void testValidHttpUri() throws Exception {
        URI uri = new URI("http://example.com/tsa");
        HttpTimestamper ts = new HttpTimestamper(uri);
        if (!ts.toString().contains("http://example.com/tsa")) {
            throw new RuntimeException("HttpTimestamper did not store HTTP URI correctly");
        }
    }

    private static void testValidHttpsUri() throws Exception {
        URI uri = new URI("https://example.com/tsa");
        HttpTimestamper ts = new HttpTimestamper(uri);
        if (!ts.toString().contains("https://example.com/tsa")) {
            throw new RuntimeException("HttpTimestamper did not store HTTPS URI correctly");
        }
    }

    private static void testInvalidSchemeUri() {
        try {
            URI uri = new URI("ftp://example.com/tsa");
            new HttpTimestamper(uri);
            throw new RuntimeException("Expected IllegalArgumentException for non-http(s) URI");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("TSA must be an HTTP or HTTPS URI")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
