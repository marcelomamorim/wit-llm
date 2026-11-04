/* @test    @summary Test for ZipEntry.setSize(long) method to verify exception on invalid input    @run main ZipEntryWitupTest */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetSizeNegative();
        testSetSizeZero();
        testSetSizePositive();
    }

    private static void testSetSizeNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for negative size");
        }
    }

    private static void testSetSizeZero() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(0);
            System.out.println("Passed: No exception for zero size");
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected exception for zero size");
        }
    }

    private static void testSetSizePositive() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(12345);
            System.out.println("Passed: No exception for positive size");
        } catch (IllegalArgumentException e) {
            System.err.println("Failed: Unexpected exception for positive size");
        }
    }
}
