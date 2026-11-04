/*
 * @test
 * @summary Observable behavior of Files.walk, which is backed by FileTreeIterator.hasNext and next
 * @run main FileTreeIteratorWitupTest
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileTreeIteratorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("ftiWitup");
        Path a = dir.resolve("a.txt");
        Path sub = dir.resolve("sub");
        Path nested = sub.resolve("b.txt");
        try {
            Files.createFile(a);
            Files.createDirectory(sub);
            Files.createFile(nested);

            List<Path> seen = new ArrayList<>();
            try (Stream<Path> s = Files.walk(dir)) {
                s.forEach(seen::add);
            }
            check(seen.size() == 4, "expected 4 entries, got " + seen.size());
            check(seen.contains(dir), "missing start directory");
            check(seen.contains(a), "missing a.txt");
            check(seen.contains(sub), "missing sub");
            check(seen.contains(nested), "missing nested file");

            List<Path> shallow = new ArrayList<>();
            try (Stream<Path> s = Files.walk(dir, 1)) {
                s.forEach(shallow::add);
            }
            check(shallow.size() == 3, "expected 3 entries at depth 1, got " + shallow.size());
            check(!shallow.contains(nested), "nested file must not appear at maxDepth 1");

            Path missing = dir.resolve("missing");
            boolean thrown = false;
            try {
                Files.walk(missing).close();
            } catch (UncheckedIOException e) {
                thrown = true;
                check(e.getCause() instanceof IOException, "cause must be an IOException");
            }
            check(thrown, "walking a missing start path must fail");
        } finally {
            Files.deleteIfExists(nested);
            Files.deleteIfExists(sub);
            Files.deleteIfExists(a);
            Files.deleteIfExists(dir);
        }
    }
}
