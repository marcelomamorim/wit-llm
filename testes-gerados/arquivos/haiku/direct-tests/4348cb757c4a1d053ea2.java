/*
 * @test
 * @summary Verifies that the Iterator.remove() of a jrt directory stream throws ReadOnlyFileSystemException
 * @run main JrtPathWitupTest
 */

import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.Iterator;

public class JrtPathWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path modules = jrt.getPath("/modules");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(modules)) {
            Iterator<Path> it = ds.iterator();
            if (!it.hasNext()) {
                throw new RuntimeException("expected at least one entry in /modules");
            }
            it.next();
            boolean thrown = false;
            try {
                it.remove();
            } catch (ReadOnlyFileSystemException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("remove() did not throw ReadOnlyFileSystemException");
            }
        }
    }
}
