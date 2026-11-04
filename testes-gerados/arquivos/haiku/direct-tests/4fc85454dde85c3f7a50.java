/*
 * @test
 * @summary File.setLastModified: negative time rejection, invalid path and missing file return false, and read-back of set times
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) throws IOException {
        testNegativeTimeThrows();
        testInvalidPathReturnsFalse();
        testMissingFileReturnsFalse();
        testSetAndReadBack();
        testEpochZero();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeTimeThrows() {
        boolean thrown = false;
        try {
            new File("negative_time_probe").setLastModified(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "Negative time must throw IllegalArgumentException");
    }

    static void testInvalidPathReturnsFalse() {
        boolean result = new File("bad\0name").setLastModified(1000L);
        check(!result, "Invalid path (NUL character) must return false");
    }

    static void testMissingFileReturnsFalse() {
        File f = new File("does_not_exist_" + System.nanoTime() + ".tmp");
        check(!f.exists(), "Test file unexpectedly exists");
        check(!f.setLastModified(1000L), "Missing file must return false");
    }

    static void testSetAndReadBack() throws IOException {
        File f = new File("FileWitupTest_" + System.nanoTime() + ".tmp");
        check(f.createNewFile(), "Could not create test file");
        try {
            long t = 1500000000000L;
            check(f.setLastModified(t), "setLastModified should return true for existing file");
            check(f.lastModified() == t, "lastModified should equal the time that was set");
        } finally {
            f.delete();
        }
    }

    static void testEpochZero() throws IOException {
        File f = new File("FileWitupTest_zero_" + System.nanoTime() + ".tmp");
        check(f.createNewFile(), "Could not create test file");
        try {
            check(f.setLastModified(0L), "setLastModified(0) should return true for existing file");
            check(f.lastModified() == 0L, "lastModified should be 0 after setting epoch time");
        } finally {
            f.delete();
        }
    }
}
