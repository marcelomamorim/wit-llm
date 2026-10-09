/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setSize(long) method
 * @run main ZipEntryWitupTest_Pdbf7ed6ff745
 */

public class ZipEntryWitupTest_Pdbf7ed6ff745 {
    public static void main(String[] args) {
        testSetSizeValid();
        testSetSizeZero();
        testSetSizeNegative();
    }

    private static void testSetSizeValid() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        entry.setSize(12345L);
        if (entry.size != 12345L) {
            throw new RuntimeException("setSize did not set the size correctly for positive value");
        }
    }

    private static void testSetSizeZero() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("zero");
        entry.setSize(0L);
        if (entry.size != 0L) {
            throw new RuntimeException("setSize did not set the size correctly for zero");
        }
    }

    private static void testSetSizeNegative() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("negative");
        try {
            entry.setSize(-1L);
            throw new RuntimeException("setSize did not throw IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
