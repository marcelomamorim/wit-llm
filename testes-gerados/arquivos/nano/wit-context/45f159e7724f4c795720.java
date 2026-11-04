/* @test    @summary Test for FileTreeIterator.hasNext() method to verify behavior when iterator is closed.    @run main FileTreeIteratorWitupTest */

import java.io.UncheckedIOException;
import java.io.IOException;
import java.nio.file.FileTreeIterator;
import java.nio.file.FileTreeWalker;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws IOException {
        testHasNextWhenOpen();
        testHasNextWhenClosed();
    }

    private static void testHasNextWhenOpen() throws IOException {
        Path startPath = Paths.get(".");
        try (FileTreeIterator iterator = new FileTreeIterator(startPath, 1)) {
            boolean hasNext = iterator.hasNext();
            if (hasNext != (iterator.next() != null)) {
                throw new AssertionError("Expected hasNext to match next() presence");
            }
        }
    }

    private static void testHasNextWhenClosed() throws IOException {
        Path startPath = Paths.get(".");
        FileTreeIterator iterator = new FileTreeIterator(startPath, 1);
        // Manually close the underlying walker to simulate closed state
        // Note: walker is private; simulate by calling close and then attempting hasNext
        iterator.close();
        try {
            iterator.hasNext();
            throw new AssertionError("Expected IllegalStateException when calling hasNext() after close");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
