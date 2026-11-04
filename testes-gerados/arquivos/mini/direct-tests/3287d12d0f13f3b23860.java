/*
 * @test
 * @summary Basic tests for HttpTimestamper.generateTimestamp method
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import java.io.IOException;
import sun.security.timestamp.HttpTimestamper;
import sun.security.timestamp.TSRequest;
import sun.security.timestamp.TSResponse;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorRejectsNonHttpScheme();
        testGenerateTimestampWithNullRequest();
        testGenerateTimestampWithDummyRequest();
    }

    private static void testConstructorRejectsNonHttpScheme() {
        try {
            new HttpTimestamper(URI.create("ftp://example.com"));
            throw new RuntimeException("Expected IllegalArgumentException for non-http scheme");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new HttpTimestamper(URI.create("file:///tmp"));
            throw new RuntimeException("Expected IllegalArgumentException for non-http scheme");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testGenerateTimestampWithNullRequest() throws Exception {
        HttpTimestamper ht = new HttpTimestamper(URI.create("http://localhost"));
        try {
            ht.generateTimestamp(null);
            throw new RuntimeException("Expected NullPointerException or IOException for null TSRequest");
        } catch (NullPointerException | IOException e) {
            // expected
        }
    }

    private static void testGenerateTimestampWithDummyRequest() throws Exception {
        HttpTimestamper ht = new HttpTimestamper(URI.create("http://localhost"));
        TSRequest dummyRequest = new TSRequest() {
            @Override
            public byte[] encode() {
                return new byte[0];
            }
        };
        try {
            TSResponse resp = ht.generateTimestamp(dummyRequest);
            if (resp == null) {
                throw new RuntimeException("TSResponse should not be null");
            }
        } catch (IOException e) {
            // IOException is expected if no server is listening at localhost
        }
    }
}
