/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setMethod(int) for valid and invalid inputs
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testValidMethodStored();
        testValidMethodDeflated();
        testInvalidMethod();
    }

    private static void testValidMethodStored() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        entry.setMethod(java.util.zip.ZipEntry.STORED);
        if (entry.method != java.util.zip.ZipEntry.STORED) {
            throw new RuntimeException("setMethod(STORED) did not set method correctly");
        }
    }

    private static void testValidMethodDeflated() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        entry.setMethod(java.util.zip.ZipEntry.DEFLATED);
        if (entry.method != java.util.zip.ZipEntry.DEFLATED) {
            throw new RuntimeException("setMethod(DEFLATED) did not set method correctly");
        }
    }

    private static void testInvalidMethod() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setMethod(-1);
            throw new RuntimeException("setMethod(-1) did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            entry.setMethod(1);
            throw new RuntimeException("setMethod(1) did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            entry.setMethod(9);
            throw new RuntimeException("setMethod(9) did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
