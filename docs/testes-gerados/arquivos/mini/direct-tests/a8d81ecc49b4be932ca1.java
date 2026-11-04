/*
 * @test
 * @summary Test java.util.zip.ZipEntry.setCrc(long) method behavior
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testValidCrc();
        testNegativeCrc();
        testTooLargeCrc();
    }

    private static void testValidCrc() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        long[] validCrcs = {0L, 123456789L, 0xFFFFFFFFL};
        for (long crc : validCrcs) {
            entry.setCrc(crc);
            if (entry.crc != crc) {
                throw new RuntimeException("setCrc failed for valid value: " + crc);
            }
        }
    }

    private static void testNegativeCrc() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setCrc(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative crc");
        } catch (IllegalArgumentException e) {
            if (!"invalid entry crc-32".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testTooLargeCrc() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setCrc(0x100000000L); // 1 greater than 0xFFFFFFFF
            throw new RuntimeException("Expected IllegalArgumentException for too large crc");
        } catch (IllegalArgumentException e) {
            if (!"invalid entry crc-32".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
