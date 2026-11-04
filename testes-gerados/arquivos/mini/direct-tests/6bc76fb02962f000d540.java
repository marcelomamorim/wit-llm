/*
 * @test
 * @summary Test ZipEntry(String) constructor for valid and invalid inputs
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.util.Objects;

public class ZipEntryWitupTest {
    public static void main(String[] args) {
        testValidName();
        testNullName();
        testTooLongName();
    }

    private static void testValidName() {
        String validName = "validEntryName";
        ZipEntry entry = new ZipEntry(validName);
        if (!validName.equals(entry.name)) {
            throw new AssertionError("Name not set correctly");
        }
    }

    private static void testNullName() {
        try {
            new ZipEntry(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
            if (!"name".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testTooLongName() {
        int length = 0xFFFF + 1;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append('a');
        }
        String tooLongName = sb.toString();
        try {
            new ZipEntry(tooLongName);
            throw new AssertionError("Expected IllegalArgumentException for too long name");
        } catch (IllegalArgumentException iae) {
            if (!"entry name too long".equals(iae.getMessage())) {
                throw new AssertionError("IllegalArgumentException message mismatch");
            }
        }
    }
}
