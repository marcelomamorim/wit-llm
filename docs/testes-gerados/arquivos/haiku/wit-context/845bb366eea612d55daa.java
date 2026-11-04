/*
 * @test
 * @summary Checks File.getCanonicalPath behavior for invalid (NUL-containing) and valid paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testNulCharacterPathThrowsIOException();
        testValidPathReturnsStableAbsoluteCanonicalPath();
    }

    static void testNulCharacterPathThrowsIOException() {
        File f = new File("a\0b");
        try {
            f.getCanonicalPath();
            throw new RuntimeException("Expected IOException for path containing NUL");
        } catch (IOException e) {
            if (!"Invalid file path".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testValidPathReturnsStableAbsoluteCanonicalPath() throws IOException {
        File f = new File(".");
        String canonical = f.getCanonicalPath();
        if (canonical == null || canonical.isEmpty()) {
            throw new RuntimeException("Canonical path is null or empty");
        }
        if (!new File(canonical).isAbsolute()) {
            throw new RuntimeException("Canonical path is not absolute: " + canonical);
        }
        String again = new File(canonical).getCanonicalPath();
        if (!canonical.equals(again)) {
            throw new RuntimeException("Canonical path not stable: " + canonical + " vs " + again);
        }
    }
}
