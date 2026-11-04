/*
 * @test
 * @summary Test java.nio.file.Paths.get(URI) method behavior
 * @run main PathsWitupTest
 */

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystemNotFoundException;
import java.lang.IllegalArgumentException;

public class PathsWitupTest {
    public static void main(String[] args) throws Exception {
        testValidUri();
        testInvalidUriScheme();
        testNonExistingFileSystemUri();
    }

    private static void testValidUri() throws Exception {
        // Use a valid URI with "file" scheme
        URI uri = new URI("file:///tmp");
        Path path = Paths.get(uri);
        if (path == null) {
            throw new AssertionError("Paths.get(URI) returned null for valid URI");
        }
        // The path should be absolute and end with "tmp"
        if (!path.isAbsolute()) {
            throw new AssertionError("Path is not absolute for file URI");
        }
        String name = path.getFileName().toString();
        if (!"tmp".equals(name)) {
            throw new AssertionError("Expected path file name 'tmp' but got: " + name);
        }
    }

    private static void testInvalidUriScheme() {
        // Use a URI with an invalid scheme to provoke IllegalArgumentException
        try {
            URI uri = new URI("invalidscheme:///some/path");
            Paths.get(uri);
            throw new AssertionError("Expected IllegalArgumentException for invalid scheme URI");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException but got " + e);
        }
    }

    private static void testNonExistingFileSystemUri() {
        // Use a URI with a scheme that is valid but no provider installed, expecting FileSystemNotFoundException
        try {
            URI uri = new URI("nosuchfs:///some/path");
            Paths.get(uri);
            throw new AssertionError("Expected FileSystemNotFoundException for unknown file system URI");
        } catch (FileSystemNotFoundException e) {
            // Expected
        } catch (IllegalArgumentException e) {
            // Some implementations may throw IllegalArgumentException if scheme is invalid
            // Acceptable fallback
        } catch (Exception e) {
            throw new AssertionError("Expected FileSystemNotFoundException or IllegalArgumentException but got " + e);
        }
    }
}
