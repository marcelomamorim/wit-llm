/*
 * @test
 * @summary Test ZipEntry.setMethod(int) for valid and invalid compression methods
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetMethodValid();
        testSetMethodInvalid();
    }

    private static void testSetMethodValid() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        entry.setMethod(java.util.zip.ZipEntry.STORED);
        if (entry.getMethod() != java.util.zip.ZipEntry.STORED) {
            throw new RuntimeException("setMethod failed for STORED");
        }
        entry.setMethod(java.util.zip.ZipEntry.DEFLATED);
        if (entry.getMethod() != java.util.zip.ZipEntry.DEFLATED) {
            throw new RuntimeException("setMethod failed for DEFLATED");
        }
    }

    private static void testSetMethodInvalid() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setMethod(-1);
            throw new RuntimeException("setMethod did not throw IllegalArgumentException for -1");
        } catch (IllegalArgumentException expected) {
        }
        try {
            entry.setMethod(1);
            throw new RuntimeException("setMethod did not throw IllegalArgumentException for 1");
        } catch (IllegalArgumentException expected) {
        }
        try {
            entry.setMethod(9);
            throw new RuntimeException("setMethod did not throw IllegalArgumentException for 9");
        } catch (IllegalArgumentException expected) {
        }
    }
}
