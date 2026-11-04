/*
 * @test
 * @summary Verifies HttpTimestamper constructor scheme validation and generateTimestamp connection failure behavior
 * @modules java.base/sun.security.timestamp
 * @run main HttpTimestamperWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void expectIllegalArgument(String uri) {
        try {
            new HttpTimestamper(URI.create(uri));
            fail("Expected IllegalArgumentException for " + uri);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        // Accepted schemes (case-insensitive)
        new HttpTimestamper(URI.create("http://example.com/tsa"));
        new HttpTimestamper(URI.create("HTTPS://example.com/tsa"));

        // Rejected schemes
        expectIllegalArgument("ftp://example.com/tsa");
        expectIllegalArgument("file:///tmp/tsa");

        // Null URI
        try {
            new HttpTimestamper(null);
            fail("Expected NullPointerException for null URI");
        } catch (NullPointerException expected) {
            // expected
        }

        // Relative URI without scheme: getScheme() returns null
        try {
            new HttpTimestamper(URI.create("tsa/path"));
            fail("Expected NullPointerException for relative URI");
        } catch (NullPointerException expected) {
            // expected
        }

        // generateTimestamp against a closed local port fails with IOException
        int port;
        try (ServerSocket ss = new ServerSocket(0)) {
            port = ss.getLocalPort();
        }
        HttpTimestamper ts = new HttpTimestamper(
                URI.create("http://127.0.0.1:" + port + "/tsa"));
        try {
            ts.generateTimestamp(null);
            fail("Expected IOException when TSA is unreachable");
        } catch (IOException expected) {
            // expected: connect() fails before the query is encoded
        }
    }
}
