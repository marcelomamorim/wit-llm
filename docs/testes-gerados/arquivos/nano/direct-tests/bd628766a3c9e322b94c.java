/* @test    @summary Test getFileSystem method of JrtFileSystemProvider for valid URI    @run main JrtFileSystemProviderWitupTest */

import java.net.URI;
import java.nio.file.FileSystem;
import java.security.Permission;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) {
        testGetFileSystemWithValidUri();
    }

    public static void testGetFileSystemWithValidUri() {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        URI validUri = URI.create("jrt:/");
        try {
            FileSystem fs = provider.getFileSystem(validUri);
            if (fs == null) {
                throw new AssertionError("Expected non-null FileSystem");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
