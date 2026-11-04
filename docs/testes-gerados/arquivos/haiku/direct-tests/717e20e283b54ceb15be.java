/*
 * @test
 * @summary Verifies scheme validation in the HttpTimestamper constructor
 * @modules java.base/sun.security.timestamp
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) throws Exception {
        new HttpTimestamper(URI.create("http://localhost/tsa"));
        new HttpTimestamper(URI.create("https://localhost/tsa"));
        new HttpTimestamper(URI.create("HTTP://localhost/tsa"));
        new HttpTimestamper(URI.create("HtTpS://localhost:8443/tsa"));

        expectIllegalArgument("ftp://localhost/tsa");
        expectIllegalArgument("file:///tmp/tsa");
        expectIllegalArgument("ldap://localhost/tsa");

        expectNullPointer(URI.create("tsa/relative"));
        expectNullPointer(null);
    }

    static void expectIllegalArgument(String uri) {
        try {
            new HttpTimestamper(URI.create(uri));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for " + uri);
    }

    static void expectNullPointer(URI uri) {
        try {
            new HttpTimestamper(uri);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for " + uri);
    }
}
