/* @test    @summary Test ZipEntry.setSize(long) with valid and invalid inputs    @run main ZipEntryWitupTest_Padeb8acb4400 */

import java.util.zip.ZipEntry;

public class ZipEntryWitupTest_Padeb8acb4400 {

    public static void main(String[] args) {
        testSetSizeValid();
        testSetSizeNegative();
    }

    static void testSetSizeValid() {
        ZipEntry entry = new ZipEntry("test.txt");
        long validSize = 1024L;
        entry.setSize(validSize);
        if (entry.size != validSize) {
            throw new AssertionError("Expected size to be " + validSize + " but was " + entry.size);
        }
    }

    static void testSetSizeNegative() {
        ZipEntry entry = new ZipEntry("test.txt");
        long invalidSize = -1L;
        boolean exceptionThrown = false;
        try {
            entry.setSize(invalidSize);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }
}
