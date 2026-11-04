/*
 * @test
 * @summary Verifies Files.readSymbolicLink returns the link target for symbolic links and throws IOException for non-link paths
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWitupTest {

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("witup");
        Path target = dir.resolve("target.txt");
        Path link = dir.resolve("link");
        try {
            Files.write(target, new byte[0]);

            try {
                Files.createSymbolicLink(link, target);
            } catch (IOException e) {
                System.out.println("Symbolic link creation not permitted; skipping link case: " + e);
            }

            if (Files.isSymbolicLink(link)) {
                Path resolved = Files.readSymbolicLink(link);
                if (!resolved.equals(target)) {
                    throw new RuntimeException("Unexpected target: " + resolved + " expected: " + target);
                }
            }

            boolean ioeThrown = false;
            try {
                Files.readSymbolicLink(target);
            } catch (IOException e) {
                ioeThrown = true;
            }
            if (!ioeThrown) {
                throw new RuntimeException("readSymbolicLink on a non-link file did not throw IOException");
            }
        } finally {
            Files.deleteIfExists(link);
            Files.deleteIfExists(target);
            Files.deleteIfExists(dir);
        }
    }
}
