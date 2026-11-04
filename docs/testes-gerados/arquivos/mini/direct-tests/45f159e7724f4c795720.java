/*
 * @test
 * @summary Test FileTreeIterator.hasNext() behavior
 * @run main FileTreeIteratorWitupTest
 */

import java.nio.file.FileTreeIterator;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.lang.IllegalStateException;

public class FileTreeIteratorWitupTest {
    public static void main(String[] args) throws IOException {
        testHasNextOnOpenIterator();
        testHasNextThrowsAfterClose();
    }

    private static void testHasNextOnOpenIterator() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 1, new FileVisitOption[0]);
        try {
            boolean hasNext = it.hasNext();
            if (!(hasNext == true || hasNext == false)) {
                throw new AssertionError("hasNext() returned invalid boolean");
            }
        } finally {
            it.close();
        }
    }

    private static void testHasNextThrowsAfterClose() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 1, new FileVisitOption[0]);
        it.close();
        try {
            it.hasNext();
            throw new AssertionError("Expected IllegalStateException after close()");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
