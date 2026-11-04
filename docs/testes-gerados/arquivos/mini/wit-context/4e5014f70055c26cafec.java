/*
 * @test
 * @summary Test ZipEntry.setExtra(byte[]) for length limit and null
 * @run main ZipEntryWitupTest
 */

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testSetExtraNull();
        testSetExtraMaxLength();
        testSetExtraTooLong();
    }

    private static void testSetExtraNull() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        // Setting null extra should not throw and should be accepted
        entry.setExtra(null);
        // No exception expected
    }

    private static void testSetExtraMaxLength() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        // Create a byte array of length 65535 (max allowed)
        byte[] maxExtra = new byte[65535];
        // Should not throw
        entry.setExtra(maxExtra);
    }

    private static void testSetExtraTooLong() {
        java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry("test");
        // Create a byte array longer than 65535
        byte[] tooLongExtra = new byte[65536];
        try {
            entry.setExtra(tooLongExtra);
            throw new RuntimeException("Expected IllegalArgumentException for extra length > 65535");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }
}
