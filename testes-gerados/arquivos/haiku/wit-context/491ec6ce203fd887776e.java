/*
 * @test
 * @summary WITUP-derived behavioral checks for File.renameTo(File)
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws IOException {
        testNullDestinationWithoutSecurityManagerThrowsNPE();
        testInvalidSourceReturnsFalse();
        testInvalidDestinationReturnsFalse();
        testMissingSourceReturnsFalse();
        testSuccessfulRename();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullDestinationWithoutSecurityManagerThrowsNPE() {
        File src = new File("witup-null-dest-src.tmp");
        try {
            src.renameTo(null);
            throw new RuntimeException("expected NullPointerException for null dest");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testInvalidSourceReturnsFalse() {
        File src = new File("witup-bad\0source");
        File dst = new File("witup-dst-ok.tmp");
        check(!src.renameTo(dst), "renameTo must return false for invalid source path");
        check(!dst.exists(), "destination must not be created for invalid source");
    }

    static void testInvalidDestinationReturnsFalse() throws IOException {
        File src = File.createTempFile("witup", ".src");
        try {
            File dst = new File("witup-bad\0dest");
            check(!src.renameTo(dst), "renameTo must return false for invalid destination path");
            check(src.exists(), "source must remain after failed rename");
        } finally {
            src.delete();
        }
    }

    static void testMissingSourceReturnsFalse() {
        long stamp = System.nanoTime();
        File src = new File("witup-missing-" + stamp + ".tmp");
        File dst = new File("witup-missing-dst-" + stamp + ".tmp");
        check(!src.renameTo(dst), "renameTo must return false when source does not exist");
        check(!dst.exists(), "destination must not be created when source is missing");
    }

    static void testSuccessfulRename() throws IOException {
        File src = File.createTempFile("witup", ".src");
        File dst = new File(src.getParentFile(), "witup-dst-" + System.nanoTime() + ".tmp");
        try {
            check(src.exists(), "source must exist before rename");
            check(src.renameTo(dst), "renameTo must succeed for existing source in same directory");
            check(!src.exists(), "source must not exist after successful rename");
            check(dst.exists(), "destination must exist after successful rename");
        } finally {
            src.delete();
            dst.delete();
        }
    }
}
