/*
 * @test
 * @summary JrtFileSystem.getUserPrincipalLookupService() must throw UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.attribute.UserPrincipalLookupService;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        if (!fs.isOpen()) {
            throw new RuntimeException("jrt file system is not open");
        }
        expectUnsupported(fs);
        expectUnsupported(fs);
    }

    static void expectUnsupported(FileSystem fs) {
        try {
            UserPrincipalLookupService s = fs.getUserPrincipalLookupService();
            throw new RuntimeException("expected UnsupportedOperationException, got: " + s);
        } catch (UnsupportedOperationException expected) {
            // expected behavior
        }
    }
}
