/* @test    @summary Test setCrc method of ZipEntry for valid and invalid inputs    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetCrcValid();
        testSetCrcNegative();
        testSetCrcExceedsMax();
    }

    static void testSetCrcValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        long validCrc = 0x12345678L;
        entry.setCrc(validCrc);
        assert entry.crc == validCrc : "Crc should be set to valid value";
    }

    static void testSetCrcNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setCrc(-1L);
            assert false : "Expected IllegalArgumentException for negative crc";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("invalid entry crc-32") : "Exception message mismatch";
        }
    }

    static void testSetCrcExceedsMax() {
        ZipEntry entry = new ZipEntry("test.txt");
        long invalidCrc = 0x1FFFFFFFFL; // exceeds 32 bits
        try {
            entry.setCrc(invalidCrc);
            assert false : "Expected IllegalArgumentException for crc exceeding max";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("invalid entry crc-32") : "Exception message mismatch";
        }
    }
}
