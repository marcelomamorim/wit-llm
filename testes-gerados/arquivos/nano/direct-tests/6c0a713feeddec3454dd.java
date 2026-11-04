/* @test    @summary Test java.nio.file.Paths.get(URI) method with valid URI    @run main PathsWitupTest */

import java.nio.file.Paths;
import java.nio.file.Path;
import java.net.URI;

public class PathsWitupTest {
    public static void main(String[] args) {
        testGetWithValidURI();
        testGetWithInvalidURI();
    }

    static void testGetWithValidURI() {
        URI uri = URI.create("file:///tmp/testfile");
        Path path = Paths.get(uri);
        if (path == null) {
            throw new AssertionError("Path should not be null for valid URI");
        }
        String expectedPath = "file:///tmp/testfile";
        if (!path.toString().equals(expectedPath)) {
            throw new AssertionError("Expected path: " + expectedPath + ", but got: " + path.toString());
        }
    }

    static void testGetWithInvalidURI() {
        try {
            URI invalidUri = URI.create("");
            Path path = Paths.get(invalidUri);
            if (path == null) {
                throw new AssertionError("Path should not be null even for empty URI");
            }
        } catch (IllegalArgumentException e) {
            // Expected exception for invalid URI format
        }
    }
}
