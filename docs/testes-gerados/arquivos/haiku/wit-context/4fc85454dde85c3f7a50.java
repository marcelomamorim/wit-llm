/*
 * @test
 * @summary Witup-derived checks for File.setLastModified negative time, missing file, invalid path and existing file behavior
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTimeThrows();
        testNegativeMinValueThrows();
        testMissingFileReturnsFalse();
        testInvalidPathReturnsFalse();
        testExistingFileUpdatesTime();
    }

    static void testNegativeTimeThrows() {
        File f = new File("witup_missing_negative_time.tmp");
        try {
            f.setLastModified(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNegativeMinValueThrows() {
        File f = new File("witup_missing_min_value.tmp");
        try {
            f.setLastModified(Long.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMissingFileReturnsFalse() {
        File f = new File("witup_definitely_missing_file_" + System.nanoTime() + ".tmp");
        if (f.exists()) {
            throw new AssertionError("Test precondition failed: file exists");
        }
        if (f.setLastModified(1000000000000L)) {
            throw new AssertionError("setLastModified on missing file must return false");
        }
    }

    static void testInvalidPathReturnsFalse() {
        File f = new File("bad\u0000name.tmp");
        if (f.setLastModified(0L)) {
            throw new AssertionError("setLastModified on invalid path must return false");
        }
    }

    static void testExistingFileUpdatesTime() throws IOException {
        File f = File.createTempFile("witup", ".tmp");
        try {
            long t = 1000000000000L;
            if (!f.setLastModified(t)) {
                throw new AssertionError("setLastModified on existing file must return true");
            }
            if (f.lastModified() != t) {
                throw new AssertionError("lastModified mismatch: expected " + t
                        + " but was " + f.lastModified());
            }
        } finally {
            f.delete();
        }
    }
}
