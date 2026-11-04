/*
 * @test
 * @summary Tests observable behavior of URI.toURL() for absolute URIs and unknown schemes
 * @run main URIWitupTest
 */

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class URIWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testHttpComponents();
        testHttpsSimple();
        testFileScheme();
        testUnknownSchemeThrows();
    }

    static void testHttpComponents() throws Exception {
        String s = "http://example.com:8080/path?q=1#frag";
        URI uri = URI.create(s);
        URL url = uri.toURL();
        check("http".equals(url.getProtocol()), "protocol mismatch");
        check("example.com".equals(url.getHost()), "host mismatch");
        check(url.getPort() == 8080, "port mismatch");
        check("/path".equals(url.getPath()), "path mismatch");
        check("q=1".equals(url.getQuery()), "query mismatch");
        check("frag".equals(url.getRef()), "ref mismatch");
        check(s.equals(url.toString()), "toString mismatch: " + url);
    }

    static void testHttpsSimple() throws Exception {
        URL url = URI.create("https://example.com/").toURL();
        check("https".equals(url.getProtocol()), "https protocol mismatch");
        check("example.com".equals(url.getHost()), "https host mismatch");
        check("/".equals(url.getPath()), "https path mismatch");
    }

    static void testFileScheme() throws Exception {
        URL url = URI.create("file:/tmp/test.txt").toURL();
        check("file".equals(url.getProtocol()), "file protocol mismatch");
        check("/tmp/test.txt".equals(url.getPath()), "file path mismatch");
    }

    static void testUnknownSchemeThrows() {
        URI uri = URI.create("foo://bar/baz");
        boolean thrown = false;
        try {
            uri.toURL();
        } catch (MalformedURLException e) {
            thrown = true;
        }
        check(thrown, "expected MalformedURLException for unknown scheme");
    }
}
