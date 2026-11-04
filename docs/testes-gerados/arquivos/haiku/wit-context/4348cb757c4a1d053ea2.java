/*
 * @test
 * @summary Verify that the iterator returned by JrtPath.iterator() rejects remove() with ReadOnlyFileSystemException
 * @run main JrtPathWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.Iterator;

public class JrtPathWitupTest {

    public static void main(String[] args) throws Exception {
        Path root = FileSystems.getFileSystem(URI.create("jrt:/")).getPath("/modules");
        Iterator<Path> it = root.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (ReadOnlyFileSystemException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ReadOnlyFileSystemException from remove()");
        }

        Path other = FileSystems.getFileSystem(URI.create("jrt:/")).getPath("/");
        Iterator<Path> it2 = other.iterator();
        thrown = false;
        try {
            it2.remove();
        } catch (ReadOnlyFileSystemException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ReadOnlyFileSystemException from remove() on root");
        }
    }
}
