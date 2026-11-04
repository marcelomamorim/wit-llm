/* @test    @summary Test constructor of HttpTimestamper with valid and invalid URIs    @run main HttpTimestamperWitupTest */

import java.net.URI;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) {
        testValidHttpUri();
        testValidHttpsUri();
        testInvalidUriScheme();
    }

    static void testValidHttpUri() {
        URI uri = URI.create("http://example.com/tsa");
        try {
            HttpTimestamper timestamper = new HttpTimestamper(uri);
            System.out.println("testValidHttpUri passed");
        } catch (Exception e) {
            throw new AssertionError("testValidHttpUri failed", e);
        }
    }

    static void testValidHttpsUri() {
        URI uri = URI.create("https://example.com/tsa");
        try {
            HttpTimestamper timestamper = new HttpTimestamper(uri);
            System.out.println("testValidHttpsUri passed");
        } catch (Exception e) {
            throw new AssertionError("testValidHttpsUri failed", e);
        }
    }

    static void testInvalidUriScheme() {
        URI uri = URI.create("ftp://example.com/tsa");
        try {
            new HttpTimestamper(uri);
            throw new AssertionError("testInvalidUriScheme failed: exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidUriScheme passed");
        } catch (Exception e) {
            throw new AssertionError("testInvalidUriScheme failed with unexpected exception", e);
        }
    }
}
