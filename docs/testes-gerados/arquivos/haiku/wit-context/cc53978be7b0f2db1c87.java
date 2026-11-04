/*
 * @test
 * @summary Verify that the jrt file system getUserPrincipalLookupService throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        boolean thrown = false;
        try {
            fs.getUserPrincipalLookupService();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException on first call");
        }

        thrown = false;
        try {
            fs.getUserPrincipalLookupService();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException on second call");
        }
    }
}
