/*
 * @test
 * @summary Verifies Files.createLink returns the link path, creates a hard link sharing content, and fails when the link name already exists
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FilesWitupTest {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("witup-createlink");
        Path existing = null;
        Path link = null;
        try {
            existing = Files.createTempFile(dir, "existing", ".txt");
            byte[] content = "payload".getBytes(StandardCharsets.UTF_8);
            Files.write(existing, content);
            link = dir.resolve("link.txt");

            Path returned = Files.createLink(link, existing);
            if (!link.equals(returned)) {
                throw new RuntimeException("createLink must return the link path");
            }
            if (!Files.exists(link)) {
                throw new RuntimeException("link must exist after createLink");
            }
            if (!Files.isSameFile(link, existing)) {
                throw new RuntimeException("link must refer to the same file as existing");
            }
            if (!Arrays.equals(Files.readAllBytes(link), content)) {
                throw new RuntimeException("link must expose the same content");
            }

            boolean failed = false;
            try {
                Files.createLink(link, existing);
            } catch (IOException e) {
                failed = true;
            }
            if (!failed) {
                throw new RuntimeException("createLink must fail when link already exists");
            }
            if (!Arrays.equals(Files.readAllBytes(link), content)) {
                throw new RuntimeException("existing link must be preserved after failed call");
            }
        } finally {
            if (link != null) {
                Files.deleteIfExists(link);
            }
            if (existing != null) {
                Files.deleteIfExists(existing);
            }
            Files.deleteIfExists(dir);
        }
    }
}
