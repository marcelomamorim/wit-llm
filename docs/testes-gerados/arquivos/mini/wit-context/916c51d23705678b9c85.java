/*
 * @test
 * @summary Test ZipEntry.setSize(long) for valid and invalid inputs
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetSizeValid();
        testSetSizeNegative();
    }

    private static void testSetSizeValid() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        entry.setSize(0L); // boundary valid
        entry.setSize(123456789L); // typical valid
        // No exception expected
    }

    private static void testSetSizeNegative() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setSize(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("invalid entry size")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
