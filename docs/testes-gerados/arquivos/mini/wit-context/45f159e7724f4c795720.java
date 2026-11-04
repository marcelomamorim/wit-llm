/*
 * @test
 * @summary Test FileTreeIterator.hasNext() behavior including IllegalStateException when closed
 * @run main FileTreeIteratorWitupTest
 */

import java.nio.file.FileTreeIterator;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        testHasNextThrowsWhenClosed();
    }

    private static void testHasNextThrowsWhenClosed() throws IOException {
        // Use a valid existing path for construction; use current directory as safe choice
        Path start = Paths.get(".");
        FileTreeIterator fti = new FileTreeIterator(start, 1, new FileVisitOption[0]);

        // Close the underlying walker to simulate closed state
        // The walker field is private and no public close method is exposed on FileTreeIterator,
        // but FileTreeIterator implements Closeable, so close() can be called.
        fti.close();

        boolean threw = false;
        try {
            fti.hasNext();
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling hasNext() after close()");
        }
    }
}
