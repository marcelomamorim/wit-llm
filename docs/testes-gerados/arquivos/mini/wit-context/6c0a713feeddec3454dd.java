/*
 * @test
 * @summary Test java.nio.file.Paths.get(URI) method for valid and invalid URIs
 * @run main PathsWitupTest
 */

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.IllegalArgumentException;

public class PathsWitupTest {
    public static void main(String[] args) {
        testValidURI();
        testNullSchemeURI();
    }

    private static void testValidURI() {
        try {
            URI uri = new URI("file:///tmp");
            Path p = Paths.get(uri);
            if (p == null) {
                throw new AssertionError("Paths.get(URI) returned null for valid URI");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid URI: " + e);
        }
    }

    private static void testNullSchemeURI() {
        try {
            // URI with null scheme triggers IllegalArgumentException according to WIT
            URI uri = new URI(null, "host", "/path", null);
            Paths.get(uri);
            throw new AssertionError("Expected IllegalArgumentException for URI with null scheme");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
