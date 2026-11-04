/*
 * @test
 * @summary Verifies that Files.readSymbolicLink returns the target path stored in a symbolic link
 * @run main FilesWitupTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesWitupTest {
    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("readlink");
        Path target = dir.resolve("target.txt");
        Path absLink = dir.resolve("abs-link.txt");
        Path relLink = dir.resolve("rel-link.txt");
        boolean symlinksSupported = true;
        try {
            Files.write(target, new byte[0]);
            try {
                Files.createSymbolicLink(absLink, target);
                Files.createSymbolicLink(relLink, Paths.get("target.txt"));
            } catch (UnsupportedOperationException | IOException e) {
                symlinksSupported = false;
                System.out.println("Symbolic links not supported; skipping: " + e);
            }

            if (symlinksSupported) {
                Path readAbs = Files.readSymbolicLink(absLink);
                if (!target.equals(readAbs)) {
                    throw new RuntimeException("Expected " + target + " but got " + readAbs);
                }

                Path readRel = Files.readSymbolicLink(relLink);
                if (!Paths.get("target.txt").equals(readRel)) {
                    throw new RuntimeException("Expected target.txt but got " + readRel);
                }
            }
        } finally {
            Files.deleteIfExists(absLink);
            Files.deleteIfExists(relLink);
            Files.deleteIfExists(target);
            Files.deleteIfExists(dir);
        }
    }
}
