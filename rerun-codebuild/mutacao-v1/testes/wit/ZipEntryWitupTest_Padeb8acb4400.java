/* @test    @summary Test for ZipEntry.setSize(long) method to verify exception handling and behavior.    @run main ZipEntryWitupTest_Padeb8acb4400 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest_Padeb8acb4400 {

    public static void main(String[] args) {
        testSetSizeWithNegative();
        testSetSizeWithZero();
        testSetSizeWithPositive();
    }

    private static void testSetSizeWithNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        boolean exceptionThrown = false;
        try {
            entry.setSize(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }

    private static void testSetSizeWithZero() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero size: " + e);
        }
    }

    private static void testSetSizeWithPositive() {
        ZipEntry entry = new ZipEntry("test.txt");
        try {
            entry.setSize(12345);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive size: " + e);
        }
    }
}
