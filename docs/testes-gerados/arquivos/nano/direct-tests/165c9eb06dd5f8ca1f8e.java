/*
 * @test
 * @summary Test for Files.readSymbolicLink(Path)
 * @run main FilesWitupTest
 */

import java.nio.file.*;
import java.io.IOException;

public class FilesWitupTest {

    public static void main(String[] args) {
        testReadSymbolicLinkValid();
        testReadSymbolicLinkInvalid();
    }

    static void testReadSymbolicLinkValid() {
        Path target = null;
        Path link = null;
        try {
            target = Files.createTempFile("target", ".txt");
            link = Files.createTempFile("link", ".lnk");
            Files.deleteIfExists(link);
            Files.createSymbolicLink(link, target.getFileName());
            Path result = Files.readSymbolicLink(link);
            if (!result.equals(target.getFileName())) {
                throw new AssertionError("Expected: " + target.getFileName() + ", but got: " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid symbolic link test", e);
        } finally {
            try {
                if (target != null) Files.deleteIfExists(target);
                if (link != null) Files.deleteIfExists(link);
            } catch (IOException e) {
                // ignore cleanup exceptions
            }
        }
    }

    static void testReadSymbolicLinkInvalid() {
        Path invalidLink = null;
        try {
            invalidLink = Files.createTempFile("invalid", ".lnk");
            Files.deleteIfExists(invalidLink);
            // Create a regular file with the same name to cause IOException
            Files.createFile(invalidLink);
            try {
                Files.readSymbolicLink(invalidLink);
                throw new AssertionError("Expected IOException for non-symlink path");
            } catch (IOException e) {
                // expected exception
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in invalid symbolic link test", e);
        } finally {
            try {
                if (invalidLink != null) Files.deleteIfExists(invalidLink);
            } catch (IOException e) {
                // ignore cleanup exceptions
            }
        }
    }
}
