/*
 * @test
 * @summary Files.createLink creates a hard link to an existing file and returns the link path; a second creation on an existing link fails and leaves the link unchanged
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("filesWitup");
        Path existing = dir.resolve("existing.txt");
        Path link = dir.resolve("link.txt");
        Path other = dir.resolve("other.txt");
        try {
            Files.write(existing, "hello".getBytes(StandardCharsets.UTF_8));
            Files.write(other, "x".getBytes(StandardCharsets.UTF_8));

            Path returned = Files.createLink(link, existing);
            check(returned.equals(link), "createLink must return the link path");
            check(Files.exists(link), "link must exist after createLink");
            check(Files.isSameFile(link, existing), "link must refer to the same file as existing");
            String content = new String(Files.readAllBytes(link), StandardCharsets.UTF_8);
            check(content.equals("hello"), "link must expose existing content, got: " + content);

            boolean failed = false;
            try {
                Files.createLink(link, other);
            } catch (IOException e) {
                failed = true;
            }
            check(failed, "createLink on an existing link path must fail with IOException");
            String after = new String(Files.readAllBytes(link), StandardCharsets.UTF_8);
            check(after.equals("hello"), "failed createLink must not modify the link, got: " + after);
            check(Files.isSameFile(link, existing), "link must still refer to existing after failure");
        } finally {
            Files.deleteIfExists(link);
            Files.deleteIfExists(existing);
            Files.deleteIfExists(other);
            Files.deleteIfExists(dir);
        }
    }
}
