/*
 * @test
 * @summary Behavioral tests for FileTreeIterator.next() reached through the public Files.walk API
 * @run main FileTreeIteratorWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("FileTreeIteratorWitup");
        try {
            Path sub = Files.createDirectory(dir.resolve("sub"));
            Path file = Files.createFile(sub.resolve("f.txt"));
            testWalkYieldsAllEntries(dir, sub, file);
            testExhaustedIteratorThrowsNoSuchElement(dir);
            testMaxDepthZeroYieldsOnlyStart(dir);
            testNonexistentStartThrowsNoSuchFile(dir);
        } finally {
            deleteRecursively(dir);
        }
    }

    static void testWalkYieldsAllEntries(Path dir, Path sub, Path file) throws IOException {
        Set<Path> expected = new HashSet<>(Arrays.asList(dir, sub, file));
        Set<Path> actual = new HashSet<>();
        try (Stream<Path> s = Files.walk(dir)) {
            Iterator<Path> it = s.iterator();
            while (it.hasNext()) {
                actual.add(it.next());
            }
        }
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void testExhaustedIteratorThrowsNoSuchElement(Path dir) throws IOException {
        try (Stream<Path> s = Files.walk(dir)) {
            Iterator<Path> it = s.iterator();
            while (it.hasNext()) {
                it.next();
            }
            if (it.hasNext()) {
                throw new RuntimeException("hasNext() should be false after exhaustion");
            }
            try {
                it.next();
                throw new RuntimeException("next() after exhaustion did not throw");
            } catch (NoSuchElementException expectedException) {
                // expected
            }
        }
    }

    static void testMaxDepthZeroYieldsOnlyStart(Path dir) throws IOException {
        List<Path> result;
        try (Stream<Path> s = Files.walk(dir, 0)) {
            result = s.collect(Collectors.toList());
        }
        if (result.size() != 1 || !result.get(0).equals(dir)) {
            throw new RuntimeException("maxDepth 0 should yield only start, got " + result);
        }
    }

    static void testNonexistentStartThrowsNoSuchFile(Path dir) throws IOException {
        Path missing = dir.resolve("does-not-exist");
        try {
            Files.walk(missing).close();
            throw new RuntimeException("Expected NoSuchFileException");
        } catch (NoSuchFileException expectedException) {
            // expected
        }
    }

    static void deleteRecursively(Path dir) throws IOException {
        List<Path> paths;
        try (Stream<Path> s = Files.walk(dir)) {
            paths = s.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        for (Path p : paths) {
            Files.delete(p);
        }
    }
}
