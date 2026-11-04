/*
 * @test
 * @summary File.deleteOnExit registers valid paths without changing existence before exit and silently ignores invalid paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws IOException {
        testValidFileStillExistsAfterRegistration();
        testRepeatedRegistrationDoesNotThrow();
        testInvalidPathWithNulIsIgnored();
    }

    static void testValidFileStillExistsAfterRegistration() throws IOException {
        File f = File.createTempFile("witup", ".tmp");
        try {
            f.deleteOnExit();
            if (!f.exists()) {
                throw new RuntimeException("file should still exist before JVM exit");
            }
        } finally {
            f.delete();
        }
    }

    static void testRepeatedRegistrationDoesNotThrow() throws IOException {
        File f = File.createTempFile("witup", ".tmp");
        try {
            f.deleteOnExit();
            f.deleteOnExit();
            if (!f.exists()) {
                throw new RuntimeException("file should still exist after repeated registration");
            }
        } finally {
            f.delete();
        }
    }

    static void testInvalidPathWithNulIsIgnored() {
        File invalid = new File("a\u0000b");
        invalid.deleteOnExit();
        if (invalid.exists()) {
            throw new RuntimeException("invalid path should not exist");
        }
    }
}
