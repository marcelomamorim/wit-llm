/* @test    @summary Test setting CRC with valid and invalid values    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetCrcValidValues();
        testSetCrcInvalidNegative();
        testSetCrcInvalidExceeding();
    }

    static void testSetCrcValidValues() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setCrc(0L);
            entry.setCrc(0xFFFFFFFFL);
        } catch (Exception e) {
            throw new AssertionError("Valid CRC values should not throw", e);
        }
    }

    static void testSetCrcInvalidNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setCrc(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative CRC");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testSetCrcInvalidExceeding() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setCrc(0x1_0000_0000L); // 4294967296, just above max
            throw new AssertionError("Expected IllegalArgumentException for exceeding CRC");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
