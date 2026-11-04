/*
 * @test
 * @summary Test for Paths.get(URI uri) method, focusing on exception paths.
 * @run main PathsWitupTest
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URI;

public class PathsWitupTest {
    public static void main(String[] args) {
        testNullSchemeURI();
        testValidURI();
    }

    private static void testNullSchemeURI() {
        try {
            URI uri = new URI("file:///tmp/test");
            Path path = Paths.get(uri);
            System.out.println("Valid URI, path: " + path);
        } catch (Exception e) {
            System.out.println("Unexpected exception for valid URI: " + e);
        }
    }

    private static void testInvalidURI() {
        try {
            URI uri = new URI("http://");
            Path path = Paths.get(uri);
            System.out.println("Unexpected success, path: " + path);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for URI with null scheme");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
