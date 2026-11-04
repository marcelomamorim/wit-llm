/*
 * @test
 * @summary Tests for java.util.zip.ZipEntry class methods
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetMethodValid();
        testSetMethodInvalid();
    }

    public static void testSetMethodValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            // Test with valid method value: STORED (0)
            entry.setMethod(ZipEntry.STORED);
            // Test with valid method value: DEFLATED (8)
            entry.setMethod(ZipEntry.DEFLATED);
        } catch (Exception e) {
            throw new AssertionError("Valid method values should not throw", e);
        }
    }

    public static void testSetMethodInvalid() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            // Test with invalid method value: 1
            entry.setMethod(1);
            throw new AssertionError("Expected IllegalArgumentException for method=1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            // Test with invalid method value: 5
            entry.setMethod(5);
            throw new AssertionError("Expected IllegalArgumentException for method=5");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            // Test with invalid method value: -1
            entry.setMethod(-1);
            throw new AssertionError("Expected IllegalArgumentException for method=-1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
