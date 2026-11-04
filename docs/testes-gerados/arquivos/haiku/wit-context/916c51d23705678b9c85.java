/*
 * @test
 * @summary Verifies ZipEntry.setSize rejects negative sizes and keeps previous state observable through getSize
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testNewEntryHasUnknownSize();
        testNegativeOneIsRejected();
        testNegativeLongMinValueIsRejected();
        testZeroSizeIsAccepted();
        testPositiveSizeIsAccepted();
        testRejectedSetKeepsPreviousSize();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNewEntryHasUnknownSize() {
        ZipEntry e = new ZipEntry("a.txt");
        check(e.getSize() == -1L, "new entry size should be -1, was " + e.getSize());
    }

    static void testNegativeOneIsRejected() {
        ZipEntry e = new ZipEntry("a.txt");
        boolean thrown = false;
        try {
            e.setSize(-1L);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        check(thrown, "setSize(-1) should throw IllegalArgumentException");
    }

    static void testNegativeLongMinValueIsRejected() {
        ZipEntry e = new ZipEntry("a.txt");
        boolean thrown = false;
        try {
            e.setSize(Long.MIN_VALUE);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        check(thrown, "setSize(Long.MIN_VALUE) should throw IllegalArgumentException");
    }

    static void testZeroSizeIsAccepted() {
        ZipEntry e = new ZipEntry("a.txt");
        e.setSize(0L);
        check(e.getSize() == 0L, "size should be 0 after setSize(0), was " + e.getSize());
    }

    static void testPositiveSizeIsAccepted() {
        ZipEntry e = new ZipEntry("a.txt");
        e.setSize(12345L);
        check(e.getSize() == 12345L, "size should be 12345, was " + e.getSize());
        e.setSize(Long.MAX_VALUE);
        check(e.getSize() == Long.MAX_VALUE, "size should be Long.MAX_VALUE, was " + e.getSize());
    }

    static void testRejectedSetKeepsPreviousSize() {
        ZipEntry e = new ZipEntry("a.txt");
        e.setSize(42L);
        try {
            e.setSize(-5L);
        } catch (IllegalArgumentException ex) {
            // expected
        }
        check(e.getSize() == 42L, "size should remain 42 after rejected setSize, was " + e.getSize());
    }
}
