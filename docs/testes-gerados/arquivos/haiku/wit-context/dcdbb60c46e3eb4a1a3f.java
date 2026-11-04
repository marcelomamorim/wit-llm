/*
 * @test
 * @summary Checks that the iterator returned by Files.walk yields the start directory and its child, then throws NoSuchElementException when exhausted
 * @run main FileTreeIteratorWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("FileTreeIteratorWitup");
        Path file = dir.resolve("a.txt");
        try {
            Files.createFile(file);
            try (Stream<Path> stream = Files.walk(dir)) {
                Iterator<Path> it = stream.iterator();
                int count = 0;
                while (it.hasNext()) {
                    it.next();
                    count++;
                }
                if (count != 2) {
                    throw new RuntimeException("Expected 2 entries but got " + count);
                }
                boolean thrown = false;
                try {
                    it.next();
                } catch (NoSuchElementException expected) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new RuntimeException("Expected NoSuchElementException after exhaustion");
                }
            }
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(dir);
        }
    }
}
