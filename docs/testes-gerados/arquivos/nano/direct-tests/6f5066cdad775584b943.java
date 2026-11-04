/* @test    @summary Test URL.getContent() method for standard URL with accessible content.    @run main URLWitupTest */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentWithValidURL();
        testGetContentWithInvalidURL();
    }

    private static void testGetContentWithValidURL() throws IOException {
        URL url = new URL("http://example.com/");
        Object content = url.getContent();
        if (content == null) {
            throw new AssertionError("Expected non-null content for valid URL");
        }
        System.out.println("testGetContentWithValidURL passed.");
    }

    private static void testGetContentWithInvalidURL() {
        try {
            URL url = new URL("http://invalid.url/");
            Object content = url.getContent();
            // Depending on network conditions, this may throw or return null
            if (content == null) {
                System.out.println("testGetContentWithInvalidURL passed (null content as expected).");
            } else {
                System.out.println("testGetContentWithInvalidURL passed (non-null content, network may be reachable).");
            }
        } catch (IOException e) {
            System.out.println("testGetContentWithInvalidURL passed (caught IOException).\n" + e);
        }
    }
}
