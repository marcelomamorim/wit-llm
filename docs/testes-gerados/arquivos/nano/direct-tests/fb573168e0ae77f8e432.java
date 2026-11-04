/*
 * @test
 * @summary Test URLConnection.getContent() returns expected content object.
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentReturnsObject();
    }

    public static void testGetContentReturnsObject() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        Object content = connection.getContent();
        if (content == null) {
            throw new AssertionError("Expected non-null content object");
        }
        // Optionally, check if content is of expected type if known
        // For example, if content is expected to be an InputStream or String
        // but since getContent() can return various types, just verify non-null
    }
}
