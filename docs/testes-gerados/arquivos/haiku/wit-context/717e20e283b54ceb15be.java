/*
 * @test
 * @summary Verifies HttpTimestamper constructor accepts only HTTP/HTTPS TSA URIs (case-insensitive) and rejects other schemes with IllegalArgumentException
 * @modules java.base/sun.security.timestamp
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) throws Exception {
        expectAccepted("http://tsa.example.com/ts");
        expectAccepted("https://tsa.example.com/ts");
        expectAccepted("HTTP://tsa.example.com/ts");
        expectAccepted("HtTpS://tsa.example.com/ts");

        expectRejected("ftp://tsa.example.com/ts");
        expectRejected("ldap://tsa.example.com/ts");
        expectRejected("file:///tmp/ts");
    }

    private static void expectAccepted(String uri) {
        try {
            new HttpTimestamper(URI.create(uri));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valid TSA URI rejected: " + uri, e);
        }
    }

    private static void expectRejected(String uri) {
        try {
            new HttpTimestamper(URI.create(uri));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Non-HTTP(S) TSA URI accepted: " + uri);
    }
}
