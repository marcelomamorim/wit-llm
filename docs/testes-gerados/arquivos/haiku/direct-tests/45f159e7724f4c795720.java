/*
 * @test
 * @summary hasNext() of FileTreeIterator observed through Files.walk iterator
 * @run main FileTreeIteratorWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws IOException {
        emptyDirectoryYieldsOnlyStartDirectory();
        directoryWithFileYieldsTwoEntries();
        hasNextAfterStreamCloseThrowsIllegalStateException();
    }

    static void emptyDirectoryYieldsOnlyStartDirectory() throws IOException {
        Path dir = Files.createTempDirectory("ftiEmpty");
        try {
            try (Stream<Path> s = Files.walk(dir)) {
                Iterator<Path> it = s.iterator();
                check(it.hasNext());
                check(it.hasNext());
                check(dir.equals(it.next()));
                check(!it.hasNext());
                check(!it.hasNext());
            }
        } finally {
            Files.delete(dir);
        }
    }

    static void directoryWithFileYieldsTwoEntries() throws IOException {
        Path dir = Files.createTempDirectory("ftiFile");
        Path file = dir.resolve("a.txt");
        try {
            Files.createFile(file);
            int count = 0;
            try (Stream<Path> s = Files.walk(dir)) {
                Iterator<Path> it = s.iterator();
                while (it.hasNext()) {
                    it.next();
                    count++;
                }
                check(!it.hasNext());
            }
            check(count == 2);
        } finally {
            Files.delete(file);
            Files.delete(dir);
        }
    }

    static void hasNextAfterStreamCloseThrowsIllegalStateException() throws IOException {
        Path dir = Files.createTempDirectory("ftiClosed");
        try {
            Stream<Path> s = Files.walk(dir);
            Iterator<Path> it = s.iterator();
            s.close();
            boolean thrown = false;
            try {
                it.hasNext();
            } catch (IllegalStateException expected) {
                thrown = true;
            }
            check(thrown);
        } finally {
            Files.delete(dir);
        }
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
