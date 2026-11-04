/*
 * @test
 * @summary Verifies URI.toURL() for absolute URIs and for URIs without scheme (expath: null scheme -> IllegalArgumentException)
 * @run main URIWitupTest
 */

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class URIWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAbsoluteHttpUri() throws Exception {
        String s = "http://example.com/a/b?x=1#frag";
        URI uri = URI.create(s);
        URL url = uri.toURL();
        check(url != null, "toURL() returned null for absolute URI");
        check(url.toExternalForm().equals(s),
              "unexpected URL form: " + url.toExternalForm());
        check("http".equals(url.getProtocol()), "unexpected protocol: " + url.getProtocol());
        check("example.com".equals(url.getHost()), "unexpected host: " + url.getHost());
    }

    static void testRelativeUriThrowsIllegalArgument() throws Exception {
        URI uri = URI.create("a/b/c");
        check(!uri.isAbsolute(), "test URI unexpectedly absolute");
        boolean thrown = false;
        try {
            uri.toURL();
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (MalformedURLException e) {
            throw new RuntimeException("expected IllegalArgumentException, got MalformedURLException", e);
        }
        check(thrown, "toURL() did not throw IllegalArgumentException for relative URI");
    }

    public static void main(String[] args) throws Exception {
        testAbsoluteHttpUri();
        testRelativeUriThrowsIllegalArgument();
    }
}
