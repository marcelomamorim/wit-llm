/*
 * @test
 * @summary Test ZipEntry.setCrc(long) for valid and invalid CRC values
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testValidCrc();
        testInvalidCrcNegative();
        testInvalidCrcTooLarge();
    }

    private static void testValidCrc() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        long validCrc = 0x12345678L;
        entry.setCrc(validCrc);
        if (entry.getCrc() != validCrc) {
            throw new RuntimeException("setCrc failed to set valid CRC correctly");
        }
    }

    private static void testInvalidCrcNegative() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setCrc(-1L);
            throw new RuntimeException("setCrc did not throw IllegalArgumentException for negative CRC");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidCrcTooLarge() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        try {
            entry.setCrc(0x100000000L); // 1 greater than 0xFFFFFFFF
            throw new RuntimeException("setCrc did not throw IllegalArgumentException for too large CRC");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
