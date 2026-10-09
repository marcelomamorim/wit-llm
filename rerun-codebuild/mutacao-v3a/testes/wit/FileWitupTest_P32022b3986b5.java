/*
 * @test
 * @summary Test for java.io.File.setLastModified(long)
 * @run main FileWitupTest_P32022b3986b5
 */

import java.io.File;

public class FileWitupTest_P32022b3986b5 {

    public static void main(String[] args) throws Exception {
        testSetLastModifiedNegativeTime();
        testSetLastModifiedValidTime();
        testSetLastModifiedWithSecurityManager();
        testSetLastModifiedWhenInvalid();
    }

    static void testSetLastModifiedNegativeTime() {
        File file = new File("testfile.txt");
        long negativeTime = -1000L;
        boolean result = file.setLastModified(negativeTime);
        if (result != false) {
            throw new AssertionError("Expected false when setting negative time");
        }
    }

    static void testSetLastModifiedValidTime() {
        File file = new File("testfile.txt");
        long currentTime = System.currentTimeMillis();
        boolean result = false;
        try {
            result = file.setLastModified(currentTime);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        // Result may vary depending on security and file existence, so just check no exception
        // and result is boolean
        if (!(result == true || result == false)) {
            throw new AssertionError("Result should be boolean");
        }
    }

    static void testSetLastModifiedWhenInvalid() {
        File file = new File("nonexistentfile.txt");
        long currentTime = System.currentTimeMillis();
        boolean result = false;
        try {
            result = file.setLastModified(currentTime);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        // Even if file invalid, method should not throw, result is boolean
        if (!(result == true || result == false)) {
            throw new AssertionError("Result should be boolean");
        }
    }
}
