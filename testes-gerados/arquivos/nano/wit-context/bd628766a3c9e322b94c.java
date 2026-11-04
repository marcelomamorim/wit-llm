/*
 * @test
 * @summary Test getFileSystem method for various URI schemes and authorities
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) {
        testGetFileSystemWithValidUri();
        testGetFileSystemWithInvalidScheme();
    }

    public static void testGetFileSystemWithValidUri() {
        try {
            URI validUri = new URI("jrt:/");
            jdk.internal.jrtfs.JrtFileSystemProvider provider = new jdk.internal.jrtfs.JrtFileSystemProvider();
            FileSystem fs = provider.getFileSystem(validUri);
            if (fs == null) {
                throw new AssertionError("Expected non-null FileSystem for valid URI");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid URI: " + e);
        }
    }

    public static void testGetFileSystemWithInvalidScheme() {
        try {
            URI invalidUri = new URI("http:/");
            jdk.internal.jrtfs.JrtFileSystemProvider provider = new jdk.internal.jrtfs.JrtFileSystemProvider();
            provider.getFileSystem(invalidUri);
            throw new AssertionError("Expected IllegalArgumentException for invalid scheme");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
