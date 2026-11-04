/*
 * @test
 * @summary Verify that JrtPath.toFile() always throws UnsupportedOperationException through the public Path API of the jrt file system
 * @run main JrtPathWitupTest
 */

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JrtPathWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrtfs = FileSystems.getFileSystem(URI.create("jrt:/"));

        Path absolute = jrtfs.getPath("/modules");
        expectUnsupported(absolute);

        Path nested = jrtfs.getPath("/java.base/java/lang/Object.class");
        expectUnsupported(nested);

        Path relative = jrtfs.getPath("java/lang/Object.class");
        expectUnsupported(relative);
    }

    private static void expectUnsupported(Path p) {
        File f = null;
        try {
            f = p.toFile();
        } catch (UnsupportedOperationException expected) {
            return;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected exception for " + p + ": " + e, e);
        }
        throw new RuntimeException("Expected UnsupportedOperationException for " + p
                + " but toFile() returned " + f);
    }
}
