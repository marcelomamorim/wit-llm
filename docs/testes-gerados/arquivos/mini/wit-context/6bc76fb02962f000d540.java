/*
 * @test
 * @summary Test ZipEntry(String) constructor for null and length limit
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testNullNameThrows();
        testNameTooLongThrows();
        testValidNameSucceeds();
    }

    private static void testNullNameThrows() {
        try {
            new ZipEntry(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNameTooLongThrows() {
        // Create a string longer than 0xFFFF (65535) characters
        int length = 65536;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append('a');
        }
        String longName = sb.toString();

        try {
            new ZipEntry(longName);
            throw new RuntimeException("Expected IllegalArgumentException for name too long");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidNameSucceeds() {
        String validName = "validEntryName";
        ZipEntry entry = new ZipEntry(validName);
        if (!validName.equals(entry.getName())) {
            throw new RuntimeException("ZipEntry name mismatch");
        }
    }
}
